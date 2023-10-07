import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class FirstPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a SplitPane
        SplitPane splitPane = new SplitPane();

        // Create the left pane with content
        StackPane leftPane = createLeftPane();

        // Create the right pane with content
        StackPane rightPane = createRightPane(primaryStage);

        // Set the initial position of the divider (line)
        splitPane.setDividerPosition(0, 0.33);

        // Add the panes to the SplitPane
        splitPane.getItems().addAll(leftPane, rightPane);

        // Create the scene and add the SplitPane
        Scene scene = new Scene(splitPane, 1800, 950);

        // Set the stage title
        primaryStage.setTitle("Group Project Demo");
        primaryStage.setResizable(false);

        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private StackPane createLeftPane() {
        // Create the left pane with content
        StackPane leftPane = new StackPane();


        Image img = new Image("file:///D:\\JavaFX\\VersionVerse\\logoC2W.png");
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);
        imageView.setOnMouseClicked(event->{
            try{
                URI obj = new URI("https://www.core2web.in");

                Desktop desk = Desktop.getDesktop();
                desk.browse(obj);
            }catch(URISyntaxException e) {
                System.out.println("URI syntax not found");
            }catch(IOException ie) {
                System.out.println("IOException");
            }
        });

        Image vImage = new Image("file:///D:\\JavaFX\\VersionVerse\\versionverse.png");
        ImageView vImageView = new ImageView(vImage);
        vImageView.setFitHeight(150);
        vImageView.setPreserveRatio(true);

        Region gap = new Region();
        gap.setPrefHeight(50);

        HBox hbox = new HBox(50, imageView, vImageView); // Adjust spacing as needed
        hbox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(gap, hbox);
        vbox.setAlignment(Pos.TOP_CENTER);

        
        // String currentDirectory = System.getProperty("user.dir");
        //System.out.println(System.getProperty("user.dir"));
        String filePath = "D:\\JavaFX\\VersionVerse\\CompanyInfo.txt";

        //System.out.println(filePath);

        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            StringBuilder data = new StringBuilder();
            while((line=reader.readLine())!= null){
                data.append(line).append("\n");
            }
            reader.close();

            Text text = new Text(data.toString());
            vbox.getChildren().add(text);

        }catch(IOException ie){
            ie.printStackTrace();
        }

        leftPane.getChildren().add(vbox);

        return leftPane;
    }

    private StackPane createRightPane(Stage primaryStage) {

        //creating the object of 2nd class 

        JDBC_VersionVerse jdbc = new JDBC_VersionVerse("jdbc:mysql://localhost:3306/versionverse","root","Smart@123");
        
        // Create the right pane with content
        StackPane rightPane = new StackPane();
        
        BackgroundImage backgroundImage = new BackgroundImage(
            new Image("file:///D:\\JavaFX\\VersionVerse\\langWall.jpg"), 
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );

        Background background = new Background(backgroundImage);
        
        
        // Create a transparent overlay with opacity
        Region backgroundOverlay = new Region();
        backgroundOverlay.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
        
        Label name = new Label("Name");
        GridPane.setConstraints(name,0,0);
        name.setStyle("-fx-fonnt-weight: bold; -fx-font-size : 18px;-fx-font-family: \"Times New Roman\";");

        TextField nameField = new TextField("");
        nameField.setPromptText("Enter Your name");
        GridPane.setConstraints(nameField, 1, 0);
        nameField.setStyle("-fx-fonnt-weight: bold; -fx-font-size : 18px;-fx-font-family: \"Times New Roman\";");

        Button save = new Button("Save");
        GridPane.setConstraints(save, 2, 0);
        save.setStyle("-fx-fonnt-weight: bold; -fx-font-size : 18px;-fx-font-family: \"Times New Roman\";");

        save.setOnAction(event ->{
            String userData = nameField.getText();
            System.out.println("User name is : "+ userData);
        });

        Region gap = new Region();
        gap.setPrefHeight(100);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.getChildren().addAll(gap,name,nameField,save);

        //Drop Down List also called as ComboBox 
        ObservableList<String> languages = FXCollections.observableArrayList(jdbc.getLangList());
        ComboBox<String> comboBox = new ComboBox<>(languages);
        
        ObservableList<String> initialVersionList = FXCollections.observableArrayList(jdbc.getVersionList(comboBox.getValue()));
        ComboBox<String> versionComboBox = new ComboBox<>(initialVersionList);
        versionComboBox.setValue("Options"); // Set the default value
        
        // Add a listener to the comboBox's value property
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Update the options in the versionComboBox based on the selected value in comboBox
            ObservableList<String> updatedVersionList = FXCollections.observableArrayList(jdbc.getVersionList(newValue));
            versionComboBox.setItems(updatedVersionList);
        
            // Optionally, set a default value for the versionComboBox
            versionComboBox.setValue("Options");
        });

        // Set an initial value for the comboBox
        comboBox.setValue("Language");

        // Rest of your code
        GridPane.setConstraints(comboBox, 1, 1);
        comboBox.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-font-family: \"Times New Roman\";");
        GridPane.setConstraints(versionComboBox, 2, 1);
        versionComboBox.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-font-family: \"Times New Roman\";");

        
        TextArea informationTextArea = new TextArea();
        informationTextArea.setPrefSize(400, 800); // Set the size as needed
        informationTextArea.setEditable(false); // Make it non-editable
        informationTextArea.setOpacity(0.75);

        informationTextArea.setText("Information will be displayed here.");

        Button info = new Button("Get me information");
        
        info.setOnAction(event -> {
            // Call the JDBC method to fetch information based on the selected language and version
            String information = jdbc.getFeature(comboBox.getValue(), versionComboBox.getValue());
        
            // Update the TextArea with the fetched information
            informationTextArea.setText(information);
        });

        
        HBox hBox = new HBox(10, comboBox,versionComboBox,info);
        hBox.setAlignment(Pos.TOP_CENTER);

        VBox vBox = new VBox(10, gridPane,hBox,informationTextArea);
        vBox.setAlignment(Pos.TOP_CENTER);

        Button feedbackButton = new Button("FeedBack");
        feedbackButton.setStyle("-fx-font-weigth:bold;-fx-font-size:18px;-fx-font-family\"Times New Roman\";");
        feedbackButton.setPrefSize(150, 40);
        StackPane.setAlignment(feedbackButton, Pos.BOTTOM_CENTER);

        rightPane.setBackground(background);
        rightPane.getChildren().addAll(backgroundOverlay,vBox,feedbackButton);

        feedbackButton.setOnAction(event->showFeedBackPopup(primaryStage));

        return rightPane;
    }

    private void showFeedBackPopup(Stage primaryStage){
        Popup popup = new Popup();

        Label popLabel = new Label("Feedback Form");
        popLabel.setStyle("-fx-font-size:18px");

        TextArea feedbackTextArea = new TextArea();
        feedbackTextArea.setPromptText("Enter your feedback here...");
        feedbackTextArea.setPrefSize(300, 150);

        // Create a HBox to hold the star rating
        HBox starRatingBox = new HBox(5);
        starRatingBox.setAlignment(Pos.CENTER);

        // Create 5 ImageView objects for the stars
        ImageView[] stars = new ImageView[5];
        for (int i = 0; i < 5; i++) {
            ImageView star = new ImageView(new Image("file:///D:\\JavaFX\\VersionVerse\\star_empty.png"));
            star.setFitHeight(30);
            star.setFitWidth(30);

            final int rating = i + 1; // Rating value for this star
            star.setOnMouseClicked(event -> {
                
                System.out.println("Clicked star rating: " + rating);

                // Update star images based on the rating
                for (int j = 0; j < 5; j++) {
                    if (j < rating) {
                        stars[j].setImage(new Image("file:///D:\\JavaFX\\VersionVerse\\star_filled.png")); // You should replace "star_filled.png" with your filled star image URL
                    } else {
                        stars[j].setImage(new Image("file:///D:\\JavaFX\\VersionVerse\\star_empty.png")); // You should replace "star_empty.png" with your empty star image URL
                    }
                }
            });

            stars[i] = star;
            starRatingBox.getChildren().add(star);
        }

        // Create a Submit button
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-font-size: 18px");

        submitButton.setOnAction(event -> {

            String feedback = feedbackTextArea.getText();
            System.out.println("Feedback submitted: " + feedback);

            popup.hide();
        });


        VBox popVBox = new VBox(10 );
        popVBox.setAlignment(Pos.CENTER);
        popVBox.getChildren().addAll(popLabel,starRatingBox,feedbackTextArea,submitButton);

        popup.getContent().add(popVBox);
        
        popup.setAutoHide(true);
        popup.show(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
