README - Product Management Demo Project
This is a simple Spring Boot demo application for managing products. It demonstrates a typical layered architecture (Entity, Repository, Service, DTO, Mapper, REST Controller) along with a Thymeleaf-based frontend that consumes its own REST API. The project includes soft delete functionality, pagination, and basic validation.

🧱 Technologies Used
Java 17

Spring Boot 3.5.10

Spring Data JPA / Hibernate

Spring Web MVC

Thymeleaf (for server-side page rendering)

MapStruct (for DTO-Entity mapping)

Bootstrap 5 & Bootstrap Icons (for UI styling)

H2 Database (in-memory, for easy testing)

Maven (build tool)

📁 Project Structure
text
src/main/java/com/mftplus/
├── product/
│   ├── controller/
│   │   ├── ProductController.java          # REST API endpoints
│   │   └── ProductPageController.java      # Serves the Thymeleaf page
│   ├── dto/
│   │   └── ProductDto.java                  # Data Transfer Object
│   ├── exception/
│   │   ├── ProductNotFoundException.java    # Custom exception
│   │   └── GlobalExceptionHandler.java      # Global error handling
│   ├── mapper/
│   │   └── ProductMapper.java                # MapStruct mapper
│   ├── model/
│   │   ├── BaseEntity.java                    # Auditing base class
│   │   └── Product.java                        # JPA entity with soft delete
│   ├── repository/
│   │   └── ProductRepository.java              # Spring Data JPA repository
│   └── service/
│       ├── ProductService.java                  # Service interface
│       └── impl/
│           └── ProductServiceImpl.java          # Service implementation
└── FullSampleApplication.java                   # Spring Boot main class
Resources:

src/main/resources/templates/product-list.html - Thymeleaf page for product management

src/main/resources/application.properties - configuration (see below)

Static resources (CSS, JS, icons) are expected in src/main/resources/static/ but are ignored in Git (see note below).

⚙️ Configuration
Default configuration uses an H2 in-memory database for quick testing. You can change it to MySQL/PostgreSQL by modifying application.properties.

properties
# Server
server.port=8080

# Database (H2)
spring.datasource.url=jdbc:h2:mem:productdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Thymeleaf
spring.thymeleaf.cache=false
Static Resources Note:
The src/main/resources/static/ folder is ignored in .gitignore because it would contain heavy Bootstrap and icon files.
To run the UI properly, you have two options:

Use CDN links (already included in the HTML) – the page will load Bootstrap from a CDN, so no local static files are required.

If you prefer local files, download Bootstrap 5 and Bootstrap Icons and place them in:

static/css/bootstrap.min.css

static/css/bootstrap-icons.css

static/js/bootstrap.bundle.min.js

(Optional) Place an icon named product.png in static/icon/.

🚀 How to Run
Clone the repository (or copy the source files into your workspace).

Build the project using Maven:

bash
mvn clean install
Run the application:

bash
mvn spring-boot:run
Access the application:

Web UI: http://localhost:8080/products

REST API Base URL: http://localhost:8080/api/products

H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:productdb)

📡 REST API Endpoints
All endpoints return JSON and are prefixed with /api/products.

Method	Endpoint	Description
GET	/api/products	Get paginated list of active products
GET	/api/products/{id}	Get a single product by ID
POST	/api/products	Create a new product
PUT	/api/products/{id}	Update an existing product
DELETE	/api/products/{id}	Soft delete a product
GET	/api/products/deleted	Get paginated list of soft-deleted products
GET	/api/products/all-even-deleted	Get all products (including deleted)
PATCH	/api/products/{id}/restore	Restore a soft-deleted product
GET	/api/products/search/by-name	Search products by name (partial, ignore case)
GET	/api/products/search/by-username	Search products by username (partial, ignore case)
Pagination: All list endpoints accept query parameters page (0-based) and size (default 5). Example: /api/products?page=0&size=10

Validation:

name: letters and spaces, 3-20 characters.

description: letters, numbers, spaces and punctuation .,!?-, 3-255 characters.

price: must be positive.

username: letters and spaces, 3-20 characters.

Validation errors return a 400 Bad Request with a JSON body containing field-specific messages.

🖥️ Web UI
The single-page interface (/products) lets you:

View a paginated list of products.

Add a new product (modal form).

Edit an existing product (modal pre-filled).

Delete a product with confirmation.

Navigate through pages.

The UI communicates entirely with the REST API using fetch() – no form submissions to the backend. This demonstrates a clean separation between the frontend and backend.

🗄️ Database Schema
Table products (automatically created):

Column	Type	Constraints
id	BIGINT	PRIMARY KEY AUTO_INCREMENT
name	VARCHAR(20)	NOT NULL
description	VARCHAR(255)	NOT NULL
price	DOUBLE	NOT NULL
username	VARCHAR(30)	NOT NULL
deleted	BOOLEAN	NOT NULL DEFAULT false
created_date	TIMESTAMP	(from BaseEntity)
last_modified_date	TIMESTAMP	(from BaseEntity)
Soft Delete:
When a product is deleted via the API, the deleted column is set to true. The @SQLDelete and @SQLRestriction annotations ensure that normal queries automatically filter out deleted rows. The restoreById endpoint sets deleted back to false.

🧪 Testing the API
You can use tools like Postman or cURL.

Create a product (POST):

bash
curl -X POST http://localhost:8080/api/products \
-H "Content-Type: application/json" \
-d '{"name":"Laptop","description":"High-performance laptop","price":1200.00,"username":"admin"}'
Get all products (GET):

bash
curl http://localhost:8080/api/products
⚠️ Known Issues / Notes
The static resources folder is intentionally ignored in Git. If you clone the project, the UI will still work because the HTML uses Bootstrap CDN.

The project uses an H2 in-memory database; data is lost after restart. For persistence, switch to a real database in application.properties.

The BaseEntity provides created_date and last_modified_date automatically (requires @EnableJpaAuditing in the main class). Make sure your FullSampleApplication has @EnableJpaAuditing if you want auditing to work.

📜 License
This is a demo project for educational purposes. Feel free to use and modify it as you wish.

Happy Coding! 🚀