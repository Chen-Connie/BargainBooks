package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class AdminPage{
    private String asuId;
    private Button currentActiveButton;
    private VBox mainview;
    private String filePath1 = "information.txt";
    private List<String[]> userData = new ArrayList<>();
    private String filePath2 = "availableTransaction.txt";
    private List<String[]> userData1 = new ArrayList<>();
    
    
    public AdminPage(String asuId) {
    	this.asuId = asuId;
    }
    
    public void display() {
        Stage formStage = new Stage();
        BorderPane root = new BorderPane();

//TOP PART
        URL imageUrl = getClass().getResource("/images/logo.png");
        Image image = new Image(imageUrl.toExternalForm());
        ImageView logo = new ImageView(image);
        logo.setFitWidth(150);
        logo.setFitHeight(150);
        logo.setPreserveRatio(true);
        
        Label title = new Label("SunDevil Secondhand Book Exchange");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        title.setTextFill(Color.web("#FF7F50")); 
        title.setPrefWidth(500);
        title.setPadding(new Insets(0,0,0,30));
        // Add drop shadow effect to title
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.3));
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        shadow.setRadius(5);
        title.setEffect(shadow);
        
        VBox authContainer = new VBox(2); 
        authContainer.setPadding(new Insets(0,30,0,0));
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
            "-fx-pref-width: 90px;" +
            "-fx-pref-height: 35px;"
            + 
            "-fx-alignment: center;"
        );
        authContainer.getChildren().addAll(welcomeLabel, logoutBtn);
        
        HBox top = new HBox(5);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(20, 0, 20, 0));
        top.getChildren().addAll(logo,title,authContainer);
        root.setTop(top);
        
        logoutBtn.setOnAction(e -> {
        	formStage.close();
        });
//MID PART        
        HBox center = new HBox(10);
        center.setPadding(new Insets(10, 0, 10, 10));
        VBox functionchange = new VBox(5);
        mainview = new VBox(5);
        
        Button PurchaseHistory = new Button("Purchase History");
        Button SellingHistory = new Button("Selling History");
        Button UserAccount = new Button("User Accounts Managment");
        Button SystemConfig = new Button("System Configuration");
        Button BookData = new Button("Book Data");
        Button[] buttons = {PurchaseHistory, SellingHistory, UserAccount, SystemConfig, BookData};
        
        for (Button button : buttons) {
            button.getStyleClass().add("button-inactive");
        }
        
        functionchange.getChildren().addAll(buttons);
        
        PurchaseHistory.setOnAction(e -> {
            updateScene("purchase", PurchaseHistory);
        });
        
        SellingHistory.setOnAction(e -> {
            updateScene("selling", SellingHistory);
        });
        
        UserAccount.setOnAction(e -> {
            updateScene("user", UserAccount);
        });
        
        SystemConfig.setOnAction(e -> {
            updateScene("config", SystemConfig);
        });
        
        BookData.setOnAction(e -> {
            updateScene("book", BookData);
        });
        
        updateScene("purchase", PurchaseHistory);
        center.getChildren().addAll(functionchange,mainview);
        root.setCenter(center);
            
        Scene scene = new Scene(root,1300,700);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		formStage.setScene(scene);
		formStage.show();
    }
    
		private void updateScene(String sceneName, Button clickedButton) {
			if (currentActiveButton != null) {
	            currentActiveButton.getStyleClass().remove("button");
	            currentActiveButton.getStyleClass().add("button-inactive");
	        }
	        clickedButton.getStyleClass().remove("button-inactive");
	        clickedButton.getStyleClass().add("button");
	        currentActiveButton = clickedButton;
	        mainview.getChildren().clear();
	        
	        switch (sceneName) {
	            case "purchase":
	                loadPage1();
	                break;
	            case "selling":
	                loadPage2();
	                break;
	            case "user":
	                loadPage3();
	                break;
	            case "config":
	                loadPage4();
	                break;
	            case "book":
	                loadPage5();
	                break;
	        }
	    }
	    
	    private void loadPage1() {
	        VBox content = new VBox(10);
	        HBox box1 = new HBox(5);
	        VBox box2 = new VBox(5);
	        userData1.clear();
	        content.getChildren().clear();
	        mainview.getChildren().clear();
	        box1.getChildren().clear();
	        box2.getChildren().clear();
	        int a = 0;
	        int b = 1;
	        int c = 2;
	        String buyerId = null;
	        String transactionId = null;
	        String sellerId = null;
            String bookTitle = null;
            String author = null;
            String year = null;
            String category = null;
            String condition = null;
            
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath2))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	            	if (line.trim().isEmpty()) continue;
	                userData1.add(line.split(","));
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        content.setPadding(new Insets(20));
	        box1.setAlignment(Pos.CENTER_RIGHT);
	        box1.setPadding(new Insets(10));
	        Label title = new Label("TAX: 5%");
	        title.setFont(Font.font("", FontWeight.BOLD, 20));
	        title.setTextFill(Color.RED);
	        title.setPadding(new Insets(0, 0, 0, 100));
	        box1.getChildren().addAll(title);
	        
            HBox header = new HBox(10);
	    	header.setPadding(new Insets(0,0,0,40));
	    	Label infoHead = new Label("Book Title");
	    	infoHead.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead.setPrefWidth(100);
            infoHead.setAlignment(Pos.CENTER);
            
            Label infoHead12 = new Label("Buyer");
	    	infoHead12.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead12.setPrefWidth(100);
            infoHead12.setAlignment(Pos.CENTER);
            
            Label infoHead1 = new Label("Author");
            infoHead1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead1.setAlignment(Pos.CENTER);
            infoHead1.setPrefWidth(100);
            
            Label infoHead2 = new Label("Book Category");
            infoHead2.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead2.setAlignment(Pos.CENTER);
            infoHead2.setPrefWidth(100);
            
            Label infoHead3 = new Label("Published Year");
            infoHead3.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead3.setAlignment(Pos.CENTER);
            infoHead3.setPrefWidth(100);
            
            Label infoHead4 = new Label("Book Condition");
            infoHead4.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
            infoHead4.setAlignment(Pos.CENTER);
            infoHead4.setPrefWidth(100);
            
            Label infoHead6 = new Label("Transaction ID");
            infoHead6.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead6.setAlignment(Pos.CENTER);
            infoHead6.setPrefWidth(100);
            
            header.getChildren().addAll(infoHead12,infoHead6,infoHead,infoHead1,infoHead2,infoHead3,infoHead4);
            header.getStyleClass().add("textfield2");
            box2.getChildren().add(header);
	        
            for(int i = 0; i < userData1.size();i++) {
	        	String[] userinformation = userData1.get(i);
	        	transactionId = userinformation[0];
	            sellerId = userinformation[1];
	            buyerId = userinformation[2];
	            bookTitle = userinformation[3];
	            author = userinformation[4];
	            year = userinformation[5];
	            category = userinformation[6];
	            condition = userinformation[7];
	            
            if(!buyerId.equals(String.valueOf(a))&& !buyerId.equals(String.valueOf(b))&& !buyerId.equals(String.valueOf(c))) {	        	
	        	Label infoHead7 = new Label(bookTitle);
		    	infoHead7.setStyle("-fx-font-size: 12px;");
	            infoHead7.setPrefWidth(100);
	            infoHead7.setAlignment(Pos.CENTER);
	            
	            Label infoHead8 = new Label(author);
	            infoHead8.setStyle("-fx-font-size: 12px;");
	            infoHead8.setAlignment(Pos.CENTER);
	            infoHead8.setPrefWidth(100);
	            
	            Label infoHead9 = new Label(category);
	            infoHead9.setStyle("-fx-font-size: 12px;");
	            infoHead9.setAlignment(Pos.CENTER);
	            infoHead9.setPrefWidth(100);
	            
	            Label infoHead10 = new Label(year);
	            infoHead10.setStyle("-fx-font-size: 12px;");
	            infoHead10.setAlignment(Pos.CENTER);
	            infoHead10.setPrefWidth(100);
	            
	            Label infoHead11 = new Label(condition);
	            infoHead11.setStyle("-fx-font-size: 12px;");
	            infoHead11.setAlignment(Pos.CENTER);
	            infoHead11.setPrefWidth(100);
	            
	            Label infoHead13 = new Label(transactionId);
	            infoHead13.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
	            infoHead13.setAlignment(Pos.CENTER);
	            infoHead13.setPrefWidth(100);
	            
	            Label infoHead5 = new Label(buyerId);
		    	infoHead5.setStyle("-fx-font-size: 12px;");
	            infoHead5.setPrefWidth(100);
	            infoHead5.setAlignment(Pos.CENTER);
	            
	            HBox row = new HBox(10);
	            row.getStyleClass().add("textfield2");
	            row.setPadding(new Insets(0,0,0,40));
	            row.getChildren().addAll(infoHead5,infoHead13,infoHead7,infoHead8,infoHead9,infoHead10,infoHead11);
	            box2.getChildren().add(row);
            }
            }
            
            ScrollPane scrollPane = new ScrollPane(box2);
	        scrollPane.setFitToWidth(true);
	        scrollPane.setPannable(true);
	        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	        VBox.setVgrow(scrollPane, Priority.ALWAYS);
	        content.getChildren().addAll(box1,scrollPane);
	        mainview.getChildren().add(content);
	    }
	    
	    private void loadPage2() {
	        VBox content = new VBox(10);
	        HBox box1 = new HBox(5);
	        VBox box2 = new VBox(5);
	        userData1.clear();
	        content.getChildren().clear();
	        mainview.getChildren().clear();
	        box1.getChildren().clear();
	        box2.getChildren().clear();
	        int a = 0;
	        int b = 1;
	        int c = 2;
	        String buyerId = null;
	        String transactionId = null;
	        String sellerId = null;
            String bookTitle = null;
            String author = null;
            String year = null;
            String category = null;
            String condition = null;
           
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath2))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	            	if (line.trim().isEmpty()) continue;
	                userData1.add(line.split(","));
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        content.setPadding(new Insets(20));
	        box1.setAlignment(Pos.CENTER_RIGHT);
	        box1.setPadding(new Insets(10));
	        Label title = new Label("TAX: 5%");
	        title.setFont(Font.font("", FontWeight.BOLD, 20));
	        title.setTextFill(Color.RED);
	        title.setPadding(new Insets(0, 0, 0, 100));
	        box1.getChildren().addAll(title);
	        
            HBox header = new HBox(10);
	    	header.setPadding(new Insets(0,0,0,40));
	    	Label infoHead = new Label("Book Title");
	    	infoHead.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead.setPrefWidth(100);
            infoHead.setAlignment(Pos.CENTER);
            
            Label infoHead12 = new Label("Seller");
	    	infoHead12.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead12.setPrefWidth(100);
            infoHead12.setAlignment(Pos.CENTER);
            
            Label infoHead1 = new Label("Author");
            infoHead1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead1.setAlignment(Pos.CENTER);
            infoHead1.setPrefWidth(100);
            
            Label infoHead2 = new Label("Book Category");
            infoHead2.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead2.setAlignment(Pos.CENTER);
            infoHead2.setPrefWidth(100);
            
            Label infoHead3 = new Label("Published Year");
            infoHead3.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead3.setAlignment(Pos.CENTER);
            infoHead3.setPrefWidth(100);
            
            Label infoHead4 = new Label("Book Condition");
            infoHead4.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
            infoHead4.setAlignment(Pos.CENTER);
            infoHead4.setPrefWidth(100);
            
            Label infoHead6 = new Label("Transaction ID");
            infoHead6.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead6.setAlignment(Pos.CENTER);
            infoHead6.setPrefWidth(100);
            
            header.getChildren().addAll(infoHead12,infoHead6,infoHead,infoHead1,infoHead2,infoHead3,infoHead4);
            header.getStyleClass().add("textfield2");
            box2.getChildren().add(header);
	        
            for(int i = 0; i < userData1.size();i++) {
	        	String[] userinformation = userData1.get(i);
	        	transactionId = userinformation[0];
	            sellerId = userinformation[1];
	            buyerId = userinformation[2];
	            bookTitle = userinformation[3];
	            author = userinformation[4];
	            year = userinformation[5];
	            category = userinformation[6];
	            condition = userinformation[7];
	            
            if(!buyerId.equals(String.valueOf(a))&& !buyerId.equals(String.valueOf(b)) && !buyerId.equals(String.valueOf(c))) {	        	
	        	Label infoHead7 = new Label(bookTitle);
		    	infoHead7.setStyle("-fx-font-size: 12px;");
	            infoHead7.setPrefWidth(100);
	            infoHead7.setAlignment(Pos.CENTER);
	            
	            Label infoHead8 = new Label(author);
	            infoHead8.setStyle("-fx-font-size: 12px;");
	            infoHead8.setAlignment(Pos.CENTER);
	            infoHead8.setPrefWidth(100);
	            
	            Label infoHead9 = new Label(category);
	            infoHead9.setStyle("-fx-font-size: 12px;");
	            infoHead9.setAlignment(Pos.CENTER);
	            infoHead9.setPrefWidth(100);
	            
	            Label infoHead10 = new Label(year);
	            infoHead10.setStyle("-fx-font-size: 12px;");
	            infoHead10.setAlignment(Pos.CENTER);
	            infoHead10.setPrefWidth(100);
	            
	            Label infoHead11 = new Label(condition);
	            infoHead11.setStyle("-fx-font-size: 12px;");
	            infoHead11.setAlignment(Pos.CENTER);
	            infoHead11.setPrefWidth(100);
	            
	            Label infoHead13 = new Label(transactionId);
	            infoHead13.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
	            infoHead13.setAlignment(Pos.CENTER);
	            infoHead13.setPrefWidth(100);
	            
	            Label infoHead5 = new Label(sellerId);
		    	infoHead5.setStyle("-fx-font-size: 12px;");
	            infoHead5.setPrefWidth(100);
	            infoHead5.setAlignment(Pos.CENTER);
	            
	            HBox row = new HBox(10);
	            row.getStyleClass().add("textfield2");
	            row.setPadding(new Insets(0,0,0,40));
	            row.getChildren().addAll(infoHead5,infoHead13,infoHead7,infoHead8,infoHead9,infoHead10,infoHead11);
	            box2.getChildren().add(row);
            }
            }
            
            ScrollPane scrollPane = new ScrollPane(box2);
	        scrollPane.setFitToWidth(true);
	        scrollPane.setPannable(true);
	        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	        VBox.setVgrow(scrollPane, Priority.ALWAYS);
	        content.getChildren().addAll(box1,scrollPane);
	        mainview.getChildren().add(content);
	    }
	    
	    private void loadPage3() {
	    	userData.clear();
	    	VBox content = new VBox(10);
	    	content.getChildren().clear();
	    	mainview.getChildren().clear();
	    	HBox header = new HBox(10);
	    	header.setPadding(new Insets(0,0,0,40));
	    	Label infoHead = new Label("asuId" + "                          " + "role");
	    	infoHead.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
            infoHead.setPrefWidth(300);
            infoHead.setAlignment(Pos.CENTER_LEFT);
            Label infoHead1 = new Label("Block or Unblock");
            infoHead1.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
            infoHead1.setAlignment(Pos.CENTER);
            infoHead1.setPrefWidth(300);
            header.getChildren().addAll(infoHead,infoHead1);
            header.getStyleClass().add("textfield2");
            content.getChildren().add(header);
            
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	            	if (line.trim().isEmpty()) continue;
	                userData.add(line.split(","));
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        for (String[] userinformation : userData) {
	            String asuId = userinformation[0];
	            String role = userinformation[1];
	            String status = userinformation[2];

	            Label infoLabel = new Label(asuId + "                         " + role);
	            infoLabel.setPrefWidth(300);
	            Label infoLabel2 = new Label();
	            infoLabel2.setPrefWidth(65);
	            infoLabel.setAlignment(Pos.CENTER_LEFT);
	            ToggleGroup roleGroup = new ToggleGroup();
	            RadioButton blockButton = new RadioButton("Block");
	            RadioButton unblockButton = new RadioButton("Unblock");
	            blockButton.setToggleGroup(roleGroup);
	            unblockButton.setToggleGroup(roleGroup);

	            if ("block".equals(status)) {
	                blockButton.setSelected(true);
	            } else {
	                unblockButton.setSelected(true);
	            }
	            
	            if ("Admin".equals(role)) {
	                blockButton.setSelected(false);
	                unblockButton.setSelected(false);
	                blockButton.setDisable(true);
	                unblockButton.setDisable(true);
	            }
	            
	            roleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
	                if (newToggle != null) {
	                    RadioButton selected = (RadioButton) newToggle;
	                    userinformation[2] = selected.getText().toLowerCase();
	                }
	            });
	            HBox row = new HBox(10);
	            HBox buttoncheck = new HBox(10);
	            buttoncheck.getChildren().addAll(blockButton,unblockButton);
	            row.getStyleClass().add("textfield2");
	            row.setPadding(new Insets(0,0,0,40));
	            row.getChildren().addAll(infoLabel,infoLabel2,buttoncheck);
	            content.getChildren().add(row);
	    }
	        Button saveButton = new Button("Save");
	        saveButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-weight: bold;"
	        		+ "-fx-pref-width: 100px;"
	        		+ "-fx-pref-height: 50px;");
	        HBox bottom = new HBox(saveButton);
	        bottom.setAlignment(Pos.BOTTOM_RIGHT);
	        bottom.setPadding(new Insets(20, 120, 80, 20));
	        saveButton.setOnAction(event -> {
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath1))) {
	                for (String[] user : userData) {
	                    writer.write(String.join(",", user));
	                    writer.newLine();
	                }
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setHeaderText("Success saved");
	                alert.setContentText("Block account information saved successfully.");
	                alert.showAndWait();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        });
	        
	        VBox mainviewmove = new VBox();
	        ScrollPane scrollPane = new ScrollPane(content);
	        scrollPane.setFitToWidth(true);
	        scrollPane.setPannable(true);
	        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	        VBox.setVgrow(scrollPane, Priority.ALWAYS);
	        mainviewmove.getChildren().addAll(scrollPane, bottom);
	        mainview.getChildren().add(mainviewmove);
	    }
	    
	    private void loadPage4() {
	        VBox content = new VBox(10);
	        VBox changePriceRange = new VBox(5);
	        HBox changePriceMultiplier = new HBox(5);
	        HBox box1 = new HBox(5);
	        HBox box2 = new HBox(5);
	        VBox box3 = new VBox(5);
	        VBox box4 = new VBox(5);
	        VBox box5 = new VBox(5);
	        Label min = new Label("Min:");
	        TextField minField = new TextField();
	        min.setFont(Font.font("", FontWeight.BOLD, 15));
	        min.setPrefWidth(150);
	        min.setPadding(new Insets(0, 0, 0, 20));
	        minField.getStyleClass().add("textfield");
//Avoid Invalid Input
	        minField.textProperty().addListener((observable, oldValue, newValue) -> {
	            if (newValue == null || newValue.isEmpty()) {
	                return;
	            }  
	            String cleanNumber = newValue.replaceAll("[^0-9]", "");          
	            if (!newValue.equals(cleanNumber)) {
	            	minField.setText(oldValue);
	                Alert alert = new Alert(Alert.AlertType.WARNING);
	                alert.setHeaderText("Invalid Input");
	                alert.setContentText("Min price range can only contain numbers.");
	                alert.showAndWait();
	            } else if (cleanNumber.length() > 2) {
	            	minField.setText(oldValue);
	                Alert alert = new Alert(Alert.AlertType.WARNING);
	                alert.setHeaderText("Invalid Input");
	                alert.setContentText("Min price range can only have 1 or 2 bits.");
	                alert.showAndWait();
	            }
	        });
	        box1.getChildren().addAll(min, minField);
	        
	        Label max = new Label("Max:");
	        TextField maxField = new TextField();
	        max.setFont(Font.font("", FontWeight.BOLD, 15));
	        max.setPrefWidth(150);
	        max.setPadding(new Insets(0, 0, 0, 20));
	        maxField.getStyleClass().add("textfield");
//Avoid Invalid Input
	        maxField.textProperty().addListener((observable, oldValue, newValue) -> {
	            if (newValue == null || newValue.isEmpty()) {
	                return;
	            }  
	            String cleanNumber = newValue.replaceAll("[^0-9]", "");          
	            if (!newValue.equals(cleanNumber)) {
	            	maxField.setText(oldValue);
	                Alert alert = new Alert(Alert.AlertType.WARNING);
	                alert.setHeaderText("Invalid Input");
	                alert.setContentText("Max price range can only contain numbers.");
	                alert.showAndWait();
	            } else if (cleanNumber.length() > 3) {
	            	maxField.setText(oldValue);
	                Alert alert = new Alert(Alert.AlertType.WARNING);
	                alert.setHeaderText("Invalid Input");
	                alert.setContentText("Max price range can only have 2 or 3 bits.");
	                alert.showAndWait();
	            }
	        });
	        box2.getChildren().addAll(max, maxField);
	        changePriceRange.getChildren().addAll(box1, box2);
	        
			Label category = new Label("Book Category");
			category.setFont(Font.font("", FontWeight.BOLD, 15));
			ToggleGroup bookGroup = new ToggleGroup();
	        RadioButton box6 = new RadioButton("Math");
	        RadioButton box7 = new RadioButton("Natural Science");
	        RadioButton box8 = new RadioButton("English");
	        RadioButton box9 = new RadioButton("Computer");
	        RadioButton box10 = new RadioButton("Other");
	        box6.setToggleGroup(bookGroup);
	        box7.setToggleGroup(bookGroup);
	        box8.setToggleGroup(bookGroup);
	        box9.setToggleGroup(bookGroup);
	        box10.setToggleGroup(bookGroup);
	        box3.getChildren().addAll(category,box6,box7,box8,box9,box10);
	        box3.setPadding(new Insets(50,25,50,50));
	        
			Label condition = new Label("Book Condition");
			Label box11 = new Label("Used Like New");
	        box11.setFont(Font.font("", FontWeight.BOLD, 15));
	        box11.setPrefWidth(150);
	        box11.setPrefHeight(35);
	        Label box12 = new Label("Moderately Used");
	        box12.setFont(Font.font("", FontWeight.BOLD, 15));
	        box12.setPrefWidth(150);
	        box12.setPrefHeight(35);
	        Label box13 = new Label("Heavily Used");
	        box13.setFont(Font.font("", FontWeight.BOLD, 15));
	        box13.setPrefWidth(150);
	        box13.setPrefHeight(35);
	        box4.getChildren().addAll(condition,box11,box12,box13);
	        box4.setPadding(new Insets(50,25,50,25));
	        
	        Label multiplier = new Label("Price multiplier");
	        multiplier.setFont(Font.font("", FontWeight.BOLD, 15));
	        TextField field1 = new TextField();
	        field1.getStyleClass().add("textfield1");
//Avoid Invalid Input
	        field1.textProperty().addListener((observable, oldValue, newValue) -> {
	            if (newValue == null || newValue.isEmpty()) {
	                return;
	            }  
	            String cleanNumber = newValue.replaceAll("[^0-9.]", "");          
	            if (!newValue.equals(cleanNumber)) {
	            	field1.setText(oldValue);
	                Alert alert = new Alert(Alert.AlertType.WARNING);
	                alert.setHeaderText("Invalid Input");
	                alert.setContentText("Price Multiplier can only contain numbers that smaller than 1.");
	                alert.showAndWait();
	            } 
	        });
	        TextField field2 = new TextField();
	        field2.getStyleClass().add("textfield1");
//Avoid Invalid Input
	        field2.textProperty().addListener((observable, oldValue, newValue) -> {
	            if (newValue == null || newValue.isEmpty()) {
	                return;
	            }  
	            String cleanNumber = newValue.replaceAll("[^0-9.]", "");          
	            if (!newValue.equals(cleanNumber)) {
	            	field2.setText(oldValue);
	                Alert alert = new Alert(Alert.AlertType.WARNING);
	                alert.setHeaderText("Invalid Input");
	                alert.setContentText("Price Multiplier can only contain numbers that smaller than 1.");
	                alert.showAndWait();
	            } 
	        });
	        TextField field3 = new TextField();
	        field3.getStyleClass().add("textfield1");
//Avoid Invalid Input
	        field3.textProperty().addListener((observable, oldValue, newValue) -> {
	            if (newValue == null || newValue.isEmpty()) {
	                return;
	            }  
	            String cleanNumber = newValue.replaceAll("[^0-9.]", "");          
	            if (!newValue.equals(cleanNumber)) {
	            	field3.setText(oldValue);
	                Alert alert = new Alert(Alert.AlertType.WARNING);
	                alert.setHeaderText("Invalid Input");
	                alert.setContentText("Price Multiplier can only contain numbers that smaller than 1.");
	                alert.showAndWait();
	            } 
	        });
	        box5.getChildren().addAll(multiplier,field1,field2,field3);
	        box5.setPadding(new Insets(50, 25, 50, 25)); 
	        
	        
	        changePriceMultiplier.getChildren().addAll(box3, box4,box5);
	        content.getChildren().addAll(changePriceRange, changePriceMultiplier);
	        
//Bottom PART
	        Button box14 = new Button("Save");
	        box14.setPrefWidth(100);
	        box14.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-weight: bold;"
	        		+ "-fx-pref-width: 100px;"
	        		+ "-fx-pref-height: 50px;");
	        HBox bottom = new HBox(box14);
	        bottom.setAlignment(Pos.BOTTOM_RIGHT);
	        bottom.setPadding(new Insets(20, 120, 80, 20));
	        box14.setOnAction(event -> {
	            if (bookGroup.getSelectedToggle() == null) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
	                alert.setHeaderText("Category missing");
	                alert.setContentText("Need to choose a Category before saving.");
	                alert.showAndWait();
	                return;
	            }
	            
	            String selectedCategory = "";
	            if (box6.isSelected()) selectedCategory = "Math";
	            else if (box7.isSelected()) selectedCategory = "Natural Science";
	            else if (box8.isSelected()) selectedCategory = "English";
	            else if (box9.isSelected()) selectedCategory = "Computer";
	            else if (box10.isSelected()) selectedCategory = "Other";
	            
	            
	            String minField1 = minField.getText().trim();
	            String maxField1 = maxField.getText().trim();
	            String field11 = field1.getText().trim();
	            String field21 = field2.getText().trim();
	            String field31 = field3.getText().trim();
	            
				if (field1.getText()== null || field1.getText().trim().isEmpty()||
						field2.getText()== null || field2.getText().trim().isEmpty()||
						minField.getText()== null || minField.getText().trim().isEmpty()||
						maxField.getText()== null || maxField.getText().trim().isEmpty()||
						field3.getText()== null || field3.getText().trim().isEmpty()) {
						
						Alert alert = new Alert(Alert.AlertType.WARNING);
		                alert.setHeaderText("Incomplete Data");
		                alert.setContentText("All fields must be filled before saving.");
		                alert.showAndWait();
		                return;
		            }
				
	            double value1 = Double.valueOf(field11.toString());
	            double value2 = Double.valueOf(field21.toString());
	            double value3 = Double.valueOf(field31.toString());
	            double value4 = Double.valueOf(minField1.toString());
	            double value5 = Double.valueOf(maxField1.toString());
	            
	            if(value5 < value4) {
	            	Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setHeaderText("Error Data");
	                alert.setContentText("Price range is not correct." );
	                alert.showAndWait();
	                return;
	            }
	            
	            if(value1 < value2 || value2 < value3 || value1 < value3 || value1 > 1 ||
	            		value2 > 1 || value3 > 1) {
	            	Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setHeaderText("Error Data");
	                alert.setContentText("Price Multiplier Can't be bigger than one. And used liked new >= moderately used >= heavlily used." );
	                alert.showAndWait();
	                return;
	            }
	            
	            try {
	                List<String> lines = new ArrayList<>();
	                Path filePath = Paths.get("pricemultiplier.txt");
	                boolean categoryExists = false;
	                
	                if (Files.exists(filePath)) {
	                    lines = Files.readAllLines(filePath);
	                    
	                    for (int i = 0; i < lines.size(); i++) {
	                        String in = lines.get(i);
	                        if (in.startsWith(selectedCategory + ",")) {
	                            lines.set(i, String.format("%s,%.2f,%.2f,%.2f", 
	                            selectedCategory,value1,value2,value3));
	                            categoryExists = true;
	                            break;
	                        }
	                    }
	                }
	                
	                if (!categoryExists) {
	                    lines.add(String.format("%s,%.2f,%.2f,%.2f", selectedCategory,value1,value2,value3));
	                }
	                Files.write(filePath, lines);
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setHeaderText("Success saved");
	                alert.setContentText("Price multipliers saved successfully for " + selectedCategory);
	                alert.showAndWait();
	                field1.clear();
	                field2.clear();
	                field3.clear();
	                bookGroup.selectToggle(null);	                
	            } catch (IOException e) {
	            }
	            
	            try {
	                String line = new String();
	                Path filePath = Paths.get("pricerange.txt");
	                
	                if (Files.exists(filePath)) {
	                	line = Files.readAllLines(filePath).get(0);
	                }
	                else {
	                	line = "";
	                }
	                
	                line = String.format("%.2f,%.2f", value4, value5);
	                Files.write(filePath, line.getBytes());
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setHeaderText("Success saved");
	                alert.setContentText("Price range saved successfully.");
	                alert.showAndWait();
	                minField.clear();
	                maxField.clear();                
	            } catch (IOException e) {
	            }
	            
	        });
	        mainview.getChildren().addAll(content,bottom);
	       
	        
	    }
	    
	    private void loadPage5() {
	        VBox content = new VBox(10);
	        HBox box1 = new HBox(5);
	        VBox box2 = new VBox(5);
	        userData1.clear();
	        content.getChildren().clear();
	        mainview.getChildren().clear();
	        box1.getChildren().clear();
	        box2.getChildren().clear();
	        int a = 0;
	        String buyerId = String.valueOf(a);
	        String transactionId = null;
	        String sellerId = null;
            String bookTitle = null;
            String author = null;
            String year = null;
            String category = null;
            String condition = null;
            
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath2))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	            	if (line.trim().isEmpty()) continue;
	                userData1.add(line.split(","));
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	          
	        content.setPadding(new Insets(20));
	        Label title = new Label("Total number of book transactions");
	        TextField totalav = new TextField();
	        title.setFont(Font.font("", FontWeight.BOLD, 15));
	        title.setPrefWidth(400);
	        title.setPadding(new Insets(0, 0, 0, 20));
	        totalav.getStyleClass().add("textfield");
            title.setAlignment(Pos.CENTER_LEFT);
            int count = 0;
            for (String[] userinfomation : userData1) {
                if (userinfomation[2].equals("0")) {
                    count++;
                }
            }
            String k = String.valueOf(count);
            totalav.setText(k);
            totalav.setEditable(false);
            box1.getChildren().addAll(title,totalav);
	        
            HBox header = new HBox(10);
	    	header.setPadding(new Insets(0,0,0,40));
	    	Label infoHead = new Label("Book Title");
	    	infoHead.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead.setPrefWidth(100);
            infoHead.setAlignment(Pos.CENTER);
            
            Label infoHead1 = new Label("Author");
            infoHead1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead1.setAlignment(Pos.CENTER);
            infoHead1.setPrefWidth(100);
            
            Label infoHead2 = new Label("Book Category");
            infoHead2.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead2.setAlignment(Pos.CENTER);
            infoHead2.setPrefWidth(100);
            
            Label infoHead3 = new Label("Published Year");
            infoHead3.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead3.setAlignment(Pos.CENTER);
            infoHead3.setPrefWidth(100);
            
            Label infoHead4 = new Label("Book Condition");
            infoHead4.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead4.setAlignment(Pos.CENTER);
            infoHead4.setPrefWidth(100);
            
            Label infoHead5 = new Label("");
            infoHead5.setAlignment(Pos.CENTER);
            infoHead5.setPrefWidth(100);
            
            Label infoHead6 = new Label("Transaction ID");
            infoHead6.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            infoHead6.setAlignment(Pos.CENTER);
            infoHead6.setPrefWidth(100);
            
            header.getChildren().addAll(infoHead6,infoHead,infoHead1,infoHead2,infoHead3,infoHead4,infoHead5);
            header.getStyleClass().add("textfield2");
            box2.getChildren().add(header);
            
            for(int i = 0; i < userData1.size();i++) {
	        	String[] userinformation = userData1.get(i);
	        	transactionId = userinformation[0];
	            sellerId = userinformation[1];
	            buyerId = userinformation[2];
	            bookTitle = userinformation[3];
	            author = userinformation[4];
	            year = userinformation[5];
	            category = userinformation[6];
	            condition = userinformation[7];
	            
            if(buyerId.equals(String.valueOf(a))) {	        	
	        	Label infoHead7 = new Label(bookTitle);
		    	infoHead7.setStyle("-fx-font-size: 12px;");
	            infoHead7.setPrefWidth(100);
	            infoHead7.setAlignment(Pos.CENTER);
	            
	            Label infoHead8 = new Label(author);
	            infoHead8.setStyle("-fx-font-size: 12px;");
	            infoHead8.setAlignment(Pos.CENTER);
	            infoHead8.setPrefWidth(100);
	            
	            Label infoHead9 = new Label(category);
	            infoHead9.setStyle("-fx-font-size: 12px;");
	            infoHead9.setAlignment(Pos.CENTER);
	            infoHead9.setPrefWidth(100);
	            
	            Label infoHead10 = new Label(year);
	            infoHead10.setStyle("-fx-font-size: 12px;");
	            infoHead10.setAlignment(Pos.CENTER);
	            infoHead10.setPrefWidth(100);
	            
	            Label infoHead11 = new Label(condition);
	            infoHead11.setStyle("-fx-font-size: 12px;");
	            infoHead11.setAlignment(Pos.CENTER);
	            infoHead11.setPrefWidth(100);
	            
	            Label infoHead13 = new Label(transactionId);
	            infoHead13.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
	            infoHead13.setAlignment(Pos.CENTER);
	            infoHead13.setPrefWidth(100);
	            
	            Button delete = new Button("Delete");
		        delete.setStyle("-fx-background-color: #ffffff; -fx-text-fill: red;  -fx-border-color: blue;"
		        		+ "    -fx-border-width: 1px;	"
		        		+ "-fx-pref-width: 100px;"
		        		+ "-fx-pref-height: 35px;");
		        
		        delete.setOnAction(event -> {
		        	int b = 1;
		        	userinformation[2] = String.valueOf(b);
		            
		            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath2))) {
		                for (String[] data : userData1) {
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
		            userData1.clear();
		            loadPage5();
		        });
		        
	            HBox row = new HBox(10);
	            row.getStyleClass().add("textfield2");
	            row.setPadding(new Insets(0,0,0,40));
	            row.getChildren().addAll(infoHead13,infoHead7,infoHead8,infoHead9,infoHead10,infoHead11,delete);
	            box2.getChildren().add(row);
	    }
            }
            
            ScrollPane scrollPane = new ScrollPane(box2);
	        scrollPane.setFitToWidth(true);
	        scrollPane.setPannable(true);
	        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
	        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	        VBox.setVgrow(scrollPane, Priority.ALWAYS);
	        content.getChildren().addAll(box1,scrollPane);
	        mainview.getChildren().add(content);
	    }
	    
}




