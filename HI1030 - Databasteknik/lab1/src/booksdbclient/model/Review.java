package booksdbclient.model;

import java.util.Date;

/** Review model for the books database
 *
 * @author Pierre Le Fevre
 * @author Oscar Ekenlow
 */
public class Review {
    private final int customerId;
    private final int bookId;
    private final int rating;
    private final String text;
    private final Date date;

    public Review(int customerId, int bookId, int rating, String text, Date date) {
        this.customerId = customerId;
        this.bookId = bookId;
        this.rating = rating;
        this.text = text;
        this.date = date;
    }

    public int getCustomerId() { return customerId; }

    public int getBookId() { return bookId; }

    public int getRating() {
        return rating;
    }

    public String getName() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
}
