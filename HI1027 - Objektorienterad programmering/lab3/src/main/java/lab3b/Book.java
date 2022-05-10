package lab3b;

import java.util.ArrayList;
import java.lang.Comparable;
import java.io.Serializable;

import lab3b.Author;
import lab3b.Genre;
import lab3b.Isbn;

/**
 *
 * @author Pierre Le Fevre & Emil Karlsson, TIDAA2 HT2020
 */
public class Book implements Comparable, Serializable {

    /**
     * Rating of book as int (1-5)
     */
    private int rating;

    /**
     * Holds book title
     */
    private final String title;

    /**
     * ISBN object, holds book ISBN
     */
    private final Isbn isbn;

    /**
     * Holds list of authors attributed to the book
     */
    private ArrayList<Author> authors;

    /**
     * Holds book genre as Genre enum
     */
    private Genre genre;

    /**
     * Creates a complete Book object with given information
     * 
     * @param title   title of the book
     * @param isbn    isbn of the book
     * @param rating  rating of the book, capped between 1-5
     * @param authors all the authors of the book
     * @param genre   genre of the book
     * @return A Book object that matches the given information
     */
    public Book(String title, Isbn isbn, int rating, ArrayList<Author> authors, Genre genre) {
        if (rating < 1) {
            this.rating = 1;
        } else if (rating > 5) {
            this.rating = 5;
        } else {
            this.rating = rating;
        }

        this.title = title;
        this.isbn = isbn;
        this.authors = authors;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    /**
     * Compares book by their rating
     */
    @Override
    public int compareTo(Object otherBook) {
        return this.rating - ((Book) otherBook).rating;
    }

    /**
     * Checks if inputted book has same rating
     * 
     * @param otherBook book to compare to
     * @return If books are equal
     */
    @Override
    public boolean equals(Object otherBook) {
        return this.compareTo(otherBook) == 0;
    }

    @Override
    public String toString() {
        String returnStr = "";

        returnStr += "[Title: " + title + "],";
        returnStr += "[Genre: " + genre + "],";

        for (Author a : authors) {
            returnStr += "[Author: " + a.getName() + " @ " + a.getDateOfBirth() + "],";
        }

        returnStr += "[ISBN: " + isbn.getIsbnStr() + "];";

        return returnStr;
    }
}
