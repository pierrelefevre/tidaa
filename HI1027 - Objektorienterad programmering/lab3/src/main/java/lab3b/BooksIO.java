package lab3b;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import lab3b.*;

/**
 *
 * @author Pierre Le Fevre & Emil Karlsson, TIDAA2 HT2020
 */
public class BooksIO
{

    /**
     * Call this method before the application exits, to store the books, in
     * serialized form, on file the specified file.
     */
    static public void serializeToFile(String filename, List<Book> books) throws IOException
    {

        ObjectOutputStream out = null;

        try
        {
            out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(books);
        } finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
            } catch (Exception e)
            {
                System.out.println("Failed to serialize to file! Error: " + e.getMessage());
            }
        }
    }

    /**
     * Call this method at startup of the application, to deserialize the books
     * from file the specified file.
     */
    @SuppressWarnings("unchecked")
    static public List<Book> deSerializeFromFile(String filename) throws IOException, ClassNotFoundException
    {

        ObjectInputStream in = null;

        try
        {
            in = new ObjectInputStream(new FileInputStream(filename));
            // readObject returns a reference of type Object, hence the down-cast
            List<Book> books = (List<Book>) in.readObject();
            return books;
        } finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            } catch (Exception e)
            {
                System.out.println("Failed to deserialize books from file! Reason: " + e.getMessage());
                System.out.println("---> Returing empty list of books");
                return new ArrayList<>();
            }
        }
    }

    // ...
}
