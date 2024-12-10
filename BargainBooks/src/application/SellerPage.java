package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.SnapshotParameters;
import java.io.InputStream;


public class SellerPage {
    private static ImageView bookImageView;
    private static File selectedImageFile;
    private static String filePath1 = "pricemultiplier.txt";
    private static List<String[]> userData1 = new ArrayList<>();
    private static String filePath2 = "availableTransaction.txt";
    private static List<String[]> userData2 = new ArrayList<>();
    private static String filePath3 = "pricerange.txt";
    private static List<String[]> userData3 = new ArrayList<>();
      
    
    private static void handleImageUpload(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Book Photo");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                Image image = new Image(new FileInputStream(file));
                bookImageView.setImage(image);
                selectedImageFile = file;
            } catch (Exception e) {
                showAlert("Error", "Failed to load the selected image");
                setDefaultPlaceholder();
            }
        }
    }

    private static void setDefaultPlaceholder() {
        Rectangle placeholder = new Rectangle(200, 200);
        placeholder.setFill(Color.LIGHTGRAY);
        
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        
        Image placeholderImage = placeholder.snapshot(parameters, null);
        bookImageView.setImage(placeholderImage);
    }


    public static void display(Stage primaryStage, String asuId) {
        primaryStage.setTitle("SunDevil Secondhand Book Exchange");

    
        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: white;");

      
        HBox header = new HBox(10);
        header.setPadding(new Insets(10, 20, 10, 20));
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: white; -fx-border-color: transparent;");

   
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
                InputStream stream = SellerPage.class.getResourceAsStream(path);
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
                logo.setFitHeight(90);
                logo.setFitWidth(250);
                logo.setPreserveRatio(true);
            }
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }


        VBox authContainer = new VBox(0.05); 
        authContainer.setAlignment(Pos.TOP_RIGHT);
        HBox.setHgrow(authContainer, Priority.ALWAYS);

        Label welcomeLabel = new Label("Welcome " + asuId);
        welcomeLabel.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-text-fill: #666666; " +
            "-fx-border-color: black; " +   
            "-fx-border-radius: 3px; " +    
            "-fx-padding: 5 10; " +         
            "-fx-background-radius: 3px;"     
        );

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle(
            "-fx-background-color: #8B0000;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 5 15;" +
            "-fx-border-color: black; " +    
            "-fx-border-radius: 3px; " +     
            "-fx-background-radius: 3px; " + 
            "-fx-cursor: hand;" +
            "-fx-font-size: 14px"
        );

        authContainer.getChildren().addAll(welcomeLabel, logoutBtn);

       
        if (logo != null) {
            header.getChildren().addAll(logo, authContainer);
        } else {
            header.getChildren().add(authContainer);
        }

      
        HBox mainLayout = new HBox(20);
        mainLayout.setPadding(new Insets(0, 20, 20, 20));

  
        VBox navPanel = new VBox(0.05); 
        Button sellingBtn = createNavButton("Selling", true);
        Button buyingBtn = createNavButton("Buying", false);

  
        navPanel.setPadding(new Insets(10, 0, 0, 0)); 
        navPanel.getChildren().addAll(sellingBtn, buyingBtn);
        navPanel.setMaxWidth(150);  
        navPanel.setAlignment(Pos.TOP_LEFT);
        

        VBox formPanel = new VBox(15);
        formPanel.setPadding(new Insets(20));
        formPanel.setPrefWidth(600);
        

        VBox greyContainer = new VBox(15);
        greyContainer.setStyle(
            "-fx-background-color: #f5f5f5;" +
            "-fx-background-radius: 0;"
        );
        greyContainer.setPadding(new Insets(20));


        VBox imageContainer = new VBox();
        imageContainer.setPrefWidth(250);
        imageContainer.setPrefHeight(250);
        imageContainer.setStyle(
            "-fx-background-color: #D3D3D3;" +
            "-fx-background-radius: 0;"
        );
        imageContainer.setAlignment(Pos.CENTER);

     
        bookImageView = new ImageView();
        bookImageView.setFitWidth(200);
        bookImageView.setFitHeight(200);
        bookImageView.setPreserveRatio(true);


        Button uploadButton = new Button("Upload Photo");
        uploadButton.setStyle(
            "-fx-background-color: #8B0000;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 8 15;" +
            "-fx-border-color: black;" +      
            "-fx-border-radius: 3px;" +       
            "-fx-background-radius: 3px;" +   
            "-fx-cursor: hand;" +
            "-fx-font-size: 12px"
        );

       
        setDefaultPlaceholder();

       
        uploadButton.setOnAction(e -> handleImageUpload(primaryStage));

      
        VBox photoBox = new VBox(10);
        photoBox.setAlignment(Pos.CENTER);
        photoBox.getChildren().addAll(bookImageView, uploadButton);
        imageContainer.getChildren().add(photoBox);
        
     
        VBox fieldsContainer = new VBox(15);
        fieldsContainer.setPadding(new Insets(0, 0, 0, 20));


        TextField titleField = new TextField();
        titleField.setPromptText("Input Book Title Here");
        styleTextField(titleField);

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.setPromptText("Select Category");
        categoryBox.getItems().addAll("Math", "Natural Science", "Computers", "English Language", "Other");
        styleComboBox(categoryBox);
        
        TextField authorField = new TextField();
        authorField.setPromptText("Input Author");
        styleTextField(authorField);
        
        TextField yearField = new TextField();
        yearField.setPromptText("Input Year");
        styleTextField(yearField);
        

        ComboBox<String> conditionBox = new ComboBox<>();
        conditionBox.setPromptText("Select Condition");
        conditionBox.getItems().addAll("Like New", "Moderately Used", "Heavily Used");
        styleComboBox(conditionBox);

        TextField priceField = new TextField();
        priceField.setPromptText("Input Original Price");
        styleTextField(priceField);


        HBox listingRow = new HBox(10);
        Button listButton = new Button("List My Book");  
        listButton.setPrefWidth(200);  
        listButton.setStyle(
            "-fx-background-color: #8B0000;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 10 5;" +
            "-fx-border-color: black;" +     
            "-fx-border-radius: 3px;" +      
            "-fx-background-radius: 3px;" +    
            "-fx-cursor: hand;" +
            "-fx-font-size: 14px"
        );

        TextField generatedPriceField = new TextField();
        generatedPriceField.setPromptText("Auto Generated Price");
        generatedPriceField.setEditable(false);
        generatedPriceField.setPrefWidth(230);
        styleTextField(generatedPriceField);
       

        listingRow.getChildren().addAll(listButton, generatedPriceField);

  
        fieldsContainer.getChildren().addAll(
            titleField,
            authorField,
            yearField,
            categoryBox,
            conditionBox,
            priceField
        );
  
        HBox contentContainer = new HBox(); 
        contentContainer.getChildren().addAll(imageContainer, fieldsContainer);
        formPanel.getChildren().add(contentContainer);
        
        
        
        listingRow.setPadding(new Insets(20, 0, 0, 0));  
        formPanel.getChildren().add(listingRow);  
    
  
        VBox listingsPanel = new VBox(10);
        listingsPanel.setPadding(new Insets(15));
        listingsPanel.setPrefWidth(300);

       
        HBox titleContainer = new HBox();
        titleContainer.setStyle(
            "-fx-border-color: #D94F00;" +  
            "-fx-border-width: 1;" +       
            "-fx-border-radius: 5;" +
            "-fx-padding: 10;"
        );

        Label listingsTitle = new Label("My Current Listings");
        listingsTitle.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;"
        );
        titleContainer.getChildren().add(listingsTitle);

     
        VBox listingsContainer = new VBox(10);
        listingsContainer.setStyle(
            "-fx-border-color: black;" +    
            "-fx-border-width: 1;" +
            "-fx-border-radius: 5;" +
            "-fx-padding: 10;" +
            "-fx-margin-top: 10;"  
        );

       
        VBox listings = new VBox(10);
        HBox header1 = new HBox(10);
        userData2.clear();
        listings.getChildren().clear();
    	header1.setPadding(new Insets(10));
    	Label infoHead = new Label("Book Title");
    	infoHead.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        infoHead.setPrefWidth(100);
        infoHead.setAlignment(Pos.CENTER);
        
        Label infoHead1 = new Label("Earning");
    	infoHead1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        infoHead1.setPrefWidth(50);
        infoHead1.setAlignment(Pos.CENTER);
        
        header1.getChildren().addAll(infoHead1,infoHead);
        listings.getChildren().add(header1);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath2))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	if (line.trim().isEmpty()) continue;
                userData2.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        double totalEarning = 0.0;
        for(int i = 0; i < userData2.size();i++) {
        	String[] userinformation = userData2.get(i);
            String sellerId = userinformation[1];
            String buyerId = userinformation[2];
            String bookTitle = userinformation[3];
            String earning = userinformation[9];
            
        if(sellerId.equals(asuId) && buyerId.equals(String.valueOf(0))) {
        	 try {
                 double earning1 = Double.parseDouble(earning);
                 totalEarning += earning1;
             } catch (NumberFormatException e) {
                 e.printStackTrace();
             }
        	 
        	Label infoHead7 = new Label(bookTitle);
	    	infoHead7.setStyle("-fx-font-size: 12px;");
            infoHead7.setPrefWidth(100);
            infoHead7.setAlignment(Pos.CENTER);
            
            Label infoHead8 = new Label("$" + earning);
	    	infoHead8.setStyle("-fx-font-size: 12px;");
            infoHead8.setPrefWidth(50);
            infoHead8.setAlignment(Pos.CENTER);
            
            Button delete = new Button("Delist");
            delete.setAlignment(Pos.CENTER);
            delete.setPadding(new Insets(0,0,10,0));
	        delete.setStyle("-fx-background-color: red; -fx-text-fill: white;"
	        		+ "-fx-pref-width: 50px;"
	        		+ "-fx-pref-height: 15px;");
	        
	        delete.setOnAction(event -> {
	        	int b = 1;
	        	userinformation[2] = String.valueOf(b);
	            
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath2))) {
	                for (String[] data : userData2) {
	                    writer.write(String.join(",", data));
	                    writer.newLine();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setHeaderText("Success deleted");
	            alert.setContentText("This available transaction is successfully deleted.");
	            alert.showAndWait();
	            userData2.clear();
	            listings.getChildren().clear();
	            SellerPage.display(primaryStage, asuId);
	        });
	        
            HBox row = new HBox(10);
            row.setPadding(new Insets(20,0,0,0));
            row.setStyle(
            	    "-fx-border-color: black;" +
            	    "-fx-border-width: 1px;"
            	);
            row.getChildren().addAll(infoHead8,infoHead7,delete);
            listings.getChildren().add(row);
    }
        }
        ScrollPane scrollPane = new ScrollPane(listings);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.setPrefHeight(350);
      
        Label totalPrice = new Label("Total Will Earn:");
        totalPrice.setStyle(
            "-fx-font-weight: bold;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10 0 0 0;" +
            "-fx-border-color: #E8E8E8;" +
            "-fx-border-width: 1 0 0 0;"
        );
        totalPrice.setText(String.format("Total Will Earn: $%.2f", totalEarning));
    
        listingsContainer.getChildren().addAll(scrollPane, totalPrice);
        listingsPanel.getChildren().addAll(titleContainer, listingsContainer);
        mainLayout.getChildren().addAll(navPanel, formPanel, listingsPanel);
        root.getChildren().addAll(header, mainLayout);

     
        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	if (line.trim().isEmpty()) continue;
                userData1.add(line.split(","));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        
        listButton.setOnAction(e -> {
        	String selectedCategory = categoryBox.getValue().trim();
        	String selectedCondition = conditionBox.getValue().trim();
        	String title =  titleField.getText().trim();
            String author = authorField.getText().trim();
            String year = yearField.getText().trim();
            String price = priceField.getText().trim();
            double sellPrice = 0;
            
            
            if (categoryBox.getValue() == null || categoryBox.getValue().trim().isEmpty() ||
            		conditionBox.getValue() == null || conditionBox.getValue().trim().isEmpty() ||
            		titleField.getText() == null || titleField.getText().trim().isEmpty() ||
            		authorField.getText() == null || authorField.getText().trim().isEmpty() ||
            		yearField.getText() == null || yearField.getText().trim().isEmpty() ||
            		priceField.getText() == null || priceField.getText().trim().isEmpty()) {
                	
                	Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Incomplete Data");
                    alert.setContentText("All fields must be filled before listing.");
                    alert.showAndWait();
                    return;
                }
            
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath3))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	            	if (line.trim().isEmpty()) continue;
	                userData3.add(line.split(","));
	            }
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
	        
	        String[] priceinformation = userData3.get(0);
        	String min = priceinformation[0];
        	String max = priceinformation[1];
        	
        	double value1 = Double.valueOf(price.toString());
            double value2 = Double.valueOf(min.toString());
            double value3 = Double.valueOf(max.toString());
            
            if (value1 < value2 || value1 > value3) {
            	String message = String.format("Please enter a valid book price between %.2f and %.2f.", value2, value3);
                showAlert("Invalid BookPrice",message);
                return;
            }
            
            for (int i = 0; i < userData1.size(); i++) {
                String[] userInformation = userData1.get(i);
                String category = userInformation[0];
                double condition1 = Double.parseDouble(userInformation[1]);
                double condition2 = Double.parseDouble(userInformation[2]);
                double condition3 = Double.parseDouble(userInformation[3]);
                if (selectedCategory.equals(category)) {
                    switch (selectedCondition) {
                        case "Like New":
                            sellPrice = condition1 * value1;
                            break;
                        case "Moderately Used":
                            sellPrice = condition2 * value1;
                            break;
                        case "Heavily Used":
                            sellPrice = condition3 * value1;
                            break;
                    }
                    break;
                }
            }
            String a = String.valueOf(sellPrice);
            String b = String.valueOf(userData2.size()+1);
            
       	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("availableTransaction.txt", true))) {
    	        String newUser = String.join(",",b,asuId, "0", title, author, year, selectedCategory, selectedCondition, price, a);
    	        writer.write(newUser);
    	        writer.newLine();
    	    } catch (IOException ex) {
    	        ex.printStackTrace();
    	        showAlert("Error", "Failed to save user data.");
    	    }
       	    
       	 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
         alert1.setHeaderText("Success list");
         alert1.setContentText("The book has been listed.");
         alert1.showAndWait(); 
       	 SellerPage.display(primaryStage, asuId);
            
            categoryBox.setValue(null);
            conditionBox.setValue(null);
            titleField.clear();
            authorField.clear();
            yearField.clear();
            priceField.clear();
        });


        logoutBtn.setOnAction(e -> {
        	primaryStage.close();
            new LoginPage().start(primaryStage);
        });
        
        //Switch Page Functionality - Button
        sellingBtn.setOnAction(e -> switchView(primaryStage, asuId));
        buyingBtn.setOnAction(e -> switchView(primaryStage, asuId));
        
    }
    
    private static void switchView(Stage pStage, String asuIDNum) {BuyerPage.display(pStage, asuIDNum);}

    private static void styleTextField(Control field) {
        field.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 8;" +
            "-fx-pref-width: 350px;" +
            "-fx-font-size: 14px"
        );
        field.setFocusTraversable(false);
    }

    private static void styleComboBox(ComboBox<?> box) {
        box.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 8;" +
            "-fx-pref-width: 350px;" +
            "-fx-font-size: 14px"
        );
        box.setFocusTraversable(false);
    }

    private static Button createNavButton(String text, boolean isActive) {
        Button button = new Button(text);
        button.setPrefWidth(130);  
        button.setPrefHeight(15);  
        
        
        String style;
        if (isActive) {
            style = "-fx-background-color: white;" +
                    "-fx-text-fill: black;" +
                    "-fx-border-color: black;" + 
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 3;" +    
                    "-fx-background-radius: 3;";   
        } else {
            style = "-fx-background-color: #8B0000;" +  
                    "-fx-text-fill: white;" +
                    "-fx-border-color: black;" +   
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 3;" +      
                    "-fx-background-radius: 3;";    
        }
        
        button.setStyle(style + "-fx-padding: 5 15; -fx-font-size: 12px;");
        return button;
    }

    
    private static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    

}
