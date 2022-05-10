package obj.labb5;

import java.io.File;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Pierre Le Fevre (pierrelf@kth.se) and Emil Karlsson (emilk2@kth.se)
 */
public class View
{

    private ImageController controller;

    private BorderPane border;
    private FlowPane leftPane;
    private FlowPane bottomPane;
    private Stage stage;
    private FileChooser fileChooser;

    /**
     * Creates a View object which contains UI elements
     *
     * @param stage Stage object to put UI elements in
     */
    public View(Stage stage)
    {
        this.stage = stage;
        controller = new ImageController(this);

        fileChooser = new FileChooser();

        border = new BorderPane();

        leftPane = new FlowPane();
        leftPane.setPadding(new Insets(10, 10, 10, 10));
        leftPane.setVgap(30);
        leftPane.setHgap(30);

        border.setLeft(leftPane);

        bottomPane = new FlowPane();
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        bottomPane.setHgap(40);
        createWindow();
        createLevel();
        createInvert();
        createBrightness();
        createBlur();

        border.setBottom(bottomPane);

        Menu menu = new Menu("File");
        MenuItem openFile = new MenuItem("Open File...");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As...");
        MenuItem exit = new MenuItem("Exit");

        exit.setOnAction((ActionEvent t) ->
        {
            Platform.exit();
        });

        openFile.setOnAction((final ActionEvent e) ->
        {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null)
            {
                try
                {
                    controller.loadImage(file.getAbsolutePath());
                } catch (Exception fe)
                {
                    showError("Open file...", fe.getMessage());
                }
            }
        });

        save.setOnAction((final ActionEvent e) ->
        {
            controller.saveImage(false);
        });

        saveAs.setOnAction((final ActionEvent e) ->
        {
            controller.saveImage(true);
        });

        menu.getItems().add(openFile);
        menu.getItems().add(save);
        menu.getItems().add(saveAs);
        menu.getItems().add(exit);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

        border.setTop(menuBar);

        Scene scene = new Scene(border);
        stage.setScene(scene);
        stage.setWidth(1280);
        stage.setHeight(800);
        stage.setTitle("Image Processing");

        stage.show();

        //Refresh image view
        controller.setWindowVal(255);

    }

    /**
     * Creates the window slider
     */
    private void createWindow()
    {
        final Label lWindow = new Label("Window");
        final Slider window = new Slider();
        window.setMin(0);
        window.setMax(255);
        window.setValue(255);
        window.setShowTickLabels(true);
        window.setShowTickMarks(true);
        window.setMajorTickUnit(64);
        window.setMinorTickCount(32);
        window.setBlockIncrement(16);

        window.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val)
            {
                controller.setWindowVal(new_val.floatValue());
            }
        });

        bottomPane.getChildren().add(lWindow);
        bottomPane.getChildren().add(window);
    }

    /**
     * Creates the level slider
     */
    private void createLevel()
    {
        final Label lLevel = new Label("Level");
        final Slider level = new Slider();
        level.setMin(0);
        level.setMax(255);
        level.setValue(128);
        level.setShowTickLabels(true);
        level.setShowTickMarks(true);
        level.setMajorTickUnit(64);
        level.setMinorTickCount(32);
        level.setBlockIncrement(16);

        level.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val)
            {
                controller.setLevelVal(new_val.floatValue());
            }
        });

        bottomPane.getChildren().add(lLevel);
        bottomPane.getChildren().add(level);
    }

    /**
     * Creates the invert slider
     */
    private void createInvert()
    {
        final Label lInvert = new Label("Invert");
        final Slider invert = new Slider();
        invert.setMin(0);
        invert.setMax(100);
        invert.setValue(0);
        invert.setShowTickLabels(true);
        invert.setShowTickMarks(true);
        invert.setMajorTickUnit(50);
        invert.setMinorTickCount(25);
        invert.setBlockIncrement(25);

        invert.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val)
            {
                controller.setInvert(new_val.floatValue());
            }
        });

        bottomPane.getChildren().add(lInvert);
        bottomPane.getChildren().add(invert);
    }

    /**
     * Creates the brightness slider
     */
    private void createBrightness()
    {
        final Label lBrightness = new Label("Brightness");
        final Slider brightness = new Slider();
        brightness.setMin(-100);
        brightness.setMax(100);
        brightness.setValue(0);
        brightness.setShowTickLabels(true);
        brightness.setShowTickMarks(true);
        brightness.setMajorTickUnit(50);
        brightness.setMinorTickCount(5);
        brightness.setBlockIncrement(25);

        brightness.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val)
            {
                controller.setBrightness(new_val.floatValue());
            }
        });

        bottomPane.getChildren().add(lBrightness);
        bottomPane.getChildren().add(brightness);
    }

    /**
     * Creates the blur slider
     */
    private void createBlur()
    {
        final Label lBlur = new Label("Blur");
        final Slider blur = new Slider();
        blur.setMin(0);
        blur.setMax(100);
        blur.setValue(0);
        blur.setShowTickLabels(true);
        blur.setShowTickMarks(true);
        blur.setMajorTickUnit(25);
        blur.setMinorTickCount(5);
        blur.setBlockIncrement(10);

        blur.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val)
            {
                controller.setBlur(new_val.floatValue());
            }
        });

        bottomPane.getChildren().add(lBlur);
        bottomPane.getChildren().add(blur);
    }

    /**
     * Updates image in view
     *
     * @param image Inputs the image to be updated in UI
     */
    public void updateImage(Image image)
    {
        ImageView newView = new ImageView(image);
        border.setRight(newView);
    }

    /**
     * Updates the histogram in the view
     *
     * @param lineChart Inputs the new lineChart to be updated in UI
     */
    public void updateHistogram(LineChart lineChart)
    {
        leftPane.getChildren().clear();
        leftPane.getChildren().add(lineChart);
    }

    /**
     * Updates the info text in the view
     *
     * @param info Info string to put in the title
     */
    public void updateInfoText(String info)
    {
        stage.setTitle("Image Processing [" + info + "]");
    }

    /**
     * Shows an error dialog
     *
     * @param where where did the error happen?
     * @param message what happened?
     */
    public void showError(String where, String message)
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error at " + where);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public String saveFilePrompt()
    {
        File file = fileChooser.showSaveDialog(stage);
        return file == null ? null : file.getAbsolutePath();
    }

}
