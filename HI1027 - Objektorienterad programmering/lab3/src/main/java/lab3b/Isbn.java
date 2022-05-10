package lab3b;

import lab3b.*;
import java.io.Serializable;

/**
 *
 * @author Pierre Le Fevre & Emil Karlsson, TIDAA2 HT2020
 */
public class Isbn implements Serializable {

    private String isbnStr;
    static private final String isbnPattern = "[0-9]{13}";

    /**
     * Creates a valid ISBN Object with the ISBN string provided
     * 
     * @param isbnStr version of a ISBN
     * @return A ISBN Object if isbn is valid
     * @throws IllegalArgumentException if invalid input string
     */
    static public Isbn createIsbn(String isbnStr) throws IllegalArgumentException {
        Isbn isbn = new Isbn();
        isbn.isbnStr = isbnStr;
        isbn.isbnStr = isbnStr.replace("-", ""); // remove all ’-’
        if (isbnStr.matches(isbnPattern)) {
            return isbn;
        } else {
            throw new IllegalArgumentException("Bad isbnStr");
        }

    }

    /**
     * Creates a valid ISBN Object with the ISBN string provided
     * 
     * @return ISBN object as a string
     */
    public String getIsbnStr() {
        return isbnStr;
    }

    @Override
    public String toString() {
        return "ISBN: " + isbnStr;
    }
}
