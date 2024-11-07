CREATE TABLE `clinic` (
  `clinic_id` BIGINT AUTO_INCREMENT,
  `name` VARCHAR(255),
  `address` VARCHAR(255) DEFAULT NULL,
  `phone_number` VARCHAR(255) DEFAULT NULL,
  `email` VARCHAR(255),
  PRIMARY KEY (`clinic_id`)
);

CREATE TABLE `user` (
  `user_id` BIGINT AUTO_INCREMENT,
  `name` VARCHAR(255),
  `password` VARCHAR(255),
  `email` VARCHAR(255),
  `clinic_id` BIGINT DEFAULT NULL,
  `phone_number` VARCHAR(255) DEFAULT NULL,
  `address` VARCHAR(255) DEFAULT NULL,
  `user_type` ENUM('PetOwner', 'Vet', 'Admin'),  
  PRIMARY KEY (`user_id`),
  FOREIGN KEY (`clinic_id`) REFERENCES `clinic`(`clinic_id`)
);

CREATE TABLE `pet` (
  `pet_id` BIGINT AUTO_INCREMENT,
  `owner_id` BIGINT,
  `name` VARCHAR(255),
  `species` VARCHAR(255),
  `breed` VARCHAR(255),
  `age` INT,
  PRIMARY KEY (`pet_id`),
  FOREIGN KEY (`owner_id`) REFERENCES `user`(`user_id`)
);

CREATE TABLE `treatment_plan` (
  `treatment_plan_id` BIGINT AUTO_INCREMENT,
  `pet_id` BIGINT,
  `diagnosis` VARCHAR(255),
  `description` TEXT,
  `date_administered` DATE,
  `end_date` DATE,
  `notes` TEXT,
  `vet_id` BIGINT,
  PRIMARY KEY (`treatment_plan_id`),
  FOREIGN KEY (`pet_id`) REFERENCES `pet`(`pet_id`),
  FOREIGN KEY (`vet_id`) REFERENCES `user`(`user_id`)
);

CREATE TABLE `educational_resource` (
  `resource_id` BIGINT AUTO_INCREMENT,
  `title` VARCHAR(255),
  `resource_type` VARCHAR(255),
  `author` VARCHAR(255),
  `publish_date` DATE,
  `category` VARCHAR(255),
  `content` TEXT,
  `description` TEXT,
  PRIMARY KEY (`resource_id`)
);

CREATE TABLE `latest_trends` (
  `trend_id` BIGINT AUTO_INCREMENT,
  `title` VARCHAR(255),
  `description` TEXT,
  `author` VARCHAR(255),
  `publish_date` DATE,
  `trend_category` VARCHAR(255),
  PRIMARY KEY (`trend_id`)
);

CREATE TABLE `medicine` (
  `medicine_id` BIGINT AUTO_INCREMENT,
  `name` VARCHAR(255),
  `description` TEXT,
  `strength` VARCHAR(50),
  `side_effects` TEXT,
  `cost` DECIMAL(10, 2),
  PRIMARY KEY (`medicine_id`)
);

CREATE TABLE `saved_resources` (
  `user_id` BIGINT,
  `resource_id` BIGINT,
  `saved_at` TIMESTAMP,
  PRIMARY KEY (`user_id`, `resource_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
  FOREIGN KEY (`resource_id`) REFERENCES `educational_resource`(`resource_id`)
);

CREATE TABLE `appointment` (
  `appointment_id` BIGINT AUTO_INCREMENT,
  `user_id` BIGINT,
  `pet_id` BIGINT,
  `clinic_id` BIGINT,
  `appointment_date` DATE,
  `status` ENUM('Upcoming', 'Past', 'Cancelled'),
  `general_notes` TEXT,
  `fees` DECIMAL(10, 2),
  `vet_id` BIGINT,
  `appointment_time` TIME,
  PRIMARY KEY (`appointment_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`),
  FOREIGN KEY (`pet_id`) REFERENCES `pet`(`pet_id`),
  FOREIGN KEY (`clinic_id`) REFERENCES `clinic`(`clinic_id`),
  FOREIGN KEY (`vet_id`) REFERENCES `user`(`user_id`)
);

CREATE TABLE `prescription` (
  `prescription_id` BIGINT AUTO_INCREMENT,
  `pet_id` BIGINT,
  `vet_id` BIGINT,
  `medicine_id` BIGINT,
  `instructions` TEXT,
  `dosage_quantity` VARCHAR(50),
  `date_administered` DATE,
  `expiry_date` DATE,
  `repeats_left` INT,
  `renewal_date` DATE,
  PRIMARY KEY (`prescription_id`),
  FOREIGN KEY (`pet_id`) REFERENCES `pet`(`pet_id`),
  FOREIGN KEY (`vet_id`) REFERENCES `user`(`user_id`),
  FOREIGN KEY (`medicine_id`) REFERENCES `medicine`(`medicine_id`)
);

CREATE TABLE `vaccination_record` (
  `vaccination_id` BIGINT AUTO_INCREMENT,
  `pet_id` BIGINT,
  `vaccine_name` VARCHAR(255),
  `date_administered` DATE,
  `next_due_date` DATE,
  `status` VARCHAR(50),
  `notes` TEXT,
  `vet_id` BIGINT,
  `cost` DECIMAL(10, 2),
  PRIMARY KEY (`vaccination_id`),
  FOREIGN KEY (`pet_id`) REFERENCES `pet`(`pet_id`),
  FOREIGN KEY (`vet_id`) REFERENCES `user`(`user_id`)
);

CREATE TABLE `medical_history` (
  `medical_history_id` BIGINT AUTO_INCREMENT,
  `pet_id` BIGINT,
  `chronic_conditions` TEXT,
  `allergies` TEXT,
  `notes` TEXT,
  `last_vaccination_date` DATE,
  `last_treatment_date` DATE,
  `last_prescription_date` DATE,
  PRIMARY KEY (`medical_history_id`),
  FOREIGN KEY (`pet_id`) REFERENCES `pet`(`pet_id`)
);


