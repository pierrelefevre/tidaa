package booksdbclient.view;

import booksdbclient.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


/**
 * Dialog representing a Book, for updating and adding books.
 * The dialog has different modes depending on what is neccessary
 *
 * @author Pierre Le Fevre
 * @author Oscar Ekenlow
 */
public class BookDialog extends Dialog<Book> {

    private final TextField titleField = new TextField();
    private final TextField isbnField = new TextField();

    private ComboBox<Genre> genreChoice;
    private final ArrayList<Genre> activeGenres = new ArrayList<>();
    Label activeGenresLabel = new Label();

    private final DatePicker publishedPick = new DatePicker();

    private ComboBox<Author> authorsChoice;
    private final ArrayList<Author> activeAuthors = new ArrayList<>();
    Label activeAuthorsLabel = new Label();

    private int bookId;
    private ReviewList reviewList;
    private Book currentBook;

    private TableView<Review> reviewTable;
    private ObservableList<Review> reviewsInTable;

    private final TextArea storylineArea = new TextArea();
    private final Label storylineLabel = new Label();
    private final Label title = new Label();
    private final ButtonType buttonTypeDelete = new ButtonType("Delete", ButtonBar.ButtonData.HELP);
    private final ButtonType buttonTypeReview = new ButtonType("Add Review", ButtonBar.ButtonData.HELP_2);

    public enum DialogMode {CREATE, UPDATE}

    ;

    private DialogMode mode;
    private final Controller controller;

    public BookDialog(Controller controller) {
        this.controller = controller;
        build();
    }

    private void build() {

        this.setHeight(600);
        this.setWidth(800);
        this.getDialogPane().setMinWidth(800);
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
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(5);
        grid.setVgap(5);
        title.setText("Book details");
        title.setStyle("-fx-font-size: 32;");
        grid.add(title, 1, 0);
        grid.add(new Label("Title "), 1, 1);
        grid.add(titleField, 2, 1);
        grid.add(new Label("Isbn "), 1, 2);
        grid.add(isbnField, 2, 2);
        grid.add(new Label("published "), 1, 4);
        grid.add(publishedPick, 2, 4);

        activeGenresLabel = new Label();
        activeGenresLabel.setStyle("-fx-font-size: 18;");
        genreChoice = new ComboBox(FXCollections.observableArrayList(Genre.values()));
        genreChoice.setOnAction(e -> {
            Platform.runLater(() -> {
                if (activeGenres.contains(genreChoice.getValue())) {
                    activeGenres.remove(genreChoice.getValue());
                } else {
                    activeGenres.add(genreChoice.getValue());
                }
                activeGenresLabel.setText(genresToString());
            });
        });
        GridPane genreGrid = new GridPane();
        grid.add(new Label("Genres "), 1, 3);
        genreGrid.add(genreChoice, 1, 1);
        genreGrid.add(activeGenresLabel, 2, 1);
        grid.add(genreGrid, 2, 3);

        activeAuthorsLabel = new Label();
        activeAuthorsLabel.setStyle("-fx-font-size: 18;");
        authorsChoice = new ComboBox();
        authorsChoice.setOnAction(e -> {
            Platform.runLater(() -> {
                if (authorsChoice.getValue() != null) {
                    if ("Add new Author...".equals(authorsChoice.getValue().getName())) {
                        controller.onAddAuthorSelected();
                    } else if (containsAuthorChoice()) {//(activeAuthors.contains(authorsChoice.getValue())) {
                        activeAuthors.remove(returnAuthor());
                    } else {
                        activeAuthors.add(authorsChoice.getValue());
                    }
                    activeAuthorsLabel.setText(authorsToString());
                }
            });
        });
        grid.add(new Label("Authors "), 1, 5);
        GridPane authorsGrid = new GridPane();
        authorsGrid.setPadding(new Insets(0, 10, 0, 0));
        authorsGrid.add(authorsChoice, 1, 1);
        authorsGrid.add(activeAuthorsLabel, 2, 1);
        grid.add(authorsGrid, 2, 5);


        grid.add(new Label("Storyline"), 1, 6);
        grid.add(storylineArea, 2, 6);
        grid.add(storylineLabel, 2, 7);

        reviewsInTable = FXCollections.observableArrayList();
        initReviewTable();
        grid.add(reviewTable, 1, 8);
        storylineArea.setWrapText(true);
        storylineArea.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 250 ? change : null));
        storylineArea.textProperty().addListener((observable, oldValue, newValue) -> storylineLabel.setText(storylineArea.getText().length() + "/250"));


        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().setContent(grid);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        this.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        this.getDialogPane().getButtonTypes().add(buttonTypeDelete);
        this.getDialogPane().getButtonTypes().add(buttonTypeReview);

        // this callback returns the result from our dialog, via 
        // Optional<Book> result = dialog.showAndWait();
        // Book book = result.get();
        // see DialogExample, line 31-34
        this.setResultConverter(new Callback<ButtonType, Book>() {
            @Override
            public Book call(ButtonType b) {
                Book result = null;
                if (b == buttonTypeOk) {
                    if (isValidData()) {
                        LocalDate localDate = publishedPick.getValue();
                        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                        Date bookDate = Date.from(instant);

                        result = new Book(
                                isbnField.getText(),
                                titleField.getText(),
                                bookDate,
                                activeGenres,
                                activeAuthors,
                                storylineArea.getText()
                        );
                        result.setBookId(bookId);

                        return result;
                    }

                }
                if (b == buttonTypeDelete) {
                    if (isValidData()) {
                        LocalDate localDate = publishedPick.getValue();
                        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                        Date bookDate = Date.from(instant);

                        result = new Book(
                                bookId,
                                isbnField.getText(),
                                titleField.getText(),
                                bookDate,
                                activeGenres,
                                activeAuthors
                        );
                        controller.onDeleteSelected(result);
                    }
                }
                if (b == buttonTypeReview) {
                    if (isValidData()) {
                        LocalDate localDate = publishedPick.getValue();
                        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                        Date bookDate = Date.from(instant);

                        result = new Book(
                                bookId,
                                isbnField.getText(),
                                titleField.getText(),
                                bookDate,
                                activeGenres,
                                activeAuthors
                        );
                        controller.onReviewSelected(result);
                    }
                }
                clearFormData();
                return null;
            }
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
        if (titleField.getText() == null) {
            return false;
        }
        if (!Book.isValidIsbn(isbnField.getText())) {
            return false;
        }
        if (activeGenres.isEmpty()) {
            return false;
        }
        if (publishedPick.getValue() == null) {
            return false;
        }
        if (activeAuthors.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * clears data in form after it is used
     */
    public void clearFormData() {
        titleField.setText("");
        isbnField.setText("");
        publishedPick.setValue(null);

        genreChoice.setValue(null);
        activeGenres.clear();
        activeGenresLabel.setText(genresToString());

        authorsChoice.setValue(null);
        activeAuthors.clear();
        activeAuthorsLabel.setText(authorsToString());

        storylineArea.clear();
        reviewTable.getItems().clear();
    }

    /**
     * in update mode, use this to set which book the dialog will represent
     *
     * @param b book to represent
     */
    public void setBook(Book b) {
        Platform.runLater(() -> {
            clearFormData();
            currentBook = b;
            bookId = b.getBookId();

            titleField.setText(b.getTitle());
            isbnField.setText(b.getIsbn());

            LocalDate localDate = LocalDate.parse(b.getPublished().toString());
            publishedPick.setValue(localDate);

            genreChoice.setValue(null);
            activeGenres.clear();
            activeGenres.addAll(b.getGenres());
            activeGenresLabel.setText(genresToString());

            authorsChoice.setValue(null);
            activeAuthors.clear();
            activeAuthors.addAll(b.getAuthors());
            activeAuthorsLabel.setText(authorsToString());

            storylineArea.setText(b.getStoryLine());

            reviewList = b.getReviews();
            reviewsInTable.addAll(b.getReviews().getReviews());
        });
    }

    /**
     * initiates the table holding all reviews.
     * only visible if in update mode
     */
    private void initReviewTable() {
        reviewTable = new TableView<>();
        reviewTable.setEditable(false); // don't allow user updates (yet)
        reviewTable.setPlaceholder(new Label("No reviews found..."));

        // define columns
        TableColumn<Review, String> ratingCol = new TableColumn<>("Rating");
        TableColumn<Review, String> textCol = new TableColumn<>("Text");
        TableColumn<Review, java.sql.Date> dateCol = new TableColumn<>("Date");
        reviewTable.getColumns().addAll(ratingCol, textCol, dateCol);
        reviewTable.autosize();

        // define how to fill data for each cell,
        // get values from Book properties
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        textCol.setCellValueFactory(new PropertyValueFactory<>("text"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        for (var col : reviewTable.getColumns()) {
            col.prefWidthProperty().bind(reviewTable.widthProperty().divide(reviewTable.getColumns().size()).subtract(1));
        }

        // associate the table view with the data
        reviewTable.setItems(reviewsInTable);
    }


    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    private void showErrorAlert(String title, String info) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(info);
        errorAlert.show();
    }

    /**
     * add all the authors from database after authors have been added
     *
     * @param a authors list from db
     */
    public void updateAuthorList(ArrayList<Author> a) {
        Platform.runLater(() -> {
            authorsChoice.getItems().clear();
            authorsChoice.getItems().add(new Author("Add new Author...", Date.from(Instant.now())));
            for (var item : a) {
                authorsChoice.getItems().add(item);
            }
        });
    }

    /**
     * handle the mode switching in the dialog. Show/hide different components
     *
     * @param mode mode to change to.
     */
    public void setMode(DialogMode mode) {
        Platform.runLater(() -> {
            this.mode = mode;
            switch (mode) {
                case CREATE:
                    title.setText("Add book...");
                    genreChoice.setVisible(true);
                    activeGenresLabel.setVisible(true);
                    authorsChoice.setVisible(true);
                    activeAuthorsLabel.setVisible(true);
                    reviewTable.setVisible(false);
                    this.getDialogPane().getButtonTypes().remove(buttonTypeDelete);
                    this.getDialogPane().getButtonTypes().remove(buttonTypeReview);
                    break;
                case UPDATE:
                    title.setText("Update book...");
                    genreChoice.setVisible(false);
                    activeGenresLabel.setVisible(false);
                    authorsChoice.setVisible(false);
                    activeAuthorsLabel.setVisible(false);
                    reviewTable.setVisible(true);
                    if (!this.getDialogPane().getButtonTypes().contains(buttonTypeReview))
                        this.getDialogPane().getButtonTypes().add(buttonTypeReview);
                    if (!this.getDialogPane().getButtonTypes().contains(buttonTypeDelete))
                        this.getDialogPane().getButtonTypes().add(buttonTypeDelete);
                    break;
            }
        });
    }

    public DialogMode getMode() {
        return mode;
    }

    private boolean containsAuthorChoice() {
        for (Author activeAuthor : activeAuthors) {
            if (authorsChoice != null)
                if (authorsChoice.getValue().getId() == activeAuthor.getId())
                    return true;
        }
        return false;
    }

    private Author returnAuthor() {
        for (Author activeAuthor : activeAuthors) {
            if (authorsChoice != null)
                if (containsAuthorChoice())
                    return activeAuthor;
        }
        return null;
    }

    private String genresToString() {
        String info = "  ";
        for (Genre g : activeGenres) {
            info += g + ", ";
        }
        if (info.length() > 4)
            info = info.substring(0, info.length() - 2);
        else {
            info = "  No genres selected.";
        }
        return info;
    }

    private String authorsToString() {
        String info = "  ";
        for (Author a : activeAuthors) {
            info += a.getName() + ", ";
        }
        if (info.length() > 4) {
            info = info.substring(0, info.length() - 2);
        } else {
            info = "  No authors selected.";
        }
        return info;
    }
}
