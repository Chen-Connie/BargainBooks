package application;


import java.io.FileInputStream;
import java.io.InputStream;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuyerPage {
    private static String filePath2 = "availableTransaction.txt";
    private static List<String[]> userData2 = new ArrayList<>();
    
    public static void display(Stage primaryStage, String asuId) {
    	Stage buyerStage = primaryStage;
        BorderPane root = new BorderPane();
        // Add Logo
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
        Label title = new Label("Bargain Books");
        title.setStyle("-fx-font-size: 18px; -fx-text-fill: #8b0000;");
        
        
        Button sellingButton = createNavButton("Selling", false);
        Button buyingButton = createNavButton("Buying", true);
        VBox topleft = new VBox(0);
        topleft.getChildren().addAll(logo,sellingButton, buyingButton);
        // Left Filters
        VBox filters = new VBox(10);
        filters.setPadding(new Insets(10));
        Label Category = new Label("Category of Book");
        Category.setStyle(
                "-fx-font-size: 10px; " +
                "-fx-text-fill: black; " +
                "-fx-border-color: red; " +    
                "-fx-border-radius: 3px; " + 
                "-fx-padding: 5 30; " + 
                "-fx-background-radius: 3px;"
            );
        Label Condition = new Label("Condition of Book");
        Condition.setStyle(
                "-fx-font-size: 10px; " +
                "-fx-text-fill: black; " +
                "-fx-border-color: red; " +    
                "-fx-border-radius: 3px; " + 
                "-fx-padding: 5 30; " + 
                "-fx-background-radius: 3px;"
            );
        
        CheckBox box1 = new CheckBox("Math");
		CheckBox box2 = new CheckBox("Natural Science");
		CheckBox box3 =new CheckBox("Computers");
		CheckBox box4 =new CheckBox("English Language");
		CheckBox box5 = new CheckBox("Other");
		CheckBox box6 = new CheckBox("Used Like New");
		CheckBox box7 =new CheckBox("Moderately Used");
		CheckBox box8 =new CheckBox("Heavily Used");
        filters.getChildren().addAll(
        	Category,
        	box1,
        	box2,
        	box3,
        	box4,
        	box5,
            Condition,
            box6,
            box7,
            box8           
        );
        root.setLeft(filters);
  
        //Left
        VBox Left = new VBox(20);
        Left.setPadding(new Insets(10));
        Left.setAlignment(Pos.CENTER_LEFT);

        Left.getChildren().addAll(
        	topleft,
        	filters
        );
        root.setTop(Left);
        
        
        
        // top right
        VBox topright = new VBox(5); 
        topright.setAlignment(Pos.TOP_RIGHT);
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
        topright.getChildren().addAll(welcomeLabel, logoutBtn);
        

        //Shopping Cart
        VBox cart = new VBox(80);
        cart.setPadding(new Insets(10,10,10,50));
        cart.setStyle("-fx-border-color: darkred; -fx-border-width: 2;");
        root.setRight(cart);
        VBox listings = new VBox(10);
        HBox header1 = new HBox(10);
        userData2.clear();
        listings.getChildren().clear();
        cart.getChildren().clear();
    	header1.setPadding(new Insets(10));
    	Label infoHead = new Label("Book Title");
    	infoHead.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        infoHead.setPrefWidth(100);
        infoHead.setAlignment(Pos.CENTER);
        
        Label infoHead1 = new Label("Spending");
    	infoHead1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        infoHead1.setPrefWidth(75);
        infoHead1.setAlignment(Pos.CENTER);
        
        header1.getChildren().addAll(infoHead1,infoHead);
        listings.getChildren().add(header1);
        int a = 0;
        String buyerId = null;
        String transactionId = null;
        String sellerId = null;
        String bookTitle = null;
        String author = null;
        String year = null;
        String category = null;
        String condition = null;
        String spending = null;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath2))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	if (line.trim().isEmpty()) continue;
                userData2.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        double totalSpending = 0.0;
        for(int i = 0; i < userData2.size();i++) {
        	String[] userinformation = userData2.get(i);
        	transactionId = userinformation[0];
            sellerId = userinformation[1];
            buyerId = userinformation[2];
            bookTitle = userinformation[3];
            spending = userinformation[9];
            double value1 = Double.valueOf(spending.toString());
            value1 = value1 * 1.05;
            String spending1 = String.valueOf(value1);
            if(!sellerId.equals(asuId) && buyerId.equals(String.valueOf(2))) {
           	 try {
                    double spending11 = Double.parseDouble(spending1);
                    totalSpending += spending11;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
           	Label infoHead22 = new Label(bookTitle);
	    	infoHead22.setStyle("-fx-font-size: 12px;");
            infoHead22.setPrefWidth(100);
            infoHead22.setAlignment(Pos.CENTER);
            
            Label infoHead35 = new Label("$" + spending1);
	    	infoHead35.setStyle("-fx-font-size: 12px;");
            infoHead35.setPrefWidth(50);
            infoHead35.setAlignment(Pos.CENTER);
            
            Button delete = new Button("Remove");
            delete.setAlignment(Pos.CENTER);
            delete.setPadding(new Insets(0,0,10,0));
	        delete.setStyle("-fx-background-color: red; -fx-text-fill: white;"
	        		+ "-fx-pref-width: 50px;"
	        		+ "-fx-pref-height: 15px;");
	        
	        delete.setOnAction(event -> {
	        	int b = 0;
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
	            alert.setHeaderText("Success removed");
	            alert.setContentText("This available transaction is successfully removed.");
	            alert.showAndWait();
	            userData2.clear();
	            cart.getChildren().clear();
	            BuyerPage.display(primaryStage, asuId);
	        });
	        
            HBox row = new HBox(10);
            row.setPadding(new Insets(20,0,0,0));
            row.setStyle(
            	    "-fx-border-color: black;" +
            	    "-fx-border-width: 1px;"
            	);
            row.getChildren().addAll(infoHead35,infoHead22,delete);
            listings.getChildren().add(row);
    }
        }
        ScrollPane scrollPane = new ScrollPane(listings);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.setPrefHeight(250);
      
        Label totalPrice = new Label("Total Will Spend:");
        totalPrice.setStyle(
            "-fx-font-weight: bold;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10 0 0 0;" +
            "-fx-border-color: #E8E8E8;" +
            "-fx-border-width: 1 0 0 0;"
        );
        totalPrice.setText(String.format("Total Will Spend: $%.2f", totalSpending));
        cart.getChildren().addAll(scrollPane, totalPrice);
        //right
        VBox right = new VBox(60);
        right.setAlignment(Pos.CENTER_RIGHT);
        right.getChildren().addAll(topright, cart);
        root.setRight(right);
        
        HBox bottom = new HBox(5);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        Button checkout = new Button("Checkout");
        checkout.setAlignment(Pos.CENTER);
        checkout.setPadding(new Insets(0,0,10,0));
        checkout.setStyle("-fx-background-color: blue; -fx-text-fill: white;"
        		+ "-fx-pref-width: 100px;"
        		+ "-fx-pref-height: 50px;");
        checkout.setOnAction(event -> {
        	boolean foundMatchingRecord = false;
            for(int i = 0; i < userData2.size(); i++) {
                String[] userinformation = userData2.get(i);
                String buyerId1 = userinformation[2];
                if(buyerId1.equals(String.valueOf(2))) {
                    userinformation[2] = asuId;
                    foundMatchingRecord = true;
                }
            }
            if (!foundMatchingRecord) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Checkout Error");
                alert.setContentText("No transactions available for checkout. Please add books first.");
                alert.showAndWait();
                return;
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath2))) {
                for (String[] data : userData2) {
                    writer.write(String.join(",", data));
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success checkout");
            alert.setContentText("Successfully checkout.");
            alert.showAndWait();
            userData2.clear();
            cart.getChildren().clear();
            totalPrice.setText("Total Will Spend: $0.00");
            BuyerPage.display(primaryStage, asuId);
        });       
        bottom.getChildren().addAll(checkout);
        root.setBottom(bottom);
        
        // Set Scene
        Scene scene = new Scene(root, 1400, 600); // Adjust size for better fit
        buyerStage.setScene(scene);
        buyerStage.setTitle("Buyer Page");
        buyerStage.show();
     
        // Center - Book Listings
        
        VBox content = new VBox(10);
        content.setStyle("-fx-background-color: white; -fx-border-color: red; "
        		+ "    -fx-border-width: 1px;	");
        HBox box9 = new HBox(5);
        VBox box10 = new VBox(5);
        content.getChildren().clear();
        box9.getChildren().clear();
        box10.getChildren().clear();    
        content.setPadding(new Insets(20));
        HBox header = new HBox(10);
    	header.setPadding(new Insets(0,0,0,40));
    	Label infoHead99 = new Label("Book Title");
    	infoHead99.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        infoHead99.setPrefWidth(100);
        infoHead99.setAlignment(Pos.CENTER);
        
        Label infoHead12 = new Label("Seller");
    	infoHead12.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        infoHead12.setPrefWidth(100);
        infoHead12.setAlignment(Pos.CENTER);
        
        Label infoHead11 = new Label("");
        infoHead11.setPrefWidth(50);
        
        Label infoHead2 = new Label("Book Category");
        infoHead2.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        infoHead2.setAlignment(Pos.CENTER);
        infoHead2.setPrefWidth(100);
        
        Label infoHead3 = new Label("Price for it");
        infoHead3.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        infoHead3.setAlignment(Pos.CENTER);
        infoHead3.setPrefWidth(100);
        
        Label infoHead4 = new Label("Book Condition");
        infoHead4.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        infoHead4.setAlignment(Pos.CENTER);
        infoHead4.setPrefWidth(100);
        
        Label infoHead6 = new Label("Transaction ID");
        infoHead6.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        infoHead6.setAlignment(Pos.CENTER);
        infoHead6.setPrefWidth(100);
        
        header.getChildren().addAll(infoHead12,infoHead6,infoHead99,infoHead3,infoHead2,infoHead4,infoHead11);
        header.getStyleClass().add("textfield2");
        header.setStyle("-fx-pref-width: 750px;");
        box9.getChildren().add(header);
        
        List<String> selectedCategories = new ArrayList<>();
        List<String> selectedConditions = new ArrayList<>();
        ChangeListener<Boolean> filterListener = (observable, oldValue, newValue) -> {
            selectedCategories.clear();
            selectedConditions.clear();
            box10.getChildren().clear();
            if (box1.isSelected()) {
            selectedCategories.add("Math");}
            if (box2.isSelected()) { 
            selectedCategories.add("Natural Science");}
            if (box3.isSelected()) { 
            selectedCategories.add("Computer");}
            if (box4.isSelected()) {
            selectedCategories.add("English");}
            if (box5.isSelected()) {
            selectedCategories.add("Other");}
            if (box6.isSelected()) {
            selectedConditions.add("Used Like New");}
            if (box7.isSelected()) {
            selectedConditions.add("Moderately Used");}
            if (box8.isSelected()) {
            selectedConditions.add("Heavily Used");}

            for(int i = 0; i < userData2.size(); i++) {
                String[] userinformation = userData2.get(i);
            	String transactionId1 = userinformation[0];
            	String sellerId1 = userinformation[1];
            	String buyerId1 = userinformation[2];
            	String bookTitle1 = userinformation[3];
            	String author1 = userinformation[4];
            	String year1 = userinformation[5];
            	String category1 = userinformation[6];
            	String condition1 = userinformation[7];
            	String spending2 = userinformation[9];
                double value1 = Double.valueOf(spending2.toString());
                value1 = value1 * 1.05;
                value1 = Math.round(value1 * 100.0) / 100.0;
                String spending1 = String.valueOf(value1);

                boolean categoryMatch = selectedCategories.isEmpty() || selectedCategories.contains(category1);
                boolean conditionMatch = selectedConditions.isEmpty() || selectedConditions.contains(condition1);
                
                if (categoryMatch && conditionMatch && buyerId1.equals(String.valueOf(a)) && !sellerId1.equals(asuId)) {
                    Label infoHead7 = new Label(bookTitle1);
                    infoHead7.setStyle("-fx-font-size: 12px;");
                    infoHead7.setPrefWidth(100);
                    infoHead7.setAlignment(Pos.CENTER);
                    
                    Button buy = new Button("Buy");
                    buy.setAlignment(Pos.CENTER);
                    buy.setPadding(new Insets(0,0,10,0));
                    buy.setStyle("-fx-background-color: white; -fx-text-fill: red;"
                            + "-fx-pref-width: 100px;"
                            + "-fx-pref-height: 35px;");
                    
                    buy.setOnAction(event -> {
                        userinformation[2] = String.valueOf(2);
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath2))) {
                            for (String[] data : userData2) {
                                writer.write(String.join(",", data));
                                writer.newLine();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Success add to the cart");
                        alert.setContentText("This book has been successfully added.");
                        alert.showAndWait();
                        userData2.clear();
                        content.getChildren().clear();
                        BuyerPage.display(primaryStage, asuId);
                    });
                    
                    Label infoHead9 = new Label(category1);
                    infoHead9.setStyle("-fx-font-size: 12px;");
                    infoHead9.setAlignment(Pos.CENTER);
                    infoHead9.setPrefWidth(100);
                    
                    Label infoHead10 = new Label(spending1);
                    infoHead10.setStyle("-fx-font-size: 12px;");
                    infoHead10.setAlignment(Pos.CENTER);
                    infoHead10.setPrefWidth(100);
                    
                    Label infoHead88 = new Label(condition1);
                    infoHead88.setStyle("-fx-font-size: 12px;");
                    infoHead88.setAlignment(Pos.CENTER);
                    infoHead88.setPrefWidth(100);
                    
                    Label infoHead13 = new Label(transactionId1);
                    infoHead13.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
                    infoHead13.setAlignment(Pos.CENTER);
                    infoHead13.setPrefWidth(100);
                    
                    Label infoHead5 = new Label(sellerId1);
                    infoHead5.setStyle("-fx-font-size: 12px;");
                    infoHead5.setPrefWidth(100);
                    infoHead5.setAlignment(Pos.CENTER);
                    
                    HBox row = new HBox(10);
                    row.getStyleClass().add("textfield2");
                    row.setPadding(new Insets(15,0,0,40));
                    row.getChildren().addAll(infoHead5,infoHead13,infoHead7,infoHead10,infoHead9,infoHead88,buy);
                    box10.getChildren().add(row);
                }
            }
        };

        box1.selectedProperty().addListener(filterListener);
        box2.selectedProperty().addListener(filterListener);
        box3.selectedProperty().addListener(filterListener);
        box4.selectedProperty().addListener(filterListener);
        box5.selectedProperty().addListener(filterListener);
        box6.selectedProperty().addListener(filterListener);
        box7.selectedProperty().addListener(filterListener);
        box8.selectedProperty().addListener(filterListener);
        filterListener.changed(null, null, null);
        
        ScrollPane scrollPane1 = new ScrollPane(box10);
        scrollPane1.setFitToWidth(true);
        scrollPane1.setPannable(true);
        scrollPane1.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scrollPane1, Priority.ALWAYS);
        content.getChildren().addAll(box9,scrollPane1);
        root.setCenter(content);
        
        //final
        HBox end = new HBox(1);
        end.getChildren().addAll(Left,content,right);
        root.setCenter(end);
        
        
        //Switch Page Button Functionality
        sellingButton.setOnAction(e -> switchView(primaryStage, asuId));
        buyingButton.setOnAction(e -> switchView(primaryStage, asuId));
        
        //Logout button
        logoutBtn.setOnAction(e -> {
            new LoginPage().start(primaryStage);
        });
        
        
    }
    
    private static void switchView(Stage pStage, String asuIDNum)
    {
    	SellerPage.display(pStage, asuIDNum);
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
}
