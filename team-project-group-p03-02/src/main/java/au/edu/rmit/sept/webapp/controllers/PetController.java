package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.dto.DosageDTO;
import au.edu.rmit.sept.webapp.dto.PetDTO;
import au.edu.rmit.sept.webapp.models.*;
import au.edu.rmit.sept.webapp.services.CustomUserDetailsService;
import au.edu.rmit.sept.webapp.services.DosageService;
import au.edu.rmit.sept.webapp.services.EmailService;
import au.edu.rmit.sept.webapp.services.PetMedicalHistoryService;
import au.edu.rmit.sept.webapp.services.PetService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import au.edu.rmit.sept.webapp.services.PrescriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PetMedicalHistoryService petMedicalHistoryService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private DosageService dosageService;
    /**
     * Handles the view for displaying all registered pets for the currently logged-in user.
     */
    @GetMapping
    public String viewRegisteredPets(Model model) {
        // Get the currently authenticated user
        CustomUser currentUser = customUserDetailsService.getCurrentUser();

        // Fetch the pets associated with the current user
        List<PetDTO> pets = petService.findPetsByUserId(currentUser.getUserId());

        // Add the list of pets to the model
        if (!pets.isEmpty()) {
            model.addAttribute("pets", pets);
        } else {
            model.addAttribute("noPetsMessage", "You have no registered pets.");
        }

        // Return the view to display the registered pets
        return "pets/index";
    }

    @GetMapping("/{petId}/view")
    public String viewPetMedicalHistory(@PathVariable Long petId, Model model) {
        // Fetch the pet by ID - necessary for TH template
        Optional<Pet> pet = petService.findPetBypetId(petId);

        if (pet.isEmpty()) {
            model.addAttribute("errorMessage", "Pet not found.");
            return "pets/error";
        }

        // Add the pet to the model
        model.addAttribute("pet", pet.get());

        // Get the pet's medical data
        List<VaccinationRecord> vaccinations = petMedicalHistoryService.getVaccinationRecordsBypetId(petId);
        List<TreatmentPlan> treatmentPlans = petMedicalHistoryService.getTreatmentPlansBypetId(petId);
        List<Prescription> prescriptions = petMedicalHistoryService.getPrescriptionsBypetId(petId);
        
        model.addAttribute("vaccinations", vaccinations);
        model.addAttribute("treatmentPlans", treatmentPlans);
        model.addAttribute("prescriptions", prescriptions);

        // Check if all medical data is empty or not
        if (vaccinations.isEmpty()) {
            model.addAttribute("noVaccinationsMessage", "No vaccination records found.");
        } else {
            model.addAttribute("vaccinations", vaccinations);
        }

        if (treatmentPlans.isEmpty()) {
            model.addAttribute("noTreatmentPlansMessage", "No treatment plans found.");
        } else {
            model.addAttribute("treatmentPlans", treatmentPlans);
        }

        if (prescriptions.isEmpty()) {
            model.addAttribute("noPrescriptionsMessage", "No prescriptions found.");
        } else {
            model.addAttribute("prescriptions", prescriptions);
        }

        // Return the view for displaying the medical data
        return "pets/view";
    }
    
    @GetMapping("/new")
    public String showPetRegistrationForm(Model model) {
        return "pets/new";
    }

    @PostMapping("/new")
    public String registerNewPet(@ModelAttribute("pet") Pet pet, BindingResult result, Model model) {
        // Validation for blank fields
        if (pet.getName().trim().isEmpty()) {
            result.rejectValue("name", "error.pet", "Your pet's name cannot be blank.");
        }
        if (pet.getSpecies().trim().isEmpty()) {
            result.rejectValue("species", "error.pet", "Your pet's species cannot be blank.");
        }
        if (pet.getBreed().trim().isEmpty()) {
            result.rejectValue("breed", "error.pet", "Your pet's breed cannot be blank.");
        }
        
        if (result.hasErrors()) {
            return "pets/new";
        }

        // Get the currently authenticated user
        CustomUser currentUser = customUserDetailsService.getCurrentUser();
        
        // Set the owner of the pet
        pet.setOwner(currentUser);

        // Save the pet
        petService.savePet(pet);

        // Redirect to the pets list page after saving
        return "redirect:/pets";
    }

    // Handles prescription refill requests
    @PostMapping("/{petId}/refill/{prescriptionID}")
    public String requestPrescription(@PathVariable("petId") Long petId, @PathVariable("prescriptionID") Long prescriptionID, RedirectAttributes redirectAttributes) {

        // Get Prescription from ID
        Prescription prescription = prescriptionService.getPrescriptionFromID(prescriptionID);
        // Check if prescription is valid
        String prescriptionRequest = prescriptionService.checkPrescription(prescription);

        if (prescriptionRequest.equals("valid")) {
            System.out.println("Processed valid refill request");
            // Decrement and order prescription
            prescriptionService.decrementPrescription(prescription);
            prescriptionService.orderPrescription(prescriptionID, petId);
            // Add success message to redirect
            redirectAttributes.addFlashAttribute("successMessage", "Prescription ordered successfully!");
        } else {
            System.out.println("Invalid refill request");
            // Add fail message to redirect
            redirectAttributes.addFlashAttribute("failMessage", String.format("Invalid refill request, %s", prescriptionRequest));
        }

        // Redirect back to same page.
        return "redirect:/pets/{petId}/view";
    }
    //feature to download :
    @GetMapping("/{petId}/download")
    public void downloadPetRecords(@PathVariable Long petId, HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=pet_records_" + petId + ".pdf");
        // Creating new pdf
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());    
        document.open();
        // fetching pet data
        Pet pet = petService.findPetBypetId(petId).orElseThrow(() -> new IllegalArgumentException("Invalid pet ID"));
        List<VaccinationRecord> vaccinations = petMedicalHistoryService.getVaccinationRecordsBypetId(petId);
        List<TreatmentPlan> treatmentPlans = petMedicalHistoryService.getTreatmentPlansBypetId(petId);
        List<Prescription> prescriptions = petMedicalHistoryService.getPrescriptionsBypetId(petId);
        //setting fonts
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font regularFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
        // data to the PDF -> Pet's name
        document.add(new Paragraph("Medical Records for " + pet.getName(), boldFont));
        document.add(new Paragraph("\n"));
        // Vaccination History
        document.add(new Paragraph("Vaccination History", boldFont));
        if (vaccinations.isEmpty()) {
            document.add(new Paragraph("No vaccination records found.", regularFont));
        } else {
            PdfPTable vaccinationTable = new PdfPTable(5);
            vaccinationTable.setWidthPercentage(100);
            vaccinationTable.addCell(new PdfPCell(new Paragraph("Vaccine Name", boldFont)));
            vaccinationTable.addCell(new PdfPCell(new Paragraph("Date Administered", boldFont)));
            vaccinationTable.addCell(new PdfPCell(new Paragraph("Next Due Date", boldFont)));
            vaccinationTable.addCell(new PdfPCell(new Paragraph("Status", boldFont)));
            vaccinationTable.addCell(new PdfPCell(new Paragraph("Notes", boldFont)));
            for (VaccinationRecord vaccine : vaccinations) {
                vaccinationTable.addCell(new PdfPCell(new Paragraph(vaccine.getVaccineName(), regularFont)));
                vaccinationTable.addCell(new PdfPCell(new Paragraph(vaccine.getDateAdministered().toString(), regularFont)));
                vaccinationTable.addCell(new PdfPCell(new Paragraph(vaccine.getNextDueDate() != null ? vaccine.getNextDueDate().toString() : "N/A", regularFont)));
                vaccinationTable.addCell(new PdfPCell(new Paragraph(vaccine.getStatus(), regularFont)));
                vaccinationTable.addCell(new PdfPCell(new Paragraph(vaccine.getNotes(), regularFont)));
            }
            document.add(vaccinationTable);
        }
        document.add(new Paragraph("\n"));
        // Treatment Plans
        document.add(new Paragraph("Treatment Plans", boldFont));
        if (treatmentPlans.isEmpty()) {
            document.add(new Paragraph("No treatment plans found.", regularFont));
        } else {
            PdfPTable treatmentTable = new PdfPTable(5);
            treatmentTable.setWidthPercentage(100);
            treatmentTable.addCell(new PdfPCell(new Paragraph("Diagnosis", boldFont)));
            treatmentTable.addCell(new PdfPCell(new Paragraph("Description", boldFont)));
            treatmentTable.addCell(new PdfPCell(new Paragraph("Date Administered", boldFont)));
            treatmentTable.addCell(new PdfPCell(new Paragraph("End Date", boldFont)));
            treatmentTable.addCell(new PdfPCell(new Paragraph("Notes", boldFont)));
            for (TreatmentPlan treatment : treatmentPlans) {
                treatmentTable.addCell(new PdfPCell(new Paragraph(treatment.getDiagnosis(), regularFont)));
                treatmentTable.addCell(new PdfPCell(new Paragraph(treatment.getDescription(), regularFont)));
                treatmentTable.addCell(new PdfPCell(new Paragraph(treatment.getDateAdministered().toString(), regularFont)));
                treatmentTable.addCell(new PdfPCell(new Paragraph(treatment.getEndDate() != null ? treatment.getEndDate().toString() : "N/A", regularFont)));
                treatmentTable.addCell(new PdfPCell(new Paragraph(treatment.getNotes(), regularFont)));
            }
    
            document.add(treatmentTable);
        }
        document.add(new Paragraph("\n"));
        // Prescription History
        document.add(new Paragraph("Prescription History", boldFont));
        if (prescriptions.isEmpty()) {
            document.add(new Paragraph("No prescriptions found.", regularFont));
        } else {
            PdfPTable prescriptionTable = new PdfPTable(6);
            prescriptionTable.setWidthPercentage(100);
            prescriptionTable.addCell(new PdfPCell(new Paragraph("Medicine Name", boldFont)));
            prescriptionTable.addCell(new PdfPCell(new Paragraph("Dosage Quantity", boldFont)));
            prescriptionTable.addCell(new PdfPCell(new Paragraph("Instructions", boldFont)));
            prescriptionTable.addCell(new PdfPCell(new Paragraph("Date Administered", boldFont)));
            prescriptionTable.addCell(new PdfPCell(new Paragraph("Expiry Date", boldFont)));
            prescriptionTable.addCell(new PdfPCell(new Paragraph("Repeats Left", boldFont)));
            for (Prescription prescription : prescriptions) {
                prescriptionTable.addCell(new PdfPCell(new Paragraph(prescription.getMedicine().getName(), regularFont)));
                prescriptionTable.addCell(new PdfPCell(new Paragraph(prescription.getDosageQuantity(), regularFont)));
                prescriptionTable.addCell(new PdfPCell(new Paragraph(prescription.getInstructions(), regularFont)));
                prescriptionTable.addCell(new PdfPCell(new Paragraph(prescription.getDateAdministered().toString(), regularFont)));
                prescriptionTable.addCell(new PdfPCell(new Paragraph(prescription.getExpiryDate() != null ? prescription.getExpiryDate().toString() : "N/A", regularFont)));
                prescriptionTable.addCell(new PdfPCell(new Paragraph(String.valueOf(prescription.getRepeatsLeft()), regularFont)));
            }
            document.add(prescriptionTable);
        }    
        document.close();
    }
           
    @PostMapping("/{petId}/share")
    public String shareHealthRecords(@PathVariable Long petId, @RequestParam("email") String email, Model model) {
        try {
            // Fetch the pet to get the name
            Pet pet = petService.findPetBypetId(petId).orElseThrow(() -> new IllegalArgumentException("Invalid pet ID"));
            String petName = pet.getName(); 
            // Fetch the health records PDF for the pet
            ByteArrayOutputStream healthRecordPdf = generateHealthRecordPdf(petId);
            // Send the email with health record attachment
            emailService.sendHealthRecord(email, petName, healthRecordPdf);
            //confirmation message
            model.addAttribute("successMessage", "Health records for " + petName + " have been shared with " + email);
        } catch (IOException | DocumentException | MessagingException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to share health records.");
        }
    
        return "redirect:/pets";  
    }
    
// PDF generation method :
    public ByteArrayOutputStream generateHealthRecordPdf(Long petId) throws DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            // Fetch pet data
            Pet pet = petService.findPetBypetId(petId).orElseThrow(() -> new IllegalArgumentException("Invalid pet ID"));
            List<VaccinationRecord> vaccinations = petMedicalHistoryService.getVaccinationRecordsBypetId(petId);
            List<TreatmentPlan> treatmentPlans = petMedicalHistoryService.getTreatmentPlansBypetId(petId);
            List<Prescription> prescriptions = petMedicalHistoryService.getPrescriptionsBypetId(petId);
            // fonts
            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font regularFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
            //pet information
            document.add(new Paragraph("Medical Records for " + pet.getName(), boldFont));
            document.add(new Paragraph("\n"));
            // Vaccination History
            document.add(new Paragraph("Vaccination History", boldFont));
            if (vaccinations.isEmpty()) {
                document.add(new Paragraph("No vaccination records found.", regularFont));
            } else {
                PdfPTable vaccinationTable = new PdfPTable(5);
                vaccinationTable.setWidthPercentage(100);
                //table headers
                vaccinationTable.addCell(new PdfPCell(new Paragraph("Vaccine Name", boldFont)));
                vaccinationTable.addCell(new PdfPCell(new Paragraph("Date Administered", boldFont)));
                vaccinationTable.addCell(new PdfPCell(new Paragraph("Next Due Date", boldFont)));
                vaccinationTable.addCell(new PdfPCell(new Paragraph("Status", boldFont)));
                vaccinationTable.addCell(new PdfPCell(new Paragraph("Notes", boldFont)));
                // vaccination data
                for (VaccinationRecord vaccine : vaccinations) {
                    vaccinationTable.addCell(new PdfPCell(new Paragraph(vaccine.getVaccineName(), regularFont)));
                    vaccinationTable.addCell(new PdfPCell(new Paragraph(vaccine.getDateAdministered().toString(), regularFont)));
                    vaccinationTable.addCell(new PdfPCell(new Paragraph(vaccine.getNextDueDate() != null ? vaccine.getNextDueDate().toString() : "N/A", regularFont)));
                    vaccinationTable.addCell(new PdfPCell(new Paragraph(vaccine.getStatus(), regularFont)));
                    vaccinationTable.addCell(new PdfPCell(new Paragraph(vaccine.getNotes(), regularFont)));
                }

                document.add(vaccinationTable);
            }
            document.add(new Paragraph("\n"));  
            // Treatment Plans
            document.add(new Paragraph("Treatment Plans", boldFont));
            if (treatmentPlans.isEmpty()) {
                document.add(new Paragraph("No treatment plans found.", regularFont));
            } else {
                PdfPTable treatmentTable = new PdfPTable(5);
                treatmentTable.setWidthPercentage(100);
                //table headers
                treatmentTable.addCell(new PdfPCell(new Paragraph("Diagnosis", boldFont)));
                treatmentTable.addCell(new PdfPCell(new Paragraph("Description", boldFont)));
                treatmentTable.addCell(new PdfPCell(new Paragraph("Date Administered", boldFont)));
                treatmentTable.addCell(new PdfPCell(new Paragraph("End Date", boldFont)));
                treatmentTable.addCell(new PdfPCell(new Paragraph("Notes", boldFont)));
                // treatment plan data
                for (TreatmentPlan treatment : treatmentPlans) {
                    treatmentTable.addCell(new PdfPCell(new Paragraph(treatment.getDiagnosis(), regularFont)));
                    treatmentTable.addCell(new PdfPCell(new Paragraph(treatment.getDescription(), regularFont)));
                    treatmentTable.addCell(new PdfPCell(new Paragraph(treatment.getDateAdministered().toString(), regularFont)));
                    treatmentTable.addCell(new PdfPCell(new Paragraph(treatment.getEndDate() != null ? treatment.getEndDate().toString() : "N/A", regularFont)));
                    treatmentTable.addCell(new PdfPCell(new Paragraph(treatment.getNotes(), regularFont)));
                }
                document.add(treatmentTable);
            }
            document.add(new Paragraph("\n"));  
            // prescription History
            document.add(new Paragraph("Prescription History", boldFont));
            if (prescriptions.isEmpty()) {
                document.add(new Paragraph("No prescriptions found.", regularFont));
            } else {
                PdfPTable prescriptionTable = new PdfPTable(6);
                prescriptionTable.setWidthPercentage(100);
                //table headers
                prescriptionTable.addCell(new PdfPCell(new Paragraph("Medicine Name", boldFont)));
                prescriptionTable.addCell(new PdfPCell(new Paragraph("Dosage Quantity", boldFont)));
                prescriptionTable.addCell(new PdfPCell(new Paragraph("Instructions", boldFont)));
                prescriptionTable.addCell(new PdfPCell(new Paragraph("Date Administered", boldFont)));
                prescriptionTable.addCell(new PdfPCell(new Paragraph("Expiry Date", boldFont)));
                prescriptionTable.addCell(new PdfPCell(new Paragraph("Repeats Left", boldFont)));
                //prescription data
                for (Prescription prescription : prescriptions) {
                    prescriptionTable.addCell(new PdfPCell(new Paragraph(prescription.getMedicine().getName(), regularFont)));
                    prescriptionTable.addCell(new PdfPCell(new Paragraph(prescription.getDosageQuantity(), regularFont)));
                    prescriptionTable.addCell(new PdfPCell(new Paragraph(prescription.getInstructions(), regularFont)));
                    prescriptionTable.addCell(new PdfPCell(new Paragraph(prescription.getDateAdministered().toString(), regularFont)));
                    prescriptionTable.addCell(new PdfPCell(new Paragraph(prescription.getExpiryDate() != null ? prescription.getExpiryDate().toString() : "N/A", regularFont)));
                    prescriptionTable.addCell(new PdfPCell(new Paragraph(String.valueOf(prescription.getRepeatsLeft()), regularFont)));
                }

                document.add(prescriptionTable);
            }

        } finally {
            document.close();
        }

        return byteArrayOutputStream;
    }

    @GetMapping("/{petId}/medicine/{medicineId}/dosages")
    public String viewMedicineDosages(@PathVariable Long petId, @PathVariable Long medicineId, Model model) {
        System.out.println("Entered viewMedicineDosages method.");
        
        // Fetch the pet by ID
        Optional<Pet> pet = petService.findPetBypetId(petId);
        if (pet.isEmpty()) {
            model.addAttribute("errorMessage", "Pet not found.");
            return "pets/error";
        }
        
        // Add the pet to the model
        model.addAttribute("pet", pet.get());
        
        // Fetch dosage details for this medicine
        List<DosageDTO> dosages = dosageService.getDetailedDosageByPetIdAndMedicineId(petId, medicineId);
        
        if (dosages.isEmpty()) {
            model.addAttribute("noDosagesMessage", "No dosages found for this medicine.");
        } else {
            model.addAttribute("dosages", dosages);
        }
        
        return "pets/dosages";
    }
    
}
