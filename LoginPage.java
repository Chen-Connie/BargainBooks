package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.InputStream;

public class LoginPage extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SunDevil Secondhand Book Exchange");

        // Create main VBox
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: white;");

        // Header with logo and title
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER);
        
        // Try multiple approaches to load the image
        ImageView logo = null;
        try {
            // Try different path formats
            String[] possiblePaths = {
                "/resource/images/logo.png",
                "/images/logo.png",
                "resource/images/logo.png",
                "../resource/images/logo.png"
            };
            
            Image image = null;
            for (String path : possiblePaths) {
                InputStream stream = getClass().getResourceAsStream(path);
                if (stream != null) {
                    image = new Image(stream);
                    System.out.println("Successfully loaded image from: " + path);
                    break;
                }
            }
            
            if (image == null) {
                // If resource stream doesn't work, try direct file path
                try {
                    FileInputStream fileStream = new FileInputStream("src/resource/images/logo.png");
                    image = new Image(fileStream);
                    System.out.println("Successfully loaded image from file path");
                } catch (Exception e) {
                    System.out.println("Could not load image from file: " + e.getMessage());
                }
            }
            
            if (image != null) {
                logo = new ImageView(image);
                logo.setFitHeight(100);
                logo.setFitWidth(100);
                logo.setPreserveRatio(true);
            } else {
                // If image still can't be loaded, create a placeholder
                System.out.println("Using placeholder text instead of logo");
                Text placeholderLogo = new Text("📚");
                placeholderLogo.setFont(Font.font("Arial", FontWeight.BOLD, 40));
                placeholderLogo.setFill(Color.web("#dc3545"));
                logo = new ImageView();
            }
            
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            // Create a placeholder if image loading fails
            Text placeholderLogo = new Text("📚");
            placeholderLogo.setFont(Font.font("Arial", FontWeight.BOLD, 40));
            placeholderLogo.setFill(Color.web("#dc3545"));
            logo = new ImageView();
        }

        // Title
        Text title = new Text("SunDevil Secondhand Book Exchange");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setFill(Color.web("#FF7F50"));

        header.getChildren().addAll(logo, title);

        // Login form container
        VBox loginForm = new VBox(15);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setPadding(new Insets(30));
        loginForm.setMaxWidth(400);
        loginForm.setStyle(
            "-fx-background-color: #f8f9fa;" +
            "-fx-border-color: #dc3545;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;"
        );

        // ASU ID field
        TextField asuIdField = new TextField();
        asuIdField.setPromptText("ASU ID");
        asuIdField.setMaxWidth(300);
        asuIdField.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #ced4da;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 8;"
        );

        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #ced4da;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 8;"
        );

        // Login button
        Button loginButton = new Button("Log in");
        loginButton.setMaxWidth(300);
        loginButton.setStyle(
            "-fx-background-color: #6c757d;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10;" +
            "-fx-border-radius: 3;" +
            "-fx-background-radius: 3;"
        );

        // Add login action
        loginButton.setOnAction(e -> {
            String asuId = asuIdField.getText();
            String password = passwordField.getText();
            
            if (validateLogin(asuId, password)) {
            	 System.out.println("Login successful with ASU ID: " + asuId);
             // Clear the current scene
                primaryStage.getScene().setRoot(new VBox()); // Clear current content
                
                // Call SellerPage display method
                SellerPage.display(primaryStage, asuId);
            } else {
                showAlert("Login Failed", "Invalid ASU ID or password");
            }
        });

        // Add all elements to the login form
        loginForm.getChildren().addAll(asuIdField, passwordField, loginButton);

        // Add everything to the main layout
        mainLayout.getChildren().addAll(header, loginForm);
        mainLayout.setPadding(new Insets(20));

        // Create and show the scene
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean validateLogin(String asuId, String password) {
        return asuId != null && !asuId.isEmpty() && 
               password != null && !password.isEmpty();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}