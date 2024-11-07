INSERT INTO treatment_plan (pet_id, diagnosis, description, date_administered, end_date, notes, vet_id) VALUES 
(16, 'Arthritis', 'Chronic arthritis, requires regular monitoring and anti-inflammatory medication.', '2024-10-01', '2025-01-01', 'Follow-up in 3 months.', 1);

INSERT INTO medicine (name, description, strength, side_effects, cost) VALUES 
('Carprofen', 'Non-steroidal anti-inflammatory drug (NSAID) used for arthritis.', '50mg', 'May cause stomach upset.', 25.00);

INSERT INTO prescription (pet_id, vet_id, medicine_id, instructions, dosage_quantity, date_administered, expiry_date, repeats_left, renewal_date) VALUES 
(16, 1, 1, 'Administer 50mg twice daily for 7 days.', '50mg', '2024-10-01', '2024-11-01', 3, '2024-12-01');

INSERT INTO vaccination_record (pet_id, vaccine_name, date_administered, next_due_date, status, notes, vet_id, cost) 
VALUES 
(16, 'Rabies', '2024-09-01', '2025-09-01', 'Completed', 'Next vaccination in 12 months.', 1, 45.00),
(16, 'Distemper', '2024-09-01', '2025-09-01', 'Completed', 'Next vaccination in 12 months.', 1, 30.00);
