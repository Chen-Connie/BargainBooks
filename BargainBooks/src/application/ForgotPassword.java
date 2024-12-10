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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ForgotPassword {
	private static String filePath1 = "information.txt";
    private static List<String[]> userData = new ArrayList<>();
    
    public static void display(Stage primaryStage) {

        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: white");

   
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER);

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
                InputStream stream = ForgotPassword.class.getResourceAsStream(path);
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

      
        Text title = new Text("SunDevil Secondhand Book Exchange");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        title.setFill(Color.web("#FF7F50")); 
        
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.3));
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        shadow.setRadius(5);
        title.setEffect(shadow);

        if (logo != null) {
            header.getChildren().addAll(logo, title);
        } else {
            header.getChildren().add(title);
        }

  
        VBox recoveryForm = new VBox(15);
        recoveryForm.setAlignment(Pos.CENTER);
        recoveryForm.setPadding(new Insets(20));
        recoveryForm.setMaxWidth(400);
        recoveryForm.setStyle(
            "-fx-background-color: #f8f9fa;" +
            "-fx-border-color: #dc3545;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 5"
        );

   
        Text formTitle = new Text("Password Recovery");
        formTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

       
        Text instructions = new Text("Please enter your ASU ID and email address. " +
                                   "We will send you instructions to reset your password.");
        instructions.setStyle("-fx-font-size: 14px; -fx-text-alignment: center;");
        instructions.setWrappingWidth(350);

     
        TextField asuIdField = new TextField();
        asuIdField.setPromptText("ASU ID");
        asuIdField.setPrefWidth(300);
        asuIdField.setFocusTraversable(false);
        asuIdField.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #ced4da;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 8;" +
            "-fx-font-size: 14px"
        );

   
        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");
        emailField.setPrefWidth(300);
        emailField.setFocusTraversable(false); 
        emailField.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #ced4da;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 8;" +
            "-fx-font-size: 14px"
        );


        HBox buttonContainer = new HBox(20);
        buttonContainer.setAlignment(Pos.CENTER);

    
        Button backButton = new Button("Back to Login");
        backButton.setStyle(
            "-fx-background-color: #6c757d;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10 20;" +
            "-fx-background-radius: 5"
        );

  
        Button submitButton = new Button("Submit");
        submitButton.setStyle(
            "-fx-background-color: #dc3545;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10 20;" +
            "-fx-background-radius: 5"
        );

        buttonContainer.getChildren().addAll(backButton, submitButton);

     
        backButton.setOnMouseEntered(e -> 
            backButton.setStyle(
                "-fx-background-color: #5a6268;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20;" +
                "-fx-background-radius: 5"
            )
        );

        backButton.setOnMouseExited(e -> 
            backButton.setStyle(
                "-fx-background-color: #6c757d;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20;" +
                "-fx-background-radius: 5"
            )
        );

        submitButton.setOnMouseEntered(e -> 
            submitButton.setStyle(
                "-fx-background-color: #c82333;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20;" +
                "-fx-background-radius: 5"
            )
        );

        submitButton.setOnMouseExited(e -> 
            submitButton.setStyle(
                "-fx-background-color: #dc3545;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20;" +
                "-fx-background-radius: 5"
            )
        );

   
        backButton.setOnAction(e -> new LoginPage().start(primaryStage));
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	if (line.trim().isEmpty()) continue;
                userData.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        submitButton.setOnAction(e -> {
        	String asuId = asuIdField.getText().trim();
        	String emailAddress = emailField.getText().trim();
        	
            if (!validateInputs(asuIdField, emailField)) {
            	return;
            }
            
            boolean found = false;
			for (String[] userinformation : userData) {
                String asuId1 = userinformation[0];
                String email = userinformation[6];

                if (asuId.equals(asuId1)) {
                	found = true;
                    if (emailAddress.equals(email)) {
                        showSuccess("Recovery Email Sent", "You will receive password reset email soon.");
                            primaryStage.close();
                            new LoginPage().start(primaryStage);
                    }
                }
            }
            if (!found) {
                showAlert("Error", "Account ID does not exist.Please create an account.");
            }
        });


        recoveryForm.getChildren().addAll(
            formTitle,
            instructions,
            asuIdField,
            emailField,
            buttonContainer
        );

      
        mainLayout.getChildren().addAll(header, recoveryForm);
        mainLayout.setPadding(new Insets(20));

      
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static boolean validateInputs(TextField asuIdField, TextField emailField) {
        if (asuIdField.getText().trim().isEmpty()) {
            showAlert("Missing ASU ID", "Please enter your ASU ID");
            return false;
        }
        
        if (emailField.getText().trim().isEmpty()) {
            showAlert("Missing Email", "Please enter your email address");
            return false;
        }
        
        if (!emailField.getText().contains("@")) {
            showAlert("Invalid Email", "Please enter a valid email address");
            return false;
        }
        
        return true;
    }

    private static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static void showSuccess(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
