package booksdbclient.model;

import java.util.Date;

/** Author model for the books database
 *
 * @author Pierre Le Fevre
 * @author Oscar Ekenlow
 */
public class Author {
    private final int id;
    private final String name;
    private final Date dob;

    public Author(String name, Date dob) {
        this.name = name;
        this.dob = dob;
        this.id = -1;
    }

    public Author(int id, String name, Date dob) {
        this.id = id;
        this.name = name;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
