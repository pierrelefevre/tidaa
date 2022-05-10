package booksdbclient.view;

import booksdbclient.model.Author;
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
import java.util.Date;

/**
 * dialog used to get information about author to be added in
 */
public class AddAuthorDialog extends Dialog<Author> {
    private final TextField authorNameField = new TextField();
    private final DatePicker authorDobPick = new DatePicker();

    public AddAuthorDialog() {
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

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        Label title = new Label("Add author");
        title.setStyle("-fx-font-size: 32;");
        grid.add(title , 1, 0);
        grid.add(new Label("Name "), 1, 1);
        grid.add(authorNameField, 2, 1);
        grid.add(new Label("Date of birth "), 1, 2);
        grid.add(authorDobPick, 2, 2);

        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk
                = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel
                = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        this.setResultConverter(b -> {
            Author result = null;
            if (b == buttonTypeOk) {
                if (isValidData()) {
                    Date authorDate = null;
                    if (authorDobPick.getValue() != null) {
                        LocalDate localDate = authorDobPick.getValue();
                        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                        authorDate = Date.from(instant);

                        result = new Author(authorNameField.getText(), authorDate);
                    }
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
        return authorNameField.getText() != null;
    }

    private void clearFormData() {
        authorNameField.setText("");
        authorDobPick.setValue(null);
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }
}
