# Bestore
book ecommerce project

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Features

- User authentication and authorization
- Product listing and categorization
- Shopping cart functionality
- Order management
- User profile management
- Search product
- Payment gateway integration

## Requirements

- **Back End:**
  - Java 17+
  - Spring Boot 3.0+
  - MySQL 8.0+
  - Maven 3.6+
  
- **Front End:**
  - Node.js 20+
  - Angular 17+
  - npm 10.1+

## Installation

### Backend

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Luvannie/Bestore.git
   cd bestore/backend
    ```
2. **Create MySQL database:**
    ```sql
    CREATE DATABASE bestore;
    CREATE USER 'bestoreuser'@'localhost' IDENTIFIED BY 'password';
    GRANT ALL PRIVILEGES ON bestore.* TO 'bestoreuser'@'localhost';
    FLUSH PRIVILEGES;
    ```
    execute the SQL script located in data/BE_Store.sql to populate the database with sample data.
    

3. **Update database configuration:**
 Modify the application.properties file located in src/main/resources/ to match your database settings.
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/bestore
    spring.datasource.username=bestoreuser
    spring.datasource.password=password
    spring.jpa.hibernate.ddl-auto=update
    ```
    see more in [application.properties]

4. **Build and run the backend application:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

### Frontend
1. **Navigate to the frontend directory:**
    ```bash
    cd bestore/frontend
    ```
2. **Install the dependencies:**
    ```bash
    npm install
    ```
3. **Run the frontend application:**
    ```bash
    ng serve
    ```

## Usage
Access the backend API at http://localhost:8080.
Access the frontend application at http://localhost:4200.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.