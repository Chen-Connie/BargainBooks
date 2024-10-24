package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.*;

public class LoginPage extends Application {
    private TextField asuIdField;
    private PasswordField passwordField;
    private VBox loginForm;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SunDevil Secondhand Book Exchange");

        // Create main VBox
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: white");

        // Header with logo and title
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER);
        
        // Try multiple approaches to load the image
        ImageView logo = null;
        try {
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
            }
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }

        // Title with shadow effect
        Text title = new Text("SunDevil Secondhand Book Exchange");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setFill(Color.web("#FF7F50"));
        
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#FF4500"));
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        shadow.setRadius(3);
        title.setEffect(shadow);

        if (logo != null) {
            header.getChildren().addAll(logo, title);
        } else {
            header.getChildren().add(title);
        }

        // Login form container
        loginForm = new VBox(20);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setPadding(new Insets(30));
        loginForm.setMaxWidth(400);
        loginForm.setStyle(
            "-fx-background-color: #f8f9fa;" +
            "-fx-border-color: #dc3545;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5"
        );

        // ASU ID field
        asuIdField = new TextField();
        asuIdField.setPromptText("ASU ID");
        asuIdField.setPrefWidth(300);
        asuIdField.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #ced4da;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 8;" +
            "-fx-font-size: 14px"
        );

        // Password field
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(300);
        passwordField.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #ced4da;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 8;" +
            "-fx-font-size: 14px"
        );

        // Container for Activate link and login button
        HBox actionContainer = new HBox(20);
        actionContainer.setAlignment(Pos.CENTER);

        Hyperlink activateLink = new Hyperlink("Activate or request an ID");
        activateLink.setStyle(
            "-fx-text-fill: #0645AD;" +
            "-fx-border-color: transparent;" +
            "-fx-font-size: 14px;" +
            "-fx-underline: true;" +
            "-fx-padding: 10 0"
        );

        // Login button
        Button loginButton = new Button("Log in");
        loginButton.setPrefWidth(150);
        loginButton.setStyle(
            "-fx-background-color: #6c757d;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-padding: 10 20;" +
            "-fx-background-radius: 5"
        );

        actionContainer.getChildren().addAll(activateLink, loginButton);

        // Forgot password link
        HBox forgotContainer = new HBox();
        forgotContainer.setAlignment(Pos.CENTER);
        
        Hyperlink forgotLink = new Hyperlink("Forgot ID / password?");
        forgotLink.setStyle(
            "-fx-text-fill: #0645AD;" +
            "-fx-border-color: transparent;" +
            "-fx-font-size: 14px;" +
            "-fx-underline: true"
        );
        
        forgotContainer.getChildren().add(forgotLink);

        // Add hover effect for login button
        loginButton.setOnMouseEntered(e -> 
            loginButton.setStyle(
                "-fx-background-color: #5a6268;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;" +
                "-fx-padding: 10 20;" +
                "-fx-background-radius: 5"
            )
        );

        loginButton.setOnMouseExited(e -> 
            loginButton.setStyle(
                "-fx-background-color: #6c757d;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 16px;" +
                "-fx-padding: 10 20;" +
                "-fx-background-radius: 5"
            )
        );

        // Button actions
        loginButton.setOnAction(e -> {
            String asuId = asuIdField.getText();
            String password = passwordField.getText();
            
            if (validateLogin(asuId, password)) {
                System.out.println("Login successful with ASU ID: " + asuId);
                primaryStage.getScene().setRoot(new VBox());
                SellerPage.display(primaryStage, asuId);
            } else {
                showAlert("Login Failed", "Invalid ASU ID or password");
            }
        });

        // Link actions
        activateLink.setOnAction(e -> 
            showAlert("External Link", "This would redirect to ASU ID activation page")
        );
        
        forgotLink.setOnAction(e -> 
            showAlert("External Link", "This would redirect to password recovery page")
        );

        // Add all elements to login form
        loginForm.getChildren().addAll(
            asuIdField,
            passwordField,
            actionContainer,
            forgotContainer
        );

        // Add everything to main layout
        mainLayout.getChildren().addAll(header, loginForm);
        mainLayout.setPadding(new Insets(20));

        // Create and show scene
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
