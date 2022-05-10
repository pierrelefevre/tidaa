package booksdbclient.model;

/** User model for the books database
 *
 * @author Pierre Le Fevre
 * @author Oscar Ekenlow
 */
public class User {
    private final String name;
    private final String email;
    private final String secret;
    private final int customerId;

    public User(String name, String email, String secret, int customerId) {
        this.name = name;
        this.email = email;
        this.secret = secret;
        this.customerId = customerId;
    }

    public User(String email, String secret) {
        this.name = "";
        this.email = email;
        this.secret = secret;
        this.customerId = -1;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSecret() {
        return secret;
    }

    public int getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", secret='" + secret + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}
