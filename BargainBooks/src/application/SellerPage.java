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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;
import javafx.scene.SnapshotParameters;
import java.io.InputStream;

public class SellerPage {
    private static List<BookListing> currentListings = new ArrayList<>();
    private static final double LIKE_NEW_MULTIPLIER = 0.90;
    private static final double MODERATELY_USED_MULTIPLIER = 0.75;
    private static final double HEAVILY_USED_MULTIPLIER = 0.50;
    private static ImageView bookImageView;
    private static File selectedImageFile;
    
    
    
    
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

    private static class BookListing {
        String title;
        double originalPrice;
        double adjustedPrice;
        String condition;
        File imageFile;  // New field

        BookListing(String title, double originalPrice, String condition, File imageFile) {
            this.title = title;
            this.originalPrice = originalPrice;
            this.condition = condition;
            this.adjustedPrice = calculateAdjustedPrice(originalPrice, condition);
            this.imageFile = imageFile; 
        }
    }

    private static double calculateAdjustedPrice(double originalPrice, String condition) {
        double multiplier;
        switch (condition) {
            case "Like New":
                multiplier = LIKE_NEW_MULTIPLIER;
                break;
            case "Moderately Used":
                multiplier = MODERATELY_USED_MULTIPLIER;
                break;
            case "Heavily Used":
                multiplier = HEAVILY_USED_MULTIPLIER;
                break;
            default:
                multiplier = 1.0;
        }
        return Math.round(originalPrice * multiplier * 100.0) / 100.0;
    }

    public static void display(Stage primaryStage, String asuId) {
        primaryStage.setTitle("SunDevil Secondhand Book Exchange");

     // Create the root layout
        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: white;");

        // Header with logo and welcome/logout
        HBox header = new HBox(10);
        header.setPadding(new Insets(10, 20, 10, 20));
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: white; -fx-border-color: transparent;");

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

     // Create auth container for welcome and logout (right-aligned)
        VBox authContainer = new VBox(0.05); 
        authContainer.setAlignment(Pos.TOP_RIGHT);
        HBox.setHgrow(authContainer, Priority.ALWAYS);

        Label welcomeLabel = new Label("Welcome " + asuId);
        welcomeLabel.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-text-fill: #666666; " +
            "-fx-border-color: black; " +    // Add black border
            "-fx-border-radius: 3px; " +     // Add border radius
            "-fx-padding: 5 10; " +          // Add some padding inside the border
            "-fx-background-radius: 3px;"     // background radius with border
        );

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle(
            "-fx-background-color: #8B0000;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 5 15;" +
            "-fx-border-color: black; " +    // Add black border
            "-fx-border-radius: 3px; " +     // Add border radius
            "-fx-background-radius: 3px; " + // background radius with border
            "-fx-cursor: hand;" +
            "-fx-font-size: 14px"
        );

        authContainer.getChildren().addAll(welcomeLabel, logoutBtn);

        // Add logo and auth container to header
        if (logo != null) {
            header.getChildren().addAll(logo, authContainer);
        } else {
            header.getChildren().add(authContainer);
        }

        // Main content layout
        HBox mainLayout = new HBox(20);
        mainLayout.setPadding(new Insets(0, 20, 20, 20));

     // Left Navigation Panel
        VBox navPanel = new VBox(0.05); // Added small spacing between buttons
        Button sellingBtn = createNavButton("Selling", true);
        Button buyingBtn = createNavButton("Buying", false);

        // Add padding around the buttons
        navPanel.setPadding(new Insets(10, 0, 0, 0));  // Top padding of 10
        navPanel.getChildren().addAll(sellingBtn, buyingBtn);
        navPanel.setMaxWidth(150);  // button width
        navPanel.setAlignment(Pos.TOP_LEFT);
        
        // Center Form Panel
        VBox formPanel = new VBox(15);
        formPanel.setPadding(new Insets(20));
        formPanel.setPrefWidth(600);
        
     // Create and style the content container with the grey background
        VBox greyContainer = new VBox(15);
        greyContainer.setStyle(
            "-fx-background-color: #f5f5f5;" +
            "-fx-background-radius: 0;"
        );
        greyContainer.setPadding(new Insets(20));

     // Create left side container for image placeholder
        VBox imageContainer = new VBox();
        imageContainer.setPrefWidth(250);
        imageContainer.setPrefHeight(250);
        imageContainer.setStyle(
            "-fx-background-color: #D3D3D3;" +
            "-fx-background-radius: 0;"
        );
        imageContainer.setAlignment(Pos.CENTER);

        // Create ImageView for book photo
        bookImageView = new ImageView();
        bookImageView.setFitWidth(200);
        bookImageView.setFitHeight(200);
        bookImageView.setPreserveRatio(true);

        // Create upload button
        Button uploadButton = new Button("Upload Photo");
        uploadButton.setStyle(
            "-fx-background-color: #8B0000;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 8 15;" +
            "-fx-border-color: black;" +      // Add black border
            "-fx-border-radius: 3px;" +       // Add border radius
            "-fx-background-radius: 3px;" +    // background radius
            "-fx-cursor: hand;" +
            "-fx-font-size: 12px"
        );

        // Set default placeholder
        setDefaultPlaceholder();

        // Upload button action
        uploadButton.setOnAction(e -> handleImageUpload(primaryStage));

        // Create photo container
        VBox photoBox = new VBox(10);
        photoBox.setAlignment(Pos.CENTER);
        photoBox.getChildren().addAll(bookImageView, uploadButton);
        imageContainer.getChildren().add(photoBox);
        
        
        

        // Create right side container for form fields
        VBox fieldsContainer = new VBox(15);
        fieldsContainer.setPadding(new Insets(0, 0, 0, 20));

        // Form fields
        TextField titleField = new TextField();
        titleField.setPromptText("Input Book Title Here");
        styleTextField(titleField);

        TextField authorField = new TextField();
        authorField.setPromptText("Input Author Name");
        styleTextField(authorField);

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Input Published Date");
        styleTextField(datePicker);

        ComboBox<String> conditionBox = new ComboBox<>();
        conditionBox.setPromptText("Select Condition");
        conditionBox.getItems().addAll("Like New", "Moderately Used", "Heavily Used");
        styleComboBox(conditionBox);

        TextField priceField = new TextField();
        priceField.setPromptText("Input Original Price");
        styleTextField(priceField);

        // List My Book button and generated price
        HBox listingRow = new HBox(10);
        Button listButton = new Button("List My Book");  
        listButton.setPrefWidth(200);  
        listButton.setStyle(
            "-fx-background-color: #8B0000;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 10 5;" +
            "-fx-border-color: black;" +      // Add black border
            "-fx-border-radius: 3px;" +       // Add border radius
            "-fx-background-radius: 3px;" +    // background radius
            "-fx-cursor: hand;" +
            "-fx-font-size: 14px"
        );

        TextField generatedPriceField = new TextField();
        generatedPriceField.setPromptText("Generated Price");
        generatedPriceField.setEditable(false);
        generatedPriceField.setPrefWidth(230);
        styleTextField(generatedPriceField);

        listingRow.getChildren().addAll(listButton, generatedPriceField);

        // Add fields to container
        fieldsContainer.getChildren().addAll(
            titleField,
            authorField,
            datePicker,
            conditionBox,
            priceField
//            listingRow
        );

        // Create horizontal container for image and fields
        HBox contentContainer = new HBox(0); // Remove spacing
        contentContainer.getChildren().addAll(imageContainer, fieldsContainer);
        formPanel.getChildren().add(contentContainer);
        
        
        
        listingRow.setPadding(new Insets(20, 0, 0, 0));  // Add top padding to create space
        formPanel.getChildren().add(listingRow);  // Add listingRow directly to formPanel
    
     // Right Listings Panel
        VBox listingsPanel = new VBox(10);
        listingsPanel.setPadding(new Insets(15));
        listingsPanel.setPrefWidth(300);

        // Title container with orange border
        HBox titleContainer = new HBox();
        titleContainer.setStyle(
            "-fx-border-color: #D94F00;" +  // Orange border
            "-fx-border-width: 1;" +        // Thicker border
            "-fx-border-radius: 5;" +
            "-fx-padding: 10;"
        );

        Label listingsTitle = new Label("My Current Listings");
        listingsTitle.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;"
        );
        titleContainer.getChildren().add(listingsTitle);

        // Create a container for listings and total price with black border
        VBox listingsContainer = new VBox(10);
        listingsContainer.setStyle(
            "-fx-border-color: black;" +     // Changed to black border
            "-fx-border-width: 1;" +
            "-fx-border-radius: 5;" +
            "-fx-padding: 10;" +
            "-fx-margin-top: 10;"  // Space between the two bordered containers
        );

        // VBox for the actual listings
        VBox listings = new VBox(10);
        listings.setMinHeight(200);

        // Total price label
        Label totalPrice = new Label("Total Price: $0.00");
        totalPrice.setStyle(
            "-fx-font-weight: bold;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10 0 0 0;" +
            "-fx-border-color: #E8E8E8;" +
            "-fx-border-width: 1 0 0 0;"
        );

        // Add components to their containers
        listingsContainer.getChildren().addAll(listings, totalPrice);
        listingsPanel.getChildren().addAll(titleContainer, listingsContainer);

        // Add panels to main layout
        mainLayout.getChildren().addAll(navPanel, formPanel, listingsPanel);

        // Add everything to root
        root.getChildren().addAll(header, mainLayout);

        // Create scene
        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Button actions
        listButton.setOnAction(e -> {
            if (validateInputs(titleField, authorField, priceField, conditionBox)) {
                try {
                    double originalPrice = Double.parseDouble(priceField.getText());
                    String condition = conditionBox.getValue();
                    double adjustedPrice = calculateAdjustedPrice(originalPrice, condition);
                    
                    generatedPriceField.setText(String.format("$%.2f", adjustedPrice));
                    
                    BookListing newListing = new BookListing(
                        titleField.getText(),
                        originalPrice,
                        condition,
                        selectedImageFile
                    );
                    
                    currentListings.add(newListing);
                    listings.getChildren().add(createListingEntry(newListing, listings));
                    updateTotalPrice(totalPrice);
                    clearInputs(titleField, authorField, priceField, conditionBox, datePicker);
                    generatedPriceField.clear();
                } catch (NumberFormatException ex) {
                    showAlert("Invalid Price", "Please enter a valid number for the price");
                }
            }
        });

        logoutBtn.setOnAction(e -> {
            currentListings.clear();
            new LoginPage().start(primaryStage);
        });
    }

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
        button.setPrefWidth(130);  // Reduced width
        button.setPrefHeight(15);  // Reduced height
        
        
        String style;
        if (isActive) {
            style = "-fx-background-color: white;" +
                    "-fx-text-fill: black;" +
                    "-fx-border-color: black;" +  // Black border
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 3;" +     // Kept border radius
                    "-fx-background-radius: 3;";   // Kept background radius
        } else {
            style = "-fx-background-color: #8B0000;" +  // Dark red background
                    "-fx-text-fill: white;" +
                    "-fx-border-color: black;" +   // Black border
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 3;" +      // Kept border radius
                    "-fx-background-radius: 3;";    // Kept background radius
        }
        
        button.setStyle(style + "-fx-padding: 5 15; -fx-font-size: 12px;");
        return button;
    }

    private static VBox createListingEntry(BookListing listing, VBox listings) {
        VBox entry = new VBox(5);
        entry.setAlignment(Pos.CENTER_LEFT);
        entry.setPadding(new Insets(5, 0, 5, 0));
        entry.setStyle("-fx-border-color: #E8E8E8; -fx-border-width: 0 0 1 0;"); // Bottom border only
        
        // Title
        Label titleLabel = new Label(listing.title);
        titleLabel.setStyle("-fx-font-size: 14px;");
        
        // Container for price and delist button
        HBox bottomRow = new HBox(10);
        bottomRow.setAlignment(Pos.CENTER_LEFT);
        
        Label priceLabel = new Label(String.format("$%.2f", listing.adjustedPrice));
        priceLabel.setStyle("-fx-font-size: 14px;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button delistBtn = new Button("Delist");
        delistBtn.setPrefWidth(80);
        delistBtn.setStyle(
            "-fx-background-color: #8B0000;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 5 15;" +
            "-fx-border-color: black;" +
            "-fx-border-radius: 3;" +
            "-fx-background-radius: 3;" +
            "-fx-cursor: hand;" +
            "-fx-font-size: 12px"
        );
        
        delistBtn.setOnAction(e -> {
            currentListings.remove(listing);
            listings.getChildren().remove(entry);
            
            // Find and update total price
            VBox listingsContainer = (VBox) listings.getParent();
            for (javafx.scene.Node node : listingsContainer.getChildren()) {
                if (node instanceof Label) {
                    Label label = (Label) node;
                    if (label.getText().startsWith("Total Price")) {
                        updateTotalPrice(label);
                        break;
                    }
                }
            }
        });
        
        bottomRow.getChildren().addAll(priceLabel, spacer, delistBtn);
        entry.getChildren().addAll(titleLabel, bottomRow);
        
        return entry;
    }

    private static ImageView createLogo() {
        try {
            Image image = new Image(SellerPage.class.getResourceAsStream("/resource/images/logo.png"));
            return new ImageView(image);
        } catch (Exception e) {
            System.out.println("Error loading logo: " + e.getMessage());
            return new ImageView();
        }
    }

    private static boolean validateInputs(TextField title, TextField author, TextField price, ComboBox<String> condition) {
        if (title.getText().isEmpty()) {
            showAlert("Missing Title", "Please enter a book title");
            return false;
        }
        
        if (author.getText().isEmpty()) {
            showAlert("Missing Author", "Please enter an author name");
            return false;
        }
        
        if (condition.getValue() == null) {
            showAlert("Missing Condition", "Please select a book condition");
            return false;
        }
        
        try {
            double priceValue = Double.parseDouble(price.getText());
            if (priceValue <= 0) {
                showAlert("Invalid Price", "Price must be greater than 0");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Price", "Please enter a valid number for the price");
            return false;
        }
        
        return true;
    }

    private static void updateTotalPrice(Label totalPriceLabel) {
        if (totalPriceLabel != null) {
            double total = 0.0;
            for (BookListing listing : currentListings) {
                total += listing.adjustedPrice;
            }
            totalPriceLabel.setText(String.format("Total Price: $%.2f", total));
        }
    }

    private static void clearInputs(TextField title, TextField author, TextField price, ComboBox<String> condition, DatePicker date) {
        title.clear();
        author.clear();
        price.clear();
        condition.setValue(null);
        date.setValue(null);
        setDefaultPlaceholder();
        selectedImageFile = null;
    }
    private static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
