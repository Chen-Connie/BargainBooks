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

