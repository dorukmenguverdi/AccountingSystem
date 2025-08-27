# 🏗️ Construction Accounting App

A **functional accounting system** built with **Spring
Boot + MySQL** for backend and a simple **HTML/CSS/JavaScript
frontend**.\
The application helps manage employees, their daily work attendance,
advance payments, and generates monthly salary reports.

------------------------------------------------------------------------

## ✨ Features

-   **Employee Management**: Add, list, update, and delete employees
    with their daily wage.\
-   **Advance Payments**: Record advances given to employees,
    list/filter by employee.\
-   **Daily Work Tracking**: Mark attendance per employee and date with
    status (present/absent).\
-   **Monthly Reports**: Calculate net salary based on worked days,
    daily wage, and advances.\
-   **RESTful API**: Clean endpoint structure with JSON
    request/response.\
-   **Minimal Frontend**: Single-page interface with clean and light UI.

------------------------------------------------------------------------

## 🛠️ Technologies

-   **Backend**: Spring Boot (Web, Data JPA, MySQL, Lombok)\
-   **Database**: MySQL 8+\
-   **Frontend**: HTML5, CSS3, Vanilla JavaScript (Fetch API)\
-   **Build Tool**: Maven\
-   **Version Control**: Git + GitHub

------------------------------------------------------------------------

## 📂 Project Structure

    muhasebe-app/
     ├─ src/main/java/com/muhasebe/insaat/
     │   ├─ model/           # Entities (Calisan, Avans, GunlukCalisma, etc.)
     │   ├─ repositories/    # JPA Repositories
     │   ├─ services/        # Business logic
     │   ├─ controllers/     # REST Controllers
     │   └─ dto/             # Report DTOs
     ├─ src/main/resources/
     │   ├─ application.yml  # Configuration (ignored in .gitignore)
     │   └─ static/index.html # Minimal frontend UI
     └─ README.md

------------------------------------------------------------------------

## ⚙️ Installation & Setup

### 1. Clone the repository

``` bash
git clone https://github.com/yourusername/muhasebe-app.git
cd muhasebe-app
```

### 2. Configure database

Create a MySQL database:

``` sql
CREATE DATABASE muhasebe;
```

Edit `application.yml` (or `application-dev.yml`) with your local
credentials:

``` yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/muhasebe
    username: root
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

⚠️ Note: Do not commit real credentials. Instead, commit an
`application-example.yml` file.

### 3. Run backend

``` bash
./mvnw spring-boot:run
```

Backend will start at: <http://localhost:8080>

### 4. Run frontend

Simply open `src/main/resources/static/index.html` in a browser.\
Or serve it via Spring Boot (already included in static resources).

------------------------------------------------------------------------

## 🔗 API Endpoints

### Employees

-   `GET /api/calisanlar` → List all employees\
-   `POST /api/calisanlar` → Add/Update employee\
-   `GET /api/calisanlar/{id}` → Get employee by ID\
-   `DELETE /api/calisanlar/{id}` → Delete employee

### Advances

-   `GET /api/avanslar` → List all advances\
-   `GET /api/avanslar?calisanId=1` → List advances of employee #1\
-   `POST /api/avanslar` → Add advance\
-   `DELETE /api/avanslar/{id}` → Delete advance

### Daily Work

-   `GET /api/gunluk-calisma` → List all records\
-   `GET /api/gunluk-calisma?calisanId=1&tarih=2025-08-27` → Filter by
    employee/date\
-   `POST /api/gunluk-calisma` → Add work record\
-   `DELETE /api/gunluk-calisma/{id}` → Delete record

### Reports

-   `GET /rapor/aylik?donem=2025-08` → Monthly report for all employees\
-   `GET /rapor/aylik/{calisanId}?donem=2025-08` → Monthly report for
    one employee

------------------------------------------------------------------------

## 📸 Screenshots

### Employee List  
![Employee List](ScreenShots/Calisanlar.png)  

### Advance Payment Form  
![Advance Form](ScreenShots/Avanslar.png)  

### Daily Work Tracking  
![Daily Work](ScreenShots/GunlukCalisma.png)  

### Monthly Report  
![Monthly Report](ScreenShots/RaporEkranı.png)  

------------------------------------------------------------------------

## 🚀 Future Improvements

-   Authentication & Role-based access\
-   Export reports as PDF/Excel\
-   Improved UI with React or Angular\
-   Dockerized deployment

------------------------------------------------------------------------

## 👨‍💻 Author

Developed by **Doruk Mengüverdi**\
- 📧 Email: menguverdidoruk@gmail.com\
- 💼 [LinkedIn](https://www.linkedin.com/in/doruk-menguverdi/)\
- 💻 [GitHub](https://github.com/dorukmenguverdi)
