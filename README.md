# SunDevil Book Buying and Selling System

A JavaFX-based desktop application designed for Arizona State University (ASU) students to buy and sell used textbooks. This platform offers an affordable, student-driven marketplace with secure account creation, categorized listings, and condition-based price adjustments.

> Developed as part of a semester-long software engineering course project.

---

## Features

- **User Authentication**
  - Create an account using ASU ID
  - Form validation and duplicate ID checks

- **Book Listings for Sellers**
  - Add books with title, author, publish date, price, and condition
  - Upload cover photos
  - Automatic price adjustment based on condition:
    - Like New → 90%
    - Moderately Used → 75%
    - Heavily Used → 50%

- **Buyer View**
  - Browse books by category
  - View pricing and book condition
  - Add to cart / purchase directly

- **Admin Functions**
  - Remove inappropriate listings
  - View all users and transactions

- **SceneBuilder Support**
  - UI designed with JavaFX + SceneBuilder
  - FXML-based layout

---

## Project Structure

```
SunDevilBookSystem/
├── src/
│   ├── controllers/          # JavaFX controllers
│   ├── models/               # Book, User, Admin, etc.
│   ├── views/                # FXML files
│   └── Main.java             # Entry point
├── assets/                   # Book cover images
├── data/                     # Text-based storage or SQL DB
└── README.md
```

---

## How to Run the Project Locally

Make sure you have **Java 17+** and **JavaFX SDK** installed before running the app.

### Prerequisites

- Java 17 or higher
- JavaFX SDK ([Download here](https://openjfx.io))
- IDE like IntelliJ IDEA or Eclipse

---

### Steps

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-username/SunDevil-Book-System.git
   cd SunDevil-Book-System
   ```

2. **Open the Project in your IDE**

   - Import as a Gradle/Maven or standard Java project

3. **Configure JavaFX**

   - Add JavaFX library to your module settings
   - Add VM options:
     ```
     --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
     ```

4. **Run the App**

   - Execute `Main.java` in your IDE

---

## Testing

Includes basic functional testing of login, listing creation, and book filtering.

---

## Authors

Developed by a student team at **Arizona State University**.  
Maintained by **Man-Ning Chen** for portfolio and demonstration purposes.

---

## License

For educational and non-commercial use only. Please contact for reuse permissions.
