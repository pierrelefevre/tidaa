package obj.labb5;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Pierre Le Fevre (pierrelf@kth.se) and Emil Karlsson (emilk2@kth.se)
 */
public class App extends Application
{

    View view;

    @Override
    public void start(Stage stage)
    {
        view = new View(stage);
    }

    public static void main(String[] args)
    {
        launch();
    }

}
