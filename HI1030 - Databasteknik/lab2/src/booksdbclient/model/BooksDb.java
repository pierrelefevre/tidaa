package booksdbclient.model;

import com.mysql.cj.xdevapi.PreparableStatement;

import java.awt.image.RescaleOp;
import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/** Model for communication to the database
 *
 * @author Pierre Le Fevre
 * @author Oscar Ekenlow
 */
public class BooksDb implements BooksDbInterface {
    Connection con;
    private PreparedStatement sAuthorFromBookId;
    private PreparedStatement sGenreFromBookId;
    private PreparedStatement sBookIdFromAuthor;
    private PreparedStatement sBookIdFromTitle;
    private PreparedStatement sBookIdFromISBN;
    private PreparedStatement sBookIdFromGenre;
    private PreparedStatement sBookIdFromRating;
    private PreparedStatement sRatingFromBookId;
    private PreparedStatement sBook;
    private PreparedStatement sUpdateBook;
    private PreparedStatement delInBooks;
    private PreparedStatement sAddReview;
    private PreparedStatement sAddAuthor;

    private PreparedStatement sInsertGenre;
    private PreparedStatement sInsertWrote;

    private PreparedStatement sSignIn;

    @Override
    public void connect(String database) throws SQLException {
        String server = "jdbc:mysql://127.0.0.1:3306/" + database + "?UseClientEnc=UTF8";
        String user = "user";
        String secret = "password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Connector failure: " + e.getMessage());
        }

        con = DriverManager.getConnection(server, user, secret);
        prepareStatements();
    }

    @Override
    public void disconnect() throws SQLException {
        sBookIdFromAuthor.close();
        sGenreFromBookId.close();
        sAuthorFromBookId.close();
        sBookIdFromTitle.close();
        sBookIdFromISBN.close();
        sBookIdFromGenre.close();
        sBookIdFromRating.close();
        sBook.close();
        sInsertGenre.close();
        sInsertWrote.close();
        sUpdateBook.close();
        delInBooks.close();
        sSignIn.close();
        sAddReview.close();
        con.close();
    }

    private void prepareStatements() throws SQLException {
        sAuthorFromBookId = con.prepareStatement("SELECT * FROM authors, wrote WHERE authors.authorId = wrote.authorId AND wrote.bookId = ?");
        sGenreFromBookId = con.prepareStatement("SELECT * FROM genre WHERE genre.bookId = ?");
        sRatingFromBookId = con.prepareStatement("SELECT * FROM reviews WHERE reviews.bookId = ?");

        String sqlAuthor = "SELECT DISTINCT * FROM books WHERE books.bookId IN (" +
                "SELECT wrote.bookId FROM authors, wrote WHERE wrote.authorId = authors.authorId AND INSTR(authors.name, ?))";
        sBookIdFromAuthor = con.prepareStatement(sqlAuthor);

        String sqlTitle = "SELECT * FROM books WHERE INSTR(books.title, ?);";
        sBookIdFromTitle = con.prepareStatement(sqlTitle);

        String sqlIsbn = "SELECT * FROM books WHERE INSTR(books.isbn, ?);";
        sBookIdFromISBN = con.prepareStatement(sqlIsbn);

        String sqlGenre = "SELECT DISTINCT * FROM books WHERE books.bookId IN (" +
                "SELECT genre.bookId FROM genre WHERE INSTR(genre.genre, ?))";
        sBookIdFromGenre = con.prepareStatement(sqlGenre);

        String sqlRating = "SELECT DISTINCT * FROM books WHERE books.bookId IN (" +
                "SELECT reviews.bookId FROM reviews WHERE INSTR(reviews.rating, ?));";
        sBookIdFromRating = con.prepareStatement(sqlRating);

        String sqlBook = "INSERT INTO books (isbn, title, published, storyLine, customerId) VALUES (?,?,?,?, ?)";
        sBook = con.prepareStatement(sqlBook, Statement.RETURN_GENERATED_KEYS);

        String sqlInsertWrote = "INSERT INTO wrote (bookId, authorId) VALUES (?,?)";
        sInsertWrote = con.prepareStatement(sqlInsertWrote);

        String sqlInsertGenre = "INSERT INTO genre (bookId, genre) VALUES (?,?)";
        sInsertGenre = con.prepareStatement(sqlInsertGenre);

        String sqlUpdateBook = "UPDATE books SET isbn = ?, title = ?, published = ?, storyLine = ? WHERE bookId = ?;";
        sUpdateBook = con.prepareStatement(sqlUpdateBook);

        String sqlDeleteInBooks = "DELETE FROM books WHERE books.bookId = ?;";
        delInBooks = con.prepareStatement(sqlDeleteInBooks);

        String sqlSignIn = "SELECT * FROM customers WHERE email = ? AND password = ?;";
        sSignIn = con.prepareStatement(sqlSignIn);

        String sqlAddReview = "INSERT INTO reviews (bookId, customerId, rating, text, date) VALUES (?, ?, ?, ?, ?)";
        sAddReview = con.prepareStatement(sqlAddReview);

        String sqlAddAuthor = "INSERT INTO authors (name, dob, customerId) VALUES (?, ?, ?)";
        sAddAuthor = con.prepareStatement(sqlAddAuthor);
    }

    @Override
    public List<Book> searchBooksByTitle(String title) throws SQLException, IllegalArgumentException {
        sBookIdFromTitle.setString(1, title);
        ResultSet rs = sBookIdFromTitle.executeQuery();
        return getBookInfoFromResult(rs);
    }

    @Override
    public List<Book> searchBooksByAuthor(String author) throws SQLException, IllegalArgumentException {
        sBookIdFromAuthor.setString(1, author);
        ResultSet rs = sBookIdFromAuthor.executeQuery();
        return getBookInfoFromResult(rs);
    }

    @Override
    public List<Book> searchBooksByISBN(String isbn) throws SQLException, IllegalArgumentException {
        sBookIdFromISBN.setString(1, isbn);
        ResultSet rs = sBookIdFromISBN.executeQuery();
        return getBookInfoFromResult(rs);
    }

    @Override
    public List<Book> searchBooksByGenre(String genre) throws SQLException, IllegalArgumentException {
        sBookIdFromGenre.setString(1, genre);
        ResultSet rs = sBookIdFromGenre.executeQuery();
        return getBookInfoFromResult(rs);
    }

    @Override
    public List<Book> searchBooksByRating(String rating) throws SQLException, IllegalArgumentException {
        sBookIdFromRating.setString(1, rating);
        ResultSet rs = sBookIdFromRating.executeQuery();
        return getBookInfoFromResult(rs);
    }

    @Override
    public Book searchBooksById(int bookId) throws SQLException {
        Statement sBook = con.createStatement();
        String sql = "SELECT * FROM books WHERE bookId = " + bookId + ";";

        ArrayList<Book> result = new ArrayList<>(getBookInfoFromResult(sBook.executeQuery(sql)));
        sBook.close();
        return result.get(0);
    }

    private List<Book> getBookInfoFromResult(ResultSet rs) throws SQLException {
        ArrayList<Book> result = new ArrayList<>();
        while (rs.next()) {
            Book tempBook;
            ArrayList<Genre> tempGenres = new ArrayList<>();
            ArrayList<Author> tempAuthors = new ArrayList<>();

            int id = rs.getInt("bookId");
            String title = rs.getString("title");
            String isbn = rs.getString("isbn");
            Date published = rs.getDate("published");
            String storyline = rs.getString("storyline");

            sAuthorFromBookId.setInt(1, id);
            ResultSet rsAuthor = sAuthorFromBookId.executeQuery();
            while (rsAuthor.next()) {
                int authorId = rsAuthor.getInt("authorId");
                String name = rsAuthor.getString("name");
                Date dob = rsAuthor.getDate("dob");
                tempAuthors.add(new Author(authorId, name, dob));
            }

            sGenreFromBookId.setInt(1, id);
            ResultSet rsGenre = sGenreFromBookId.executeQuery();
            while (rsGenre.next()) {
                tempGenres.add(Genre.valueOf(rsGenre.getString("genre")));
            }
            tempBook = new Book(id, isbn, title, published, tempGenres, tempAuthors);
            tempBook.setStoryLine(storyline);

            sRatingFromBookId.setInt(1, id);
            ResultSet rsRating = sRatingFromBookId.executeQuery();
            while (rsRating.next()) {
                int rCustomerId = rsRating.getInt("customerId");
                int rBookId = rsRating.getInt("bookId");
                int rRating = rsRating.getInt("rating");
                String rText = rsRating.getString("text");
                Date rDate = rsRating.getDate("date");
                Review tempReview = new Review(rCustomerId, rBookId, rRating, rText, rDate);
                tempBook.addReview(tempReview);
            }
            result.add(tempBook);
        }
        return result;
    }

    @Override
    public void insertBook(Book b, User u) throws SQLException {
        sBook.setString(1, b.getIsbn());
        sBook.setString(2, b.getTitle());
        sBook.setDate(3, new java.sql.Date(b.getPublished().getTime()));
        sBook.setString(4, b.getStoryLine());
        sBook.setInt(5, u.getCustomerId());

        try {
            con.setAutoCommit(false);
            sBook.executeUpdate();

            ResultSet rs = sBook.getGeneratedKeys();
            int bookId = -1;
            if (rs.next())
                bookId = rs.getInt(1);

            for (var item : b.getAuthors()) {
                sInsertWrote.setInt(1, bookId);
                sInsertWrote.setInt(2, item.getId());
                sInsertWrote.executeUpdate();
            }

            for (var item : b.getGenres()) {
                sInsertGenre.setInt(1, bookId);
                sInsertGenre.setString(2, item.toString());
                sInsertGenre.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            con.rollback();
        } finally {
            con.setAutoCommit(true);
        }
    }

    @Override
    public ArrayList<Author> getAllAuthors() throws SQLException {
        Statement s = con.createStatement();
        String sql = "SELECT * FROM authors";
        ResultSet rs = s.executeQuery(sql);
        ArrayList<Author> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new Author(rs.getInt("authorId"), rs.getString("name"), rs.getDate("dob")));
        }
        return result;
    }

    @Override
    public void insertAuthor(Author a, User u) throws SQLException {
        sAddAuthor.setString(1, a.getName());
        sAddAuthor.setDate(2, new java.sql.Date(a.getDob().getTime()));
        sAddAuthor.setInt(3, u.getCustomerId());

        try {
            con.setAutoCommit(false);

            sAddAuthor.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            con.rollback();
        } finally {
            con.setAutoCommit(true);
        }
    }

    @Override
    public void addReview(Book b, User u, Review r) throws SQLException {
        sAddReview.setInt(1, b.getBookId());
        sAddReview.setInt(2, u.getCustomerId());
        sAddReview.setInt(3, r.getRating());
        sAddReview.setString(4, r.getText());
        sAddReview.setDate(5, new java.sql.Date(r.getDate().getTime()));
        try {
            con.setAutoCommit(false);

            sAddReview.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            con.rollback();
        } finally {
            con.setAutoCommit(true);
        }

    }

    @Override
    public void updateBook(Book b) throws SQLException {
        sUpdateBook.setString(1, b.getIsbn());
        sUpdateBook.setString(2, b.getTitle());
        sUpdateBook.setDate(3, new java.sql.Date(b.getPublished().getTime()));
        sUpdateBook.setString(4, b.getStoryLine());
        sUpdateBook.setInt(5, b.getBookId());

        sUpdateBook.executeUpdate();
    }

    @Override
    public void deleteBook(Book b) throws SQLException {
        String sqlDeleteInBooks = "DELETE FROM books WHERE books.bookId = ?;";
        delInBooks = con.prepareStatement(sqlDeleteInBooks);

        delInBooks.setInt(1, b.getBookId());

        try {
            con.setAutoCommit(false);
            delInBooks.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            con.rollback();
        } finally {
            con.setAutoCommit(true);
        }

    }

    @Override
    public User attemptSignIn(String email, String secret) throws SQLException {
        sSignIn.setString(1, email);
        sSignIn.setString(2, secret);

        ResultSet rs = sSignIn.executeQuery();
        while (rs.next()) {
            String rName = rs.getString("name");
            String rSecret = rs.getString("password");
            int rId = rs.getInt("customerId");
            if (rSecret.equals(secret)) {
                return new User(rName, email, rSecret, rId);
            }
        }

        return null;
    }

}
