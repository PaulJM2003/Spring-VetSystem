INSERT INTO prescription (prescription_id, pet_id, vet_id, medicine_id, instructions, dosage_quantity, date_administered, expiry_date, repeats_left, renewal_date) VALUES
(2, 6, 1, 2, 'testInstuctionsExpired', 'testdosage', '2024-10-2', '2024-1-1', 5, '2024-10-10');

INSERT INTO dosage (dosage_id, date_administered, dosage_quantity, instructions, next_dosage_date, medicine_id, pet_id, notes) VALUES 
(1, '2024-10-02', 'testdosage', 'testInstuctions', '2025-02-06', 1, 6, 'none'),
(2, '2024-10-02', 'testdosage', 'testInstuctionsExpired', '2025-03-07', 2, 6, 'none');
