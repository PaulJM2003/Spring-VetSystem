INSERT INTO treatment_plan (pet_id, diagnosis, description, date_administered, end_date, notes, vet_id) VALUES 
(6, 'Arthritis', 'Chronic arthritis, requires regular monitoring and anti-inflammatory medication.', '2024-10-01', '2025-01-01', 'Follow-up in 3 months.', 1);

INSERT INTO vaccination_record (pet_id, vaccine_name, date_administered, next_due_date, status, notes, vet_id, cost) 
VALUES 
(6, 'Rabies', '2024-09-01', '2025-09-01', 'Completed', 'Next vaccination in 12 months.', 1, 45.00);