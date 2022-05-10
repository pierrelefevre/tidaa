package booksdbclient.view;
import booksdbclient.model.Review;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * dialog used to get review to be added to the selected book.
 */
public class AddReviewDialog extends Dialog<Review> {
    private final TextArea reviewTextArea = new TextArea();
    private ComboBox<String> reviewRatingField;

    public AddReviewDialog() {
        build();
    }

    private void build() {
        this.setHeight(300);
        this.setWidth(400);
        this.getDialogPane().setMinWidth(400);
        this.setResizable(true);
        this.initStyle(StageStyle.UTILITY);

        try {
            File f = new File("./src/stylesheet.css");
            this.getDialogPane().getStylesheets().clear();
            this.getDialogPane().getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        } catch (Exception e) {
            System.out.println("Error loading stylesheet: " + e.getMessage());
        }
        ArrayList<String> ratings = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            ratings.add("" + i);
        }

        reviewRatingField = new ComboBox<String>(FXCollections.observableArrayList(ratings));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        Label title = new Label("Create review");
        title.setStyle("-fx-font-size: 32;");
        grid.add(title, 1, 0);
        grid.add(new Label("Rating (1-5) "), 1, 1);
        grid.add(reviewRatingField, 2, 1);
        grid.add(new Label("Your review... "), 1, 2);
        grid.add(reviewTextArea, 2, 2);

        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk
                = new ButtonType("Review", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel
                = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        this.setResultConverter(b -> {
            Review result = null;
            if (b == buttonTypeOk) {
                if (isValidData()) {
                    LocalDate localDate = LocalDate.now();
                    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                    Date reviewDate = Date.from(instant);
                    result = new Review(-1, -1, Integer.parseInt(reviewRatingField.getValue()), reviewTextArea.getText(), reviewDate);
                }
            }

            clearFormData();
            return result;
        });

        Button okButton = (Button) this.getDialogPane().lookupButton(buttonTypeOk);
        okButton.addEventFilter(ActionEvent.ACTION, new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!isValidData()) {
                    event.consume();
                    showErrorAlert("Form error", "Invalid input");
                }
            }
        });
    }

    private boolean isValidData() {
        if(reviewRatingField.getValue() == null)
            return false;
        try{
            if(Integer.parseInt(reviewRatingField.getValue()) <= 0)
                return false;
        }catch(NumberFormatException e){
            return false;
        }

        if (reviewTextArea.getText() == "") {
            return false;
        }
        return true;
    }

    private void clearFormData() {
        reviewRatingField.setValue(null);
        reviewTextArea.setText("");
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }
}
