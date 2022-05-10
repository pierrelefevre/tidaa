package booksdbclient.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface declares methods for querying a Books database.
 * Different implementations of this interface handles the connection and
 * queries to a specific DBMS and database, for example a MySQL or a MongoDB
 * database.
 *
 * @author Oscar Ekenlow
 * @author Pierre Le Fevre
 */
public interface BooksDbInterface {

    /**
     * Initiates connection to database and prepares all possible queries.
     * @param database database name
     * @throws IOException if name is bad
     * @throws SQLException is there is an error in the query or database.
     */
    void connect(String database) throws IOException, SQLException;

    /**
     * disconnects from the database and closes all open statements.
     */
    void disconnect() throws IOException, SQLException;

    /**
     * Executes a search query to the database
     * @param title of book to be searched
     * @return a list of all books that matched
     */
    List<Book> searchBooksByTitle(String title) throws IOException, SQLException;

    /**
     * Executes a search query to the database
     * @param author of book to be searched
     * @return a list of all books that matched
     */
    List<Book> searchBooksByAuthor(String author) throws IOException, SQLException;

    /**
     * Executes a search query to the database
     * @param isbn of book to be searched
     * @return a list of all books that matched
     */
    List<Book> searchBooksByISBN(String isbn) throws IOException, SQLException;

    /**
     * Executes a search query to the database
     * @param genre of book to be searched
     * @return a list of all books that matched
     */
    List<Book> searchBooksByGenre(String genre) throws IOException, SQLException;

    /**
     * Executes a search query to the database
     * @param rating of book to be searched
     * @return a list of all books that matched
     */
    List<Book> searchBooksByRating(String rating) throws IOException, SQLException;

    /**
     * Executes a search query to the database
     * @param bookId of book to be searched
     * @return information about the one book that is found
     */
    Book searchBooksById(int bookId) throws SQLException;

    /**
     * Executes a search query to the database
     * @param email of user that wants to login
     * @param secret of user that wants to login
     * @return a user if user was found with email+secret combo, otherwise null.
     */
    User attemptSignIn(String email, String secret) throws SQLException;

    /**
     * Executes a insert query to the database
     *
     * @param b book to insert
     * @param u user that inserted it
     */
    public void insertBook(Book b, User u) throws SQLException;

    /**
     * Executes a insert query to the database
     *
     * @param b book to insert review on
     * @param u user that inserted it
     * @param r review to insert
     */
    void addReview(Book b, User u, Review r) throws SQLException;

    /**
     * Executes a insert query to the database
     *
     * @param b book to update
     */
    void updateBook(Book b) throws SQLException;

    /**
     * Executes a insert query to the database
     *
     * @param a author to add
     * @param u user that inserted it
     */
    public void insertAuthor(Author a, User u) throws SQLException;

    /**
     * Executes a delete query to the database
     *
     * @param b book to remove
     */
    void deleteBook(Book b) throws SQLException;

    /**
     * Executes a search query to the database
     *
     * @return a list of all authors in the database.
     */
    ArrayList<Author> getAllAuthors() throws SQLException;
}
