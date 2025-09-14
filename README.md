# VetCare - Convenient Care For Your Pets :

**video demonstrating the webapplication :** 
[https://rmiteduau-my.sharepoint.com/:v:/g/personal/s3968971_student_rmit_edu_au/EX2PlJx6Ht1Enzz7pcUo8isBtH63aZGMWdsOfDBTrpUzdA?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZy1MaW5rIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXcifX0%3D&e=uQ0SNK](https://rmiteduau-my.sharepoint.com/:v:/g/personal/s3968971_student_rmit_edu_au/EX2PlJx6Ht1Enzz7pcUo8isBtH63aZGMWdsOfDBTrpUzdA?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZy1MaW5rIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXcifX0%3D&e=YTApjO)

***is a Spring Boot web application designed to simplify pet healthcare management. It allows pet owners to manage their pets' medical records, book appointments, request prescriptions, and access educational resources. The backend is built with Spring Boot and integrated with a MySQL database, while Docker is used to streamline deployment.***

# How to Run the Program

## 1. Clone the Repository

First, clone the repository to a local directory 

## 2. Running the Webserver Container

Make sure you have Docker installed and Docker Engine running. Open a new terminal window, navigate to the repository, and run the following commands:

```bash
docker-compose build
docker-compose up
```

> [!TIP]
> If you run into issues, refer to Section 1.1 of the User Guide, located in the root of this repository.

You can now access the webserver in your web browser at the address `http://localhost:8080`.
The MySQL database is available at `localhost:3307`. You can access this via one of two ways:

1. A database client
2. The MySQL CLI. Run the following command in the same terminal window you started the application:

```bash
docker exec -it team-project-group-p03-02-database-1 mysql -u root
use vetcare
```

## 3. Navigating the Website

* You can use the navigation bar at the top of the webpage to perform most tasks.
* A majority of webpages are not accessible unless you are logged in.
* A test user with sample data has been created for your convenience:
  * Email: `test@test`
  * Password: `test`
* Feel free to create your own user and run through the program, using the User Guide as a reference.
* A test vet user has been created for your convenience. You may use this to update basic pet medical data by clicking on the 'Vet Dashboard' link in the navigation bar.
  * Email: `jane.smith@example.com`
  * Password: `1234`

# Functionality

Here is a list of everything you can do on this version of VetCare:

* Register for an account
* Register a new pet to your account
* View your pet's medical records (if any)
* Request prescriptions (if any)
* Book an appointment
* Manage your appointments (edit/view/cancel)
* Browse educational resources for pet care
* Save educational resources to your account
* Manage your account details

**NOTE :**
***This project was Developed by a collaborative team effort This project was created through the combined efforts of me and my team, with each member contributing to various areas, including database design, front-end and back-end development, testing, and documentation.***
