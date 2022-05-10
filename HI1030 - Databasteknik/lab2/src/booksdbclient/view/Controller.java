package booksdbclient.view;

import booksdbclient.model.*;
import javafx.application.Platform;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static javafx.scene.control.Alert.AlertType.*;

/**
 * The controller is responsible for handling user requests and update the view
 * (and in some cases the model).
 *
 * @author Oscar Ekenlow
 * @author Pierre Le Fevre
 */
public class Controller {

    private final BooksPane booksView; // view
    private final BooksDbInterface booksDb; // model
    private User currentSession;

    private ArrayList<Author> allAuthors = new ArrayList<>();
    private List<Book> searchResult;

    public Controller(BooksDbInterface booksDb, BooksPane booksView) {
        this.booksDb = booksDb;
        this.booksView = booksView;
    }

    /**
     * Handles the search functions when the textfield value is changed.
     * @param searchFor string of what to search for
     * @param mode search mode(defined in searchmode enum)
     */
    protected void onSearchSelected(String searchFor, SearchMode mode) {
        try {
            Date d1 = new Date();
            searchResult = null;
            switch (mode) {
                case Title:
                    searchResult = booksDb.searchBooksByTitle(searchFor);
                    break;
                case ISBN:
                    searchResult = booksDb.searchBooksByISBN(searchFor);
                    break;
                case Author:
                    searchResult = booksDb.searchBooksByAuthor(searchFor);
                    break;
                case Genre:
                    searchResult = booksDb.searchBooksByGenre(searchFor);
                    break;
                case Rating:
                    searchResult = booksDb.searchBooksByRating(searchFor);
                    break;
                default:
            }
            if (searchResult == null || searchResult.isEmpty()) {
                Platform.runLater(() -> booksView.setInfoLabel("No results found."));
            } else {
                Date d2 = new Date();
                long diff = d2.getTime() - d1.getTime();
                Platform.runLater(() -> booksView.setInfoLabel(searchResult.size() + " results " + "(" + diff + "ms)"));
            }
            Platform.runLater(() -> booksView.displayBooks(searchResult));
        } catch (Exception e) {
            Platform.runLater(() -> booksView.showAlertAndWait("Database error: " + e.getMessage(), ERROR));
        }
    }

    /**
     * connect to the database.
     * !!! must disconnect from database before quitting application
     */
    protected void connectDb() {
        Runnable dbTask = () -> {
            try {
                booksDb.connect("booksdb");
                Platform.runLater(() -> {
                    booksView.setInfoLabel("Connected!");
                    booksView.hasConnected(true);
                    try {
                        booksView.displayBooks(booksDb.searchBooksByTitle(""));
                    } catch (Exception e) {
                        booksView.showAlertAndWait("Error displaying first list of books: " + e.getMessage(), ERROR);
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> booksView.showAlertAndWait("Connection error: " + e.getMessage(), ERROR));
            }
        };
        new Thread(dbTask).start();
    }

    /**
     * disconnect from the database.
     * must be connected already
     */
    protected void disconnectDb() {
        Runnable dbTask = () -> {
            try {
                booksDb.disconnect();
                Platform.runLater(() -> booksView.setInfoLabel("Disconnected."));
                signOut();
                Platform.runLater(() -> booksView.hasConnected(false));
            } catch (Exception e) {
                Platform.runLater(() -> booksView.showAlertAndWait("Disconnect error: " + e.getMessage(), ERROR));
            }
        };
        new Thread(dbTask).start();
    }

    /**
     * handles sign in requests from the frontend.
     */
    protected void signIn() {
        Optional<User> result = booksView.signInDialog();
        if (result.isPresent()) {
            Runnable dbTask = () -> {
                try {
                    User u = result.get();
                    currentSession = booksDb.attemptSignIn(u.getEmail(), u.getSecret());
                    if (currentSession != null) {
                        Platform.runLater(() -> booksView.hasSignedIn(currentSession));
                        Platform.runLater(() -> booksView.setInfoLabel("Welcome back, " + currentSession.getName() + ". Double-click rows to update/delete books."));
                    } else {
                        Platform.runLater(() -> booksView.setInfoLabel("Could not find matching email + secret combo."));
                    }
                } catch (Exception e) {
                    Platform.runLater(() -> booksView.setInfoLabel("Login error: " + e.getMessage()));
                }
            };
            new Thread(dbTask).start();
        }
    }

    /**
     * sign out: clears the currentSession
     */
    protected void signOut() {
        Platform.runLater(() -> {
            currentSession = null;
            booksView.hasSignedOut();
            booksView.setInfoLabel("Signed out.");
        });
    }

    public User getCurrentSession() {
        return currentSession;
    }

    /**
     * handles the add book function, opens book dialog in create mode
     * updates database with created book when finished
     */
    public void onAddSelected() {
        booksView.bookDialogMode(BookDialog.DialogMode.CREATE);
        booksView.bookDialogUpdate(getAllAuthors());
        Optional<Book> result = booksView.bookDialog();
        if (result.isPresent()) {
            Book b = result.get();
            Runnable dbTask = () -> {
                try {
                    booksDb.insertBook(b, currentSession);
                } catch (Exception e) {
                    Platform.runLater(() -> booksView.setInfoLabel("Add book error: " + e.getMessage()));
                }
            };
            new Thread(dbTask).start();
        }
        onSearchSelected("", SearchMode.Title);
        Platform.runLater(() -> booksView.displayBooks(searchResult));
    }

    /**
     * handles add author function, opens add author dialog and updates database with new author
     */
    public void onAddAuthorSelected() {
        Optional<Author> result = booksView.addAuthorDialog();
        if (result.isPresent()) {
            Author a = result.get();
            Runnable dbTask = () -> {
                try {
                    booksDb.insertAuthor(a, currentSession);
                    Platform.runLater(() -> booksView.bookDialogUpdate(getAllAuthors()));
                } catch (Exception e) {
                    Platform.runLater(() -> booksView.setInfoLabel("Add author error: " + e.getMessage()));
                }
            };
            new Thread(dbTask).start();
        }

    }

    /**
     * deletes a book
     * @param bTable book to be deleted
     */
    public void onDeleteSelected(Book bTable) {
        Runnable dbTask = () -> {
            if (currentSession == null) {
                Platform.runLater(() -> booksView.setInfoLabel("You must sign in to update books."));
                return;
            }
            try {
                booksDb.deleteBook(bTable);
            } catch (SQLException e) {
                Platform.runLater(() -> booksView.setInfoLabel("Error deleting book" + e.getMessage()));
            }
        };
        new Thread(dbTask).start();
        onSearchSelected("", SearchMode.Title);
        Platform.runLater(() -> booksView.displayBooks(searchResult));
    }

    /**
     * add review to book
     * @param b book to add review to
     */
    public void onReviewSelected(Book b) {
        Optional<Review> result = booksView.addReviewDialog();
        Runnable dbTask = () -> {
            try {
                if (result.isPresent()) {
                    Review rRev = result.get();
                    booksDb.addReview(b, currentSession, rRev);
                }
            } catch (SQLException e) {
                Platform.runLater(() -> booksView.setInfoLabel("Review could not be added. " + e.getMessage()));
            }
        };
        new Thread(dbTask).start();
    }

    /**
     * update book information from book dialog. set book dialog to UPDATE mode.
     * @param bTable book to be updated
     */
    public void onUpdateBookSelected(Book bTable) {
        if (currentSession == null) {
            Platform.runLater(() -> booksView.setInfoLabel("You must sign in to update books."));
            return;
        }
        Runnable dbTask = () -> {
            try {
                Platform.runLater(() -> {
                    booksView.bookDialogMode(BookDialog.DialogMode.UPDATE);
                    try {
                        booksView.bookDialogSet(booksDb.searchBooksById(bTable.getBookId()));
                    } catch (Exception e) {
                        booksView.setInfoLabel("Error updating book: " + e.getMessage());
                    }
                });
                Platform.runLater(() -> {
                    booksView.bookDialogUpdate(getAllAuthors());
                    Optional<Book> result = booksView.bookDialog();

                    if (result.isPresent()) {
                        Book bRes = result.get();
                        bRes.setBookId(bTable.getBookId());
                        try {
                            booksDb.updateBook(bRes);
                        } catch (Exception e) {
                            booksView.setInfoLabel("Error updating book: " + e.getMessage());
                        }
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> booksView.setInfoLabel("Error updating book: " + e.getMessage()));
            }
        };
        new Thread(dbTask).start();
        onSearchSelected("", SearchMode.Title);
        Platform.runLater(() -> booksView.displayBooks(searchResult));
    }

    /**
     * get all present authors as an arraylist
     * @return all authors from db
     */
    public ArrayList<Author> getAllAuthors() {
        try {
            allAuthors.clear();
            allAuthors.addAll(booksDb.getAllAuthors());
        } catch (Exception e) {
            Platform.runLater(() -> booksView.setInfoLabel("Fetch all authors failed: " + e.getMessage()));

        }
        return allAuthors;
    }

}

