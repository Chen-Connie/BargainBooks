# SunDevil Book Buying and Selling System

A desktop application built with JavaFX that enables Arizona State University (ASU) students to buy and sell used textbooks. The platform provides an affordable, student-driven marketplace with secure account creation, dynamic book listings, and condition-based pricing.
---

## Features

- **User Authentication**
  - Create an account and log in using ASU ID
  - Form validation and duplicate account checks

- **Book Listing for Sellers**
  - Add books with title, author, publish date, original price, and condition
  - Upload book cover photo
  - Automatically calculates adjusted selling price based on condition:
    - *Like New* → 90% of original price  
    - *Moderately Used* → 75%  
    - *Heavily Used* → 50%

- **Real-Time Book Management**
  - View current listings
  - Delist books with one click
  - Total price calculation across listings

- **User Interface**
  - Built with JavaFX and styled with custom CSS
  - Responsive layout with intuitive navigation and error handling

---

## Technologies Used

- Java 17
- JavaFX (UI components)
- SceneBuilder (for layout design)
- CSS (JavaFX styling)
- Mocked SQL integration for demo
---
## How to Run the Project Locally
Make sure having Java 17+ and JavaFX SDK installed before running the app.

Prerequisites
- Java 17 or higher installed
- JavaFX SDK (Download: https://openjfx.io)
- IDE like IntelliJ IDEA or Eclipse (with JavaFX support)

Steps to Run
1. Clone the repository
- git clone https://github.com/Chen-Connie/BargainBooks.git
- cd BargainBooks

2. Open the project in your preferred IDE (e.g., IntelliJ or Eclipse)

3. Configure JavaFX libraries:

- Add your JavaFX SDK path to the project/module settings

- Add VM options to the run configuration:
  - --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml

4. Run the main application file
- Usually named Main.java or located under /src



