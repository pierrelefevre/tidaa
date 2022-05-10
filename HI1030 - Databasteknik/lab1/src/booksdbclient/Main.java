package booksdbclient;

import booksdbclient.model.BooksDb;
import booksdbclient.view.BooksPane;
import com.mysql.cj.xdevapi.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * Application start up.
 *
 * @author anderslm@kth.se
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        BooksDb booksDb = new BooksDb();

        BooksPane root = new BooksPane(booksDb);

        Scene scene = new Scene(root, 800, 600);
        try {
            File f = new File("./src/stylesheet.css");
            scene.getStylesheets().clear();
            scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        } catch (Exception e) {
            System.out.println("Error loading stylesheet: " + e.getMessage());
        }

        primaryStage.setTitle("Books Database Client");
        // add an exit handler to the stage (X) ?
        primaryStage.setOnCloseRequest(event -> {
            try {
                booksDb.disconnect();
            } catch (Exception e) {
                System.out.println("Error loading stylesheet: " + e.getMessage());
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
