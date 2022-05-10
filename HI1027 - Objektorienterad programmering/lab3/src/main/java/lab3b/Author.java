package lab3b;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * Holds Author as object
 *
 * @author Pierre Le Fevre & Emil Karlsson, TIDAA2 HT2020
 */
public class Author implements Serializable {

    private String name;
    private LocalDate dateOfBirth;

    /**
     * Returns a complete Book object with given information
     * 
     * @param name        name of the author
     * @param dateOfBirth date of birth of the author
     * @return A list of book objects that matches the given
     */
    public Author(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns current time to simulate author's date of birth
     * 
     * @return Current time
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

}
