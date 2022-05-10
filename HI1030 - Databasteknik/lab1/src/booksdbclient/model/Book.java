package booksdbclient.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Representation of a book.
 *
 * @author anderslm@kth.se
 */
public class Book {

    private int bookId;
    private final String isbn; // should check format
    private String title;
    private final Date published;
    private String storyLine = "";
    private final ArrayList<Author> authors;
    private final ArrayList<Genre> genres;
    private final ReviewList reviews;
    private String authorString = "";
    private String genreString = "";

    //    ISBN pattern checker:
    private static final Pattern ISBN_PATTERN =
            Pattern.compile("^\\d{9}[\\d|X]$");

    //
    public static boolean isValidIsbn(String isbn) {
        return ISBN_PATTERN.matcher(isbn).matches();
    }

    public Book(int bookId, String isbn, String title, Date published) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.published = published;
        this.authors = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.reviews = new ReviewList();
    }

    public Book(int bookId, String isbn, String title, Date published, ArrayList<Genre> genres, ArrayList<Author> authors) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.published = published;
        this.authors = new ArrayList<>(authors);
        this.genres = new ArrayList<>(genres);
        this.reviews = new ReviewList();
    }

    public Book(String isbn, String title, Date published, ArrayList<Genre> genres, ArrayList<Author> authors) {
        this.bookId = -1;
        this.isbn = isbn;
        this.title = title;
        this.published = published;
        this.authors = new ArrayList<>(authors);
        this.genres = new ArrayList<>(genres);
        this.reviews = new ReviewList();
    }
    public Book(String isbn, String title, Date published, ArrayList<Genre> genres, ArrayList<Author> authors, String storyLine) {
        this.bookId = -1;
        this.isbn = isbn;
        this.title = title;
        this.published = published;
        this.authors = new ArrayList<>(authors);
        this.genres = new ArrayList<>(genres);
        this.reviews = new ReviewList();
        this.storyLine = storyLine;
    }

    public Book(String isbn, String title, Date published) {
        this(-1, isbn, title, published);
    }

    public void addAuthor(Author a) {
        authors.add(a);
    }

    public void addGenre(Genre g) {
        genres.add(g);
    }

    public ReviewList getReviews() {
        return reviews;
    }

    public int getBookId() {
        return bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Date getPublished() {
        return published;
    }

    public String getStoryLine() {
        return storyLine;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setBookId(int bookId){this.bookId = bookId;}
    public void setTitle(String title){this.title = title;}

    public void setStoryLine(String storyLine) {
        this.storyLine = storyLine;
    }

    public void setReviews(ReviewList reviews) {
        this.reviews.setReviews(reviews.getReviews());
    }

    public void addReviews(ArrayList<Review> reviews) {
        this.reviews.addReviews(reviews);
    }

    public void addReview(Review review) {
        this.reviews.addReview(review);
    }


    public String getGenreString() {
        String info = "";
        for (Genre g : genres) {
            info += g + ", ";
        }
        if (info.length() > 4)
            info = info.substring(0, info.length() - 2);
        else {
            info = "No genres found.";
        }
        return info;
    }

    public String getAuthorString() {
        String info = "";
        for (Author a : authors) {
            info += a.getName() + ", ";
        }
        if (info.length() > 4) {
            info = info.substring(0, info.length() - 2);
        } else {
            info = "No authors found.";
        }
        return info;
    }

    @Override
    public String toString() {
        return title + ", " + isbn + ", " + published.toString() + authors.toString() + genres.toString();
    }
}
