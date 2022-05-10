package booksdbclient.view;

import booksdbclient.model.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Main view class of books application
 *
 * @author Pierre Le Fevre
 * @author Oscar Ekenlöw
 * <p>
 * <p>
 * JavaFX general dark theme by Wesos de Queso, Jan 21 2020 @ https://stackoverflow.com/a/58a441758
 */
public class BooksPane extends VBox {

    private TableView<Book> booksTable;
    private ObservableList<Book> booksInTable; // the data backing the table view

    private Button connectButton;

    private ComboBox<SearchMode> searchModeBox;
    private TextField searchField;
    private final Label infoLabel = new Label("Please connect to the database.");

    private MenuBar menuBar;

    private BookDialog bookDialog;
    private AddAuthorDialog addAuthorDialog;
    private SignInDialog signInDialog;
    private AddReviewDialog reviewDialog;

    public BooksPane(BooksDb booksDb) {
        final Controller controller = new Controller(booksDb, this);
        this.init(controller);
    }

    /**
     * Display a new set of books, e.g. from a database select, in the
     * booksTable table view.
     *
     * @param books the books to display
     */
    public void displayBooks(List<Book> books) {
        booksInTable.clear();
        booksInTable.addAll(books);

        for (var col : booksTable.getColumns()) {
            col.prefWidthProperty().bind(booksTable.widthProperty().divide(booksTable.getColumns().size()).subtract(1));
        }
    }

    /**
     * Notify user on input error or exceptions.
     *
     * @param msg  the message
     * @param type types: INFORMATION, WARNING et c.
     */
    protected void showAlertAndWait(String msg, Alert.AlertType type) {
        // types: INFORMATION, WARNING et c.
        Alert alert = new Alert(type, msg);
        alert.showAndWait();
    }

    protected void hasSignedIn(User currentSession) {
        menuBar.getMenus().get(2).setText("\uD83D\uDC64 " + currentSession.getName());
        menuBar.getMenus().get(1).setDisable(false);
        menuBar.getMenus().get(2).getItems().get(0).setText("Sign out");
    }

    protected void hasSignedOut() {
        menuBar.getMenus().get(1).setDisable(true);
        menuBar.getMenus().get(2).setText("\uD83D\uDC64 Anonymous");
        menuBar.getMenus().get(2).getItems().get(0).setText("Sign in");
    }

    protected void hasConnected(boolean status) {
        if (status) {
            menuBar.getMenus().get(2).setDisable(false);
        }
    }

    /**
     * set the small information label next to the search box to inform user about program status.
     *
     * @param msg message to be displayed.
     */
    protected void setInfoLabel(String msg) {
        infoLabel.setText(msg);
    }

    private void init(Controller controller) {
        FlowPane bottomPane = new FlowPane();

        booksInTable = FXCollections.observableArrayList();

        // init views and event handlers
        initBooksTable(controller);
        initSearchView(controller);
        initMenus(controller);
        bookDialog = new BookDialog(controller);
        addAuthorDialog = new AddAuthorDialog();
        signInDialog = new SignInDialog();
        reviewDialog = new AddReviewDialog();

        //Connect button for quick connections
        connectButton = new Button("\uD83D\uDDA7 Connect");
        connectButton.setOnAction(event -> {
            controller.connectDb();
            Platform.runLater(() -> {
                searchField.setDisable(false);
                searchModeBox.setDisable(false);
                connectButton.setVisible(false);
                searchField.requestFocus();
            });
        });

        bottomPane.setHgap(10);
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        bottomPane.getChildren().addAll(searchModeBox, searchField, infoLabel, connectButton);

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(booksTable);
        mainPane.setBottom(bottomPane);
        mainPane.setPadding(new Insets(10, 10, 10, 10));

        this.getChildren().addAll(menuBar, mainPane);
        VBox.setVgrow(mainPane, Priority.ALWAYS);

        //Grab focus to connect button for easy connection
        Platform.runLater(() -> connectButton.requestFocus());
    }

    private void initBooksTable(Controller controller) {
        booksTable = new TableView<>();
        booksTable.setEditable(false); // don't allow user updates (yet)
        booksTable.setPlaceholder(new Label("Oh no, the books ran away again!"));
        booksTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                controller.onUpdateBookSelected(booksTable.getSelectionModel().getSelectedItem());
            }
        });

        // define columns
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN-10");
        TableColumn<Book, Date> publishedCol = new TableColumn<>("Published");
        TableColumn<Book, ArrayList<String>> authorsCol = new TableColumn<>("Authors");
        TableColumn<Book, ArrayList<String>> genresCol = new TableColumn<>("Genres");
        TableColumn<Book, ArrayList<ReviewList>> ratingCol = new TableColumn<>("Rating");
        booksTable.getColumns().addAll(titleCol, isbnCol, publishedCol, authorsCol, genresCol, ratingCol);
        booksTable.autosize();

        // define how to fill data for each cell, 
        // get values from Book properties
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        publishedCol.setCellValueFactory(new PropertyValueFactory<>("published"));
        authorsCol.setCellValueFactory(new PropertyValueFactory<>("authorString"));
        genresCol.setCellValueFactory(new PropertyValueFactory<>("genreString"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("reviews"));

        for (var col : booksTable.getColumns()) {
            col.prefWidthProperty().bind(booksTable.widthProperty().divide(booksTable.getColumns().size()).subtract(1));
        }

        // associate the table view with the data
        booksTable.setItems(booksInTable);
    }

    private void initSearchView(Controller controller) {
        searchField = new TextField();
        searchField.setPromptText("Search for...");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchFor = searchField.getText();
            SearchMode mode = searchModeBox.getValue();
            controller.onSearchSelected(searchFor, mode);
        });
        searchField.setDisable(true);

        searchModeBox = new ComboBox<>();
        searchModeBox.getItems().addAll(SearchMode.values());
        searchModeBox.setValue(SearchMode.Title);
        searchModeBox.setDisable(true);


    }

    private void initMenus(Controller controller) {

        Menu fileMenu = new Menu("\uD83D\uDDA7 " + "Connection");
        MenuItem connectItem = new MenuItem("Connect");
        // event handling (dispatch to controller)
        connectItem.setOnAction(event -> {
            controller.connectDb();
            Platform.runLater(() -> {
                searchField.setDisable(false);
                searchModeBox.setDisable(false);
                connectButton.setVisible(false);
                searchField.requestFocus();
            });
        });
        MenuItem disconnectItem = new MenuItem("Disconnect");
        disconnectItem.setOnAction(event -> {
            controller.disconnectDb();
            Platform.runLater(() -> {
                searchField.requestFocus();
                searchField.setDisable(true);
                searchModeBox.setDisable(true);
                connectButton.setVisible(true);
            });
        });
        fileMenu.getItems().addAll(connectItem, disconnectItem);

        Menu manageMenu = new Menu("\uD83D\uDCD6 " + "Edit");
        MenuItem addItem = new MenuItem("Add");
        addItem.setOnAction(ActionEvent -> controller.onAddSelected());
        manageMenu.setDisable(true);
        manageMenu.getItems().addAll(addItem);

        Menu userMenu = new Menu("\uD83D\uDC64 " + "Anonymous");
        MenuItem logInItem = new MenuItem("Sign in");
        logInItem.setOnAction(event -> {
            if (controller.getCurrentSession() == null) {
                controller.signIn();
            } else {
                controller.signOut();
            }
        });
        userMenu.getItems().addAll(logInItem);
        userMenu.setDisable(true);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, manageMenu, userMenu);
    }

    /**
     * show book dialog
     * @return book information that was added
     */
    public Optional<Book> bookDialog() {
        return bookDialog.showAndWait();
    }

    /**
     * update authors dropdown in book dialog
     * @param a authors list from db
     */
    public void bookDialogUpdate(ArrayList<Author> a) {
        bookDialog.updateAuthorList(a);
    }

    /**
     * clear all data in book dialog
     */
    public void bookDialogClear() {
        bookDialog.clearFormData();
    }

    /**
     * set what mode the book dialog should be in.
     * @param mode dialog mode to change to
     */
    public void bookDialogMode(BookDialog.DialogMode mode) {
        bookDialog.setMode(mode);
    }

    /**
     * set what book to display in book dialog
     * @param b book to display
     */
    public void bookDialogSet(Book b) {
        bookDialog.setBook(b);
    }

    /**
     * show add author dialog
     * @return author if author information was filled in the form
     */
    public Optional<Author> addAuthorDialog() {
        return addAuthorDialog.showAndWait();
    }

    /**
     * show sign in dialog
     * @return user if user information was filled in the form
     */
    public Optional<User> signInDialog() {
        return signInDialog.showAndWait();
    }

    /**
     * show review dialog
     * @return review if review information was filled in the form
     */
    public Optional<Review> addReviewDialog() {
        return reviewDialog.showAndWait();
    }
}
