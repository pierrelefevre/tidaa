package lab3b;

import java.util.ArrayList;
import lab3b.*;

/**
 *
 * @author Pierre Le Fevre & Emil Karlsson, TIDAA2 HT2020
 */
public class CollectionOfBooks {

    /**
     * Collection of books
     */
    ArrayList<Book> collectionOfBooks;

    /**
     * Creates a CollectionOfBook object
     * 
     * @return A CollectionOfBook object
     */
    public CollectionOfBooks() {
        collectionOfBooks = new ArrayList<Book>();
    }

    /**
     * Adds book object to collection
     * 
     * @param book The book object to add
     */
    public void addBook(Book book) {
        collectionOfBooks.add(book);
    }

    /**
     * Removes book object from collection
     * 
     * @param index of book in collection
     */
    public void removeBook(int index) {
        collectionOfBooks.remove(index);
    }

    /**
     * Creates a CollectionOfBook object
     * 
     * @param title of book to search for
     * @return Books with matching titles
     */
    public ArrayList<Book> getBooksByTitle(String title) {
        ArrayList<Book> returnBooks = new ArrayList<>();

        for (var book : collectionOfBooks) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                returnBooks.add(book);
            }
        }
        return returnBooks;
    }

    /**
     * Search for book by Author
     * 
     * @param author Author to search for
     * @return List of books with matching Author
     */
    public ArrayList<Book> getBooksByAuthor(Author authorSearch) {
        ArrayList<Book> returnBooks = new ArrayList<>();

        for (var book : collectionOfBooks) {
            for (var author : book.getAuthors()) {
                if (author.getName().toLowerCase().contains(authorSearch.getName().toLowerCase())) {
                    returnBooks.add(book);
                    break;
                }
            }
        }
        return returnBooks;
    }

    /**
     * Search for book by ISBN
     * 
     * @param isbnStr ISBN as string to search for
     * @return List of books with matching ISBN
     */
    public ArrayList<Book> getBooksByIsbn(String isbnStr) {
        ArrayList<Book> returnBooks = new ArrayList<>();

        for (var book : collectionOfBooks) {
            if (book.getIsbn().getIsbnStr().contains(isbnStr)) {
                returnBooks.add(book);
            }
        }
        return returnBooks;
    }

    /**
     * @return A copied list of all books in collection
     */
    public ArrayList<Book> getBooks() {
        ArrayList<Book> returnBooks = new ArrayList<>();

        for (var book : collectionOfBooks) {
            returnBooks.add(book);
        }
        return returnBooks;
    }
}
