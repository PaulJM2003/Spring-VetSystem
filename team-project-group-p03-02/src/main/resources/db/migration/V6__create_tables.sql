CREATE TABLE `dosage` (
  `dosage_id` BIGINT AUTO_INCREMENT,
  `date_administered` DATE,
  `dosage_quantity` VARCHAR(50),
  `instructions` TEXT,
  `next_dosage_date` DATE,
  `medicine_id` BIGINT,
  `pet_id` BIGINT,
  `notes` TEXT,
  PRIMARY KEY (`dosage_id`),
  FOREIGN KEY (`medicine_id`) REFERENCES `medicine`(`medicine_id`),
  FOREIGN KEY (`pet_id`) REFERENCES `pet`(`pet_id`)
);