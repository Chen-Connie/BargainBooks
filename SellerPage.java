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

public class SellerPage {
    private static List<BookListing> currentListings = new ArrayList<>();

    public static void display(Stage primaryStage, String asuId) {
        primaryStage.setTitle("Bargain Books - Seller Page");

        // Create the root layout
        VBox root = new VBox(10);
        root.setStyle("-fx-background-color: white;");

        // Header with welcome and logout
        HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.setAlignment(Pos.TOP_RIGHT);
        header.setStyle("-fx-background-color: #f8f9fa;");

        Label welcomeLabel = new Label("Welcome " + asuId);
        welcomeLabel.setStyle("-fx-font-size: 14px;");
        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle(
            "-fx-background-color: #8B0000;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 5 15;" +
            "-fx-cursor: hand;"
        );
        header.getChildren().addAll(welcomeLabel, logoutBtn);
        HBox.setMargin(logoutBtn, new Insets(0, 0, 0, 20));

        // Main content layout
        HBox mainLayout = new HBox(20);
        mainLayout.setPadding(new Insets(20));

        // Left Navigation Panel
        VBox navPanel = new VBox(10);
        ImageView logo = createLogo();
        logo.setFitWidth(200);
        logo.setFitHeight(100);

        Button sellingBtn = createNavButton("Selling", true);
        Button buyingBtn = createNavButton("Buying", false);

        navPanel.getChildren().addAll(logo, sellingBtn, buyingBtn);
        navPanel.setAlignment(Pos.TOP_LEFT);

        // Center Form Panel
        VBox formPanel = new VBox(15);
        formPanel.setPadding(new Insets(20));
        formPanel.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5;");
        formPanel.setPrefWidth(500);

        // Image placeholder
        Rectangle imagePlaceholder = new Rectangle(200, 150);
        imagePlaceholder.setFill(Color.LIGHTGRAY);
        imagePlaceholder.setArcWidth(10);
        imagePlaceholder.setArcHeight(10);

        // Form fields
        TextField titleField = createStyledTextField("Input Book Title Here");
        TextField authorField = createStyledTextField("Input Author Name");
        
        // Date picker
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Input Published Date");
        styleTextField(datePicker);

        // Condition dropdown
        ComboBox<String> conditionBox = new ComboBox<>();
        conditionBox.setPromptText("Select Condition");
        conditionBox.getItems().addAll("Like New", "Moderately Used", "Heavily Used");
        styleComboBox(conditionBox);

        TextField priceField = createStyledTextField("Input Original Price");
        TextField generatedPriceField = createStyledTextField("Placeholder Generated Price");
        generatedPriceField.setEditable(false);

        // List My Book button and generated price in same row
        HBox listingRow = new HBox(10);
        Button listButton = new Button("List My Book");
        listButton.setStyle(
            "-fx-background-color: #8B0000;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 10 20;" +
            "-fx-cursor: hand;"
        );
        listingRow.getChildren().addAll(listButton, generatedPriceField);

        formPanel.getChildren().addAll(
            imagePlaceholder,
            titleField,
            authorField,
            datePicker,
            conditionBox,
            priceField,
            listingRow
        );

        // Right Listings Panel
        VBox listingsPanel = new VBox(10);
        listingsPanel.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-radius: 5;" +
            "-fx-padding: 15;"
        );
        listingsPanel.setPrefWidth(300);

        Label listingsTitle = new Label("My Current Listings");
        listingsTitle.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;"
        );

        VBox listings = new VBox(10);
        if (currentListings.isEmpty()) {
            // Show nothing if no listings
            listings.setMinHeight(200); // Maintain some height even when empty
        } else {
            // Add existing listings
            for (BookListing listing : currentListings) {
                listings.getChildren().add(createListingEntry(listing, listings));
            }
        }

        Label totalPrice = new Label(currentListings.isEmpty() ? "Total Price: $0" : "Total Price: $" + calculateTotal());
        totalPrice.setStyle("-fx-font-weight: bold;");

        listingsPanel.getChildren().addAll(listingsTitle, listings, totalPrice);

        // Add all panels to main layout
        mainLayout.getChildren().addAll(navPanel, formPanel, listingsPanel);

        // Add header and main layout to root
        root.getChildren().addAll(header, mainLayout);

        // Create scene and show
        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add button actions
        listButton.setOnAction(e -> {
            if (validateInputs(titleField, authorField, priceField, conditionBox)) {
                BookListing newListing = new BookListing(
                    titleField.getText(),
                    Double.parseDouble(priceField.getText())
                );
                currentListings.add(newListing);
                listings.getChildren().add(createListingEntry(newListing, listings));
                totalPrice.setText("Total Price: $" + calculateTotal());
                clearInputs(titleField, authorField, priceField, conditionBox, datePicker);
            }
        });

        logoutBtn.setOnAction(e -> {
            currentListings.clear(); // Clear listings on logout
            new LoginPage().start(primaryStage);
        });
    }

    private static class BookListing {
        String title;
        double price;

        BookListing(String title, double price) {
            this.title = title;
            this.price = price;
        }
    }

    private static TextField createStyledTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        styleTextField(field);
        return field;
    }

    private static void styleTextField(Control field) {
        field.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 8;" +
            "-fx-pref-width: 400px;"
        );
    }

    private static void styleComboBox(ComboBox<?> box) {
        box.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-radius: 3;" +
            "-fx-padding: 4;" +
            "-fx-pref-width: 400px;"
        );
    }

    private static Button createNavButton(String text, boolean isActive) {
        Button button = new Button(text);
        button.setMaxWidth(200);
        String style = isActive ? 
            "-fx-background-color: #8B0000; -fx-text-fill: white;" :
            "-fx-background-color: white; -fx-text-fill: black; -fx-border-color: #cccccc;";
        button.setStyle(style + "-fx-padding: 10 20; -fx-font-size: 14px; -fx-cursor: hand;");
        return button;
    }

    private static HBox createListingEntry(BookListing listing, VBox listings) {
        HBox entry = new HBox(10);
        entry.setAlignment(Pos.CENTER_LEFT);
        
        Label titleLabel = new Label(listing.title);
        Label priceLabel = new Label(String.format("$%.2f", listing.price));
        Button delistBtn = new Button("Delist");
        delistBtn.setStyle(
            "-fx-background-color: #8B0000;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 5 15;" +
            "-fx-cursor: hand;"
        );
        
        delistBtn.setOnAction(e -> {
            currentListings.remove(listing);
            listings.getChildren().remove(entry);
        });
        
        entry.getChildren().addAll(titleLabel, priceLabel, delistBtn);
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
        return !title.getText().isEmpty() && 
               !author.getText().isEmpty() && 
               !price.getText().isEmpty() && 
               condition.getValue() != null;
    }

    private static void clearInputs(TextField title, TextField author, TextField price, ComboBox<String> condition, DatePicker date) {
        title.clear();
        author.clear();
        price.clear();
        condition.setValue(null);
        date.setValue(null);
    }

    private static double calculateTotal() {
        return currentListings.stream()
            .mapToDouble(listing -> listing.price)
            .sum();
    }
}