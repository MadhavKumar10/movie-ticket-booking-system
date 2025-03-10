Movie Ticket Booking System 🎬🍿
📌 Project Overview
The Movie Ticket Booking System is a backend REST API application that allows users to browse movies, book tickets, and manage bookings. It features JWT-based authentication, role-based access control, and secure API endpoints for movie and booking management.

🛠️ Tech Stack
Backend: Java, Spring Boot, Spring Security

Database: MongoDB

Authentication: JWT (JSON Web Token)

API Documentation: Swagger

Build Tool: Maven

Version Control: Git & GitHub

🚀 Features
✅ User Authentication: Register and log in users with JWT-based authentication.

✅ Role-Based Access: Admins can manage movies and bookings; customers can book tickets.

✅ Movie Management: Add, update, delete, and retrieve movies.

✅ Ticket Booking: Place, update, and retrieve bookings.

✅ RESTful API: Built with Spring Boot for seamless integration.

📂 Project Structure
Copy
MovieTicketBookingSystem/
│── src/main/java/com/movieticket
│   ├── controller/        # REST Controllers
│   ├── entity/            # MongoDB Entity classes
│   ├── repository/        # MongoDB Repositories
│   ├── service/           # Business Logic Services
│   ├── config/            # Security & App Configurations
│   ├── util/              # Utility classes (e.g., JWT)
│── src/main/resources/
│   ├── application.properties  # MongoDB & App Configurations
│── pom.xml               # Maven Dependencies
│── README.md             # Project Documentation
🛠️ Installation & Setup
1️⃣ Clone the Repository
bash
Copy
git clone https://github.com/MadhavKumar10/movie-ticket-booking-system.git
cd movie-ticket-booking-system
2️⃣ Configure MongoDB
Modify src/main/resources/application.properties:

properties
Copy
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.mongodb.net/<database>?retryWrites=true&w=majority
3️⃣ Build and Run the Application
bash
Copy
mvn clean install
mvn spring-boot:run
🔑 Authentication & Authorization
This system uses JWT authentication. The token must be passed in the Authorization header as Bearer <token> for secured endpoints.

🔹 Generate JWT Token (Login)
Endpoint: POST /api/login

Request:

json
Copy
{
    "email": "user@example.com",
    "password": "password123"
}
Response:

json
Copy
{
    "token": "your_jwt_token"
}
📌 API Endpoints
🔹 User Endpoints
Method	Endpoint	Description
POST	/api/register	Register a new user
POST	/api/login	User login (JWT token)
🔹 Movie Endpoints
Method	Endpoint	Description
GET	/api/movies	Retrieve all movies
GET	/api/movies/{id}	Retrieve a single movie
POST	/api/movies	Add a new movie (Admin only)
PUT	/api/movies/{id}	Update a movie (Admin only)
DELETE	/api/movies/{id}	Delete a movie (Admin only)
🔹 Booking Endpoints
Method	Endpoint	Description
GET	/api/bookings	Retrieve all bookings (Admin)
GET	/api/bookings/{id}	Retrieve a single booking
POST	/api/bookings	Place a new booking (Customer)
PUT	/api/bookings/{id}/status	Update booking status (Admin)
🛠️ Troubleshooting
Issue: JWT Authentication failed: Unauthorized

Ensure the token is correctly included in the request headers.

Check if the token has expired.

Issue: Cannot connect to MongoDB

Ensure MongoDB is running.

Check application.properties for correct MongoDB credentials.

📜 License
This project is open-source and available under the MIT License.

💡 Contributors
👤 Madhav Kumar - GitHub

Happy Coding! 🚀
