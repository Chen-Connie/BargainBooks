package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoginPage extends Application {
    private TextField asuIdField;
    private PasswordField passwordField;
    private VBox loginForm;
    private String filePath1 = "information.txt";
    private List<String[]> userData = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SunDevil Secondhand Book Exchange");

        // Create main VBox
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: white");

        // Header with logo and title
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER);
        
        // Logo loading logic
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
                    break;
                }
            }
            
            if (image == null) {
                try {
                    FileInputStream fileStream = new FileInputStream("src/resource/images/logo.png");
                    image = new Image(fileStream);
                } catch (Exception e) {
                    System.out.println("Could not load image from file: " + e.getMessage());
                }
            }
            
            if (image != null) {
                logo = new ImageView(image);
                logo.setFitHeight(80);
                logo.setFitWidth(80);
                logo.setPreserveRatio(true);
            }
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }

        // Title text with shadow
        Text title = new Text("SunDevil Secondhand Book Exchange");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        title.setFill(Color.web("#FF7F50"));  // Coral color
        
        // Add drop shadow effect to title
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.3));  // Semi-transparent black
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        shadow.setRadius(5);
        title.setEffect(shadow);

        if (logo != null) {
            header.getChildren().addAll(logo, title);
        } else {
            header.getChildren().add(title);
        }

        // Login form container
        loginForm = new VBox(15);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setPadding(new Insets(20));
        loginForm.setMaxWidth(400);
        loginForm.setStyle(
            "-fx-background-color: #f8f9fa;" +  // Light gray background
            "-fx-border-color: #dc3545;" +      // Red border
            "-fx-border-width: 1;" +
            "-fx-border-radius: 5"
        );

     // ASU ID field
        asuIdField = new TextField();
        asuIdField.setPromptText("ASU ID");
        asuIdField.setPrefWidth(300);
        asuIdField.setFocusTraversable(false);  // Prevents automatic focus
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
        passwordField.setFocusTraversable(false);  // Prevents automatic focus
        passwordField.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #ced4da;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 8;" +
            "-fx-font-size: 14px"
        );

        // Button container
        HBox buttonContainer = new HBox(20);
        buttonContainer.setAlignment(Pos.CENTER);

        // Create account button
        Button createAccountButton = new Button("Create account");
        createAccountButton.setStyle(
            "-fx-background-color: #6c757d;" +
            "-fx-text-fill: #0645AD;" +    // Blue text
            "-fx-font-size: 14px;" +
            "-fx-padding: 10 20;" +
            "-fx-background-radius: 5"
        );

        // Login button
        Button loginButton = new Button("Log in");
        loginButton.setStyle(
            "-fx-background-color: #6c757d;" +
            "-fx-text-fill: #0645AD;" +    // Blue text
            "-fx-font-size: 14px;" +
            "-fx-padding: 10 20;" +
            "-fx-background-radius: 5"
        );

        buttonContainer.getChildren().addAll(createAccountButton, loginButton);

        // Forget password link
        Hyperlink forgotLink = new Hyperlink("Forget password?");
        forgotLink.setStyle(
            "-fx-text-fill: #0645AD;" +    // Blue text
            "-fx-border-color: transparent;" +
            "-fx-font-size: 14px;" +
            "-fx-underline: true"
        );
        
        VBox forgotContainer = new VBox(forgotLink);
        forgotContainer.setAlignment(Pos.CENTER);

        // Add hover effects
        createAccountButton.setOnMouseEntered(e -> 
            createAccountButton.setStyle(
                "-fx-background-color: #5a6268;" +
                "-fx-text-fill: #0645AD;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20;" +
                "-fx-background-radius: 5"
            )
        );

        createAccountButton.setOnMouseExited(e -> 
            createAccountButton.setStyle(
                "-fx-background-color: #6c757d;" +
                "-fx-text-fill: #0645AD;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20;" +
                "-fx-background-radius: 5"
            )
        );

        loginButton.setOnMouseEntered(e -> 
            loginButton.setStyle(
                "-fx-background-color: #5a6268;" +
                "-fx-text-fill: #0645AD;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20;" +
                "-fx-background-radius: 5"
            )
        );

        loginButton.setOnMouseExited(e -> 
            loginButton.setStyle(
                "-fx-background-color: #6c757d;" +
                "-fx-text-fill: #0645AD;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20;" +
                "-fx-background-radius: 5"
            )
        );
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	if (line.trim().isEmpty()) continue;
                userData.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
            
        // Button actions
        loginButton.setOnAction(e -> {
            String asuId = asuIdField.getText().trim();
            String password = passwordField.getText().trim();
            String check = loginFormatCheck(asuId, password);
            if (!check.equals("good")) {
                showAlert("Error", "Invalid ID or password format.");
                return;
            }

            boolean found = false;
            for (String[] userinformation : userData) {
                String asuId1 = userinformation[0];
                String role = userinformation[1];
                String status = userinformation[2];
                String password1 = userinformation[3];

                if (asuId.equals(asuId1)) {
                    found = true;
                    if (password.equals(password1)) {
                        if ("unblock".equals(status)) {
                            if ("Admin".equals(role)) {
                                AdminPage adminPage = new AdminPage(asuId);
                                adminPage.display();
                            } else if ("User".equals(role)) {
                                primaryStage.getScene().setRoot(new VBox());
                                BuyerPage.display(primaryStage, asuId);
                            }
                        } 
                        else {
                            showAlert("Error", "Your account has been blocked.");
                        }
                    } 
                    else {
                        showAlert("Error", "Incorrect password.");
                    }
                    break;
                }
            }

            if (!found) {
                showAlert("Error", "Account ID does not exist.Please create an account.");
            }
        });


        createAccountButton.setOnAction(e -> {
            try {
                // Clear the current scene
                primaryStage.getScene().setRoot(new VBox());
                // Create new create account scene
                CreateAccount.display(primaryStage);
            } catch (Exception ex) {
                System.out.println("Error loading create account page: " + ex.getMessage());
                showAlert("Error", "Could not load create account page");
            }
        });
        forgotLink.setOnAction(e -> {
            primaryStage.getScene().setRoot(new VBox());
            ForgotPassword.display(primaryStage);
        });

        // Add all elements to login form
        loginForm.getChildren().addAll(
            asuIdField,
            passwordField,
            buttonContainer,
            forgotContainer
        );

        // Add spacing above and below login form
        VBox.setMargin(loginForm, new Insets(50, 0, 0, 0));

        // Add everything to main layout
        mainLayout.getChildren().addAll(header, loginForm);
        mainLayout.setPadding(new Insets(20));

        // Create and show scene
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //Just for checking its the right length and not empty
    private String loginFormatCheck(String asuId, String password) {
        if((asuId == null) || (password == null)) {return "ASU ID and Password cannot be empty";}
        if((asuId.isEmpty()) || (password.isEmpty())) {return "ASU ID and Password cannot be left empty";}
        if((!asuId.matches("\\d{10}"))) {return "You ASU ID must be 10 numbers long";}
        if((8 > password.length()) || (password.length() > 32)) {return "Your password must be between 8 and 32 characters long (inclusive)";}
        return "good";
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

