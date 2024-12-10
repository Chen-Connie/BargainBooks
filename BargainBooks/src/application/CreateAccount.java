package application;

import javafx.scene.Scene; 
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CreateAccount {
    private static String filePath1 = "information.txt";
    private static List<String[]> userData = new ArrayList<>();
    
    public static void display(Stage primaryStage) {
        try {
           
            VBox mainLayout = new VBox(20);
            mainLayout.setAlignment(Pos.TOP_CENTER);
            mainLayout.setStyle("-fx-background-color: #f0f2f5");  

      
            HBox header = new HBox(10);
            header.setAlignment(Pos.CENTER);
            header.setPadding(new Insets(20, 0, 40, 0));
            
         
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
                    InputStream stream = CreateAccount.class.getResourceAsStream(path);
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

   
            VBox createAccountForm = new VBox(15);
            createAccountForm.setAlignment(Pos.TOP_CENTER);
            createAccountForm.setPadding(new Insets(20));
            createAccountForm.setMaxWidth(600);
            createAccountForm.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #dc3545;" +  
                "-fx-border-width: 1;" +
                "-fx-border-radius: 5"
            );

     
            Text formTitle = new Text("Create an account");
            formTitle.setStyle("-fx-font-size: 16px; -fx-fill: #0645AD;");
            createAccountForm.setAlignment(Pos.CENTER);  
            
       
            TextField asuIdField = createFormField("ASU ID");
            TextField usernameField = createFormField("UserName");
            TextField nameField = createFormField("Name");
            TextField emailField = createFormField("Email Address");
            PasswordField passwordField = createPasswordField("Password");

        
            HBox buttonContainer = new HBox(20);
            buttonContainer.setAlignment(Pos.CENTER);
            buttonContainer.setPadding(new Insets(20, 0, 0, 0));

   
            Button cancelButton = new Button("Cancel");
            cancelButton.setPrefWidth(150);
            cancelButton.setStyle(
                "-fx-background-color: #FFD700;" + 
                "-fx-text-fill: #666666;" +        
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20;" +
                "-fx-background-radius: 5"
            );

         
            Button confirmButton = new Button("Confirm");
            confirmButton.setPrefWidth(150);
            confirmButton.setStyle(
                "-fx-background-color: #FFB6C1;" +  
                "-fx-text-fill: #666666;" +         
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20;" +
                "-fx-background-radius: 5"
            );

        
            cancelButton.setOnMouseEntered(e -> 
                cancelButton.setStyle(
                    "-fx-background-color: #FFE44D;" + 
                    "-fx-text-fill: #666666;" +
                    "-fx-font-size: 14px;" +
                    "-fx-padding: 10 20;" +
                    "-fx-background-radius: 5"
                )
            );

            cancelButton.setOnMouseExited(e -> 
                cancelButton.setStyle(
                    "-fx-background-color: #FFD700;" + 
                    "-fx-text-fill: #666666;" +
                    "-fx-font-size: 14px;" +
                    "-fx-padding: 10 20;" +
                    "-fx-background-radius: 5"
                )
            );

            confirmButton.setOnMouseEntered(e -> 
                confirmButton.setStyle(
                    "-fx-background-color: #FFC0CB;" +  
                    "-fx-text-fill: #666666;" +
                    "-fx-font-size: 14px;" +
                    "-fx-padding: 10 20;" +
                    "-fx-background-radius: 5"
                )
            );

            confirmButton.setOnMouseExited(e -> 
                confirmButton.setStyle(
                    "-fx-background-color: #FFB6C1;" +  
                    "-fx-text-fill: #666666;" +
                    "-fx-font-size: 14px;" +
                    "-fx-padding: 10 20;" +
                    "-fx-background-radius: 5"
                )
            );

     
            cancelButton.setOnAction(e -> {

                new LoginPage().start(primaryStage);
            });
            
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
                String line;
                while ((line = reader.readLine()) != null) {
                	if (line.trim().isEmpty()) continue;
                    userData.add(line.split(","));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            confirmButton.setOnAction(e -> {
            	String asuId = asuIdField.getText().trim();
            	String userName = usernameField.getText().trim();
            	String name = nameField.getText().trim();
            	String emailAddress = emailField.getText().trim();
                String password = passwordField.getText().trim();
                
                if((!asuId.matches("\\d{10}"))) {showAlert("Error", "Incorrect Id,need have 10 digits."); return;}
                if((8 > password.length()) || (password.length() > 32)) {showAlert("Error", "Your password must be between 8 and 32 characters long (inclusive)");return;}
                if (!emailField.getText().contains("@")) {
                    showAlert("Invalid Email", "Please enter a valid email address");
                    return;
                }
                
                boolean idExists = false;
                for (String[] userinformation : userData) {
                    String asuId1 = userinformation[0];                   
                   if (asuId.equals(asuId1)) {
                        idExists = true;
                        break;
                    }
                }
                
                if (idExists) {
                    showAlert("Error", "ID already exists.");
                    return;
                }
                if (!validateFields(asuIdField, usernameField, nameField, emailField, passwordField)) {
                    showAlert("Error", "Need to fill all the textfields.");
                    return;
                }
                
                	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("information.txt", true))) {
                	        String newUser = String.join(",",asuId, "User", "unblock", password, userName, name, emailAddress);
                	        writer.write(newUser);
                	        writer.newLine();
                	    } catch (IOException ex) {
                	        ex.printStackTrace();
                	        showAlert("Error", "Failed to save user data.");
                	    }
                    showSuccess("Success", "Account created successfully");
                    primaryStage.close();
                    new LoginPage().start(primaryStage);
            });

            buttonContainer.getChildren().addAll(cancelButton, confirmButton);


            createAccountForm.getChildren().addAll(
                formTitle,
                asuIdField,
                usernameField,
                nameField,
                emailField,
                passwordField,
                buttonContainer
            );

  
            mainLayout.getChildren().addAll(header, createAccountForm);
            mainLayout.setPadding(new Insets(0, 20, 20, 20));

     
            Scene scene = new Scene(mainLayout, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Error in CreateAccount: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static TextField createFormField(String promptText) {
        TextField field = new TextField();
        field.setPromptText(promptText);
        field.setPrefWidth(500);
        field.setFocusTraversable(false);
        field.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #ced4da;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 8;" +
            "-fx-font-size: 14px"
        );
        return field;
    }

    private static PasswordField createPasswordField(String promptText) {
        PasswordField field = new PasswordField();
        field.setPromptText(promptText);
        field.setPrefWidth(500);
        field.setFocusTraversable(false);
        field.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #ced4da;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 8;" +
            "-fx-font-size: 14px"
        );
        return field;
    }

    private static boolean validateFields(TextField... fields) {
        for (TextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                return false;
            }
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
