package lab3b;

import lab3b.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.IOException;

/**
 *
 * @author Pierre Le Fevre & Emil Karlsson, TIDAA2 HT2020
 */
public class UserInterface {

    /**
     * Create new scanner object
     */
    Scanner scanner = new Scanner(System.in);

    /**
     * Hold user input string
     */
    String usrIn = "";

    /**
     * Create new collection of books
     */
    public CollectionOfBooks collection;

    /**
     * Creates a User Interface object that can print to console
     * 
     * @return User Interface object
     */
    public UserInterface() {
        collection = new CollectionOfBooks();

        loadFromFile();
    }

    /**
     * Function containing while true loop to iterate menu as long as program is
     * running
     */
    public void menu() {
        boolean running = true;
        while (running) {
            clearScreen();
            System.out.println("Book Collection - Main Menu");
            System.out.println("[A]dd book, [R]emove book, [L]ist books, [S]earch books, [Q]uit and save to file");

            usrIn = scanner.nextLine().toUpperCase();
            switch (usrIn) {
                case "A":
                    addBook();
                    break;

                case "R":
                    removeBook();
                    break;

                case "L":
                    listAllBooks();
                    break;

                case "S":
                    searchForBook();
                    break;

                case "Q":
                    saveToFile();
                    running = false;
                    break;

            }
        }
    }

    /**
     * Add book to collection
     */
    public void addBook() {
        String title = "Unnamed";
        String isbnStr = "";
        ArrayList<Author> authors = new ArrayList<Author>();
        Genre genre = Genre.COMEDY;
        int rating = 1;

        System.out.println("Enter title: ");
        boolean invalidTitle = true;
        while (invalidTitle) {
            usrIn = scanner.nextLine();
            if (usrIn.length() == 0) {
                System.out.println("Invalid title! Try again...");
            } else {
                title = usrIn;
                invalidTitle = false;
            }
        }
        System.out.println("Enter ISBN: ");
        boolean invalidIsbn = true;
        while (invalidIsbn) {
            usrIn = scanner.nextLine();
            usrIn = usrIn.replace("-", "");
            if (usrIn.length() == 0 || !usrIn.matches("[0-9]{13}")) {
                System.out.println("Invalid ISBN! (Should contain 13 digits) Try again...");
            } else {
                isbnStr = usrIn;
                invalidIsbn = false;
            }
        }

        boolean keepAddingAuthors = true;
        while (keepAddingAuthors) {
            System.out.println("Add author [enter empty to quit]: ");
            usrIn = scanner.nextLine();
            if (usrIn.length() == 0) {
                if (authors.size() == 0) {
                    System.out.println("Add atleast one author! REEEEE");
                } else {
                    keepAddingAuthors = false;
                }
            } else {
                authors.add(new Author(usrIn, LocalDate.now()));
            }
        }

        System.out.println("All genres: DRAMA(0), ROMANCE(1), CRIME(2), HORROR(3), COMEDY(4)");
        System.out.println("Set genre: ");
        boolean invalidGenre = true;
        while (invalidGenre) {
            usrIn = scanner.nextLine();
            try {
                int genreIndex = Integer.parseInt(usrIn);
                if (genreIndex < 0 || genreIndex > Genre.values().length - 1) {
                    System.out.println("Invalid genre! Try again...");
                } else {
                    genre = Genre.values()[genreIndex];
                    invalidGenre = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter ONLY numbers! REEEEEE");
            }
        }

        System.out.println("Enter book rating [1-5]");
        boolean invalidRating = true;
        while (invalidRating) {
            usrIn = scanner.nextLine();
            try {
                int ratingInput = Integer.parseInt(usrIn);
                if (ratingInput < 0 || ratingInput > 5) {
                    System.out.println("Invalid rating! Try again...");

                } else {
                    rating = ratingInput;
                    invalidRating = false;
                }

            } catch (NumberFormatException e) {
                System.out.println("Enter ONLY numbers! REEEEEE");
            }
        }

        this.collection.addBook(new Book(title, Isbn.createIsbn(isbnStr), rating, authors, genre));
    }

    /**
     * Remove book from collection
     */
    public void removeBook() {
        var books = this.collection.getBooks();
        if (books.size() == 0) {
            System.out.println("========================================");
            System.out.println("=== There no books in the collection ===");
            System.out.println("========================================");
        } else {
            System.out.println("===========================================");
            System.out.println("=== Select which book to remove (Index) ===");
            System.out.println("===========================================");
            for (int i = 0; i < books.size(); i++) {
                System.out.println("[" + i + "] " + books.get(i));
            }

            while (true) {
                usrIn = scanner.nextLine();
                if (usrIn.length() == 0) {
                    return;
                }
                try {
                    int index = Integer.parseInt(usrIn);
                    if (index < 0 || index > books.size() - 1) {
                        System.out.println(index + " was out of range! Try again...");
                        continue;
                    }
                    this.collection.removeBook(index);
                    break;

                } catch (NumberFormatException e) {
                    System.out.println("Enter ONLY numbers! REEEEEE");
                }
            }
        }

    }

    /**
     * Search menu for books in collection
     */
    public void searchForBook() {
        clearScreen();
        System.out.println("Search books by:");
        System.out.println("[A]uthor, [T]itle, [I]SBN, [B]ack to main menu");

        usrIn = scanner.nextLine().toUpperCase();
        switch (usrIn) {
            case "A":
                usrIn = scanner.nextLine();
                var author = new Author(usrIn, LocalDate.now());
                listBooksByAuthor(author);
                break;
            case "T":
                usrIn = scanner.nextLine();
                listBooksByTitle(usrIn);
                break;
            case "I":
                usrIn = scanner.nextLine();
                listBooksByIsbn(usrIn);
                break;
            case "B":
                break;
        }
    }

    /**
     * UI for listing books searching by title
     * 
     * @param title title of book to search for
     */
    public void listBooksByTitle(String title) {
        System.out.println("Search result: Get by title");
        ArrayList<Book> result = collection.getBooksByTitle(title);
        for (Book b : result) {
            System.out.println(b);
        }
    }

    /**
     * UI for listing books searching by author
     * 
     * @param author author to compare to
     */
    public void listBooksByAuthor(Author author) {
        System.out.println("Search result: Get by author");
        ArrayList<Book> result = collection.getBooksByAuthor(author);
        for (Book b : result) {
            System.out.println(b);
        }
    }

    /**
     * UI for listing books searching by ISBN
     * 
     * @param isbnStr ISBN to compare to
     */
    public void listBooksByIsbn(String isbnStr) {
        System.out.println("Search result: Get by Isbn");
        ArrayList<Book> result = collection.getBooksByIsbn(isbnStr);
        for (Book b : result) {
            System.out.println(b);
        }
    }

    /**
     * Prints all books in collection
     */
    public void listAllBooks() {
        var books = this.collection.getBooks();
        if (books.size() == 0) {
            System.out.println("========================================");
            System.out.println("=== There no books in the collection ===");
            System.out.println("========================================");
        } else {
            System.out.println("====================================");
            System.out.println("== Here are all " + books.size() + " books:\t ==");
            System.out.println("====================================");
            for (var book : books) {
                System.out.println(book);
            }
        }
    }

    /**
     * Save collection to file "books.bookCollection"
     */
    public void saveToFile() {
        try {
            BooksIO.serializeToFile("books.bookCollection", collection.getBooks());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Read collection from file "books.bookCollection"
     */
    public void loadFromFile() {
        try {
            for (var b : BooksIO.deSerializeFromFile("books.bookCollection")) {
                collection.addBook(b);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * "Clears" screen
     */
    private static void clearScreen() {
        System.out.println();
    }
}
