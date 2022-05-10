package booksdbclient.view;

import booksdbclient.model.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;

import java.io.File;

/** Dialog used for sign in-form
 *
 * @author Pierre Le Fevre
 * @author Oscar Ekenlow
 */
public class SignInDialog extends Dialog<User> {

    private final TextField usernameField = new TextField();
    private final PasswordField secretField = new PasswordField();

    public SignInDialog() {
        buildSignInDialog();
    }

    private void buildSignInDialog() {

        this.setResizable(false);
        this.initStyle(StageStyle.UTILITY);

        try {
            File f = new File("./src/stylesheet.css");
            this.getDialogPane().getStylesheets().clear();
            this.getDialogPane().getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        } catch (Exception e) {
            System.out.println("Error loading stylesheet: " + e.getMessage());
        }

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(5);
        grid.setVgap(5);
        Label title = new Label("Sign in");
        title.setStyle("-fx-font-size: 32;");
        grid.add(title , 1, 0);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(new Label("Email "), 1, 1);
        usernameField.setText("pierrelf@kth.se");
        grid.add(usernameField, 2, 1);
        grid.add(new Label("Password "), 1, 2);
        secretField.setText("123");
        grid.add(secretField, 2, 2);

        this.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk
                = new ButtonType("Sign in", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel
                = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        // this callback returns the result from our dialog, via
        // Optional<Book> result = dialog.showAndWait();
        // Book book = result.get();
        this.setResultConverter(b -> {
            User result = null;
            if (b == buttonTypeOk) {
                if (isValidData()) {
                    result = new User(
                            usernameField.getText(),
                            secretField.getText()
                    );
                }
            }
            clearFormData();
            return result;
        });

        // add an event filter to keep the dialog active if validation fails
        // (yes, this is ugly in FX)
        Button okButton
                = (Button) this.getDialogPane().lookupButton(buttonTypeOk);
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

    // feedback to the user
    private boolean isValidData() {
        return secretField.getText() != null && usernameField.getText() != null;
    }

    public void clearFormData() {
        usernameField.setText("");
        usernameField.requestFocus();
        secretField.setText("");
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }

}
