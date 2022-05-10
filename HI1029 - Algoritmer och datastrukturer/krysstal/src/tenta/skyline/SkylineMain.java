package tenta.skyline;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SkylineMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final ArrayList<Skyline.Building> buildings = Skyline.generateBuildings();
        int startSize = 10;
        BorderPane root = new BorderPane();
        MyCanvas canvas = new MyCanvas(1000, 600);//1000,600
        root.setCenter(canvas);
        final Label nrNodesCaption = new Label("Antal hus: ");
        Slider nrBuildings = new Slider(3, 10000, startSize);
        nrBuildings.setBlockIncrement(1);
        nrBuildings.setMin(1);
        nrBuildings.setMax(30);
        nrBuildings.setValue(startSize);
        final Label nrNodesValue = new Label(Integer.toString((int) nrBuildings.getValue()));
        final Label distanceCaption = new Label("Distance: ");
        final Label distance = new Label("-    ");

        nrBuildings.valueProperty().addListener((ov, oldV, newV) -> {
            if (!newV.equals(oldV))
                nrNodesValue.setText(Integer.toString(newV.intValue()));
        });

        Button displayButton = new Button();
        displayButton.setText("Display");
        displayButton.setOnAction(event -> {
            var points = Skyline.skyline(buildings);
            System.out.println(points);
            canvas.drawPoints(points);
        });

        ToolBar toolBar = new ToolBar();
        toolBar.getItems().add(displayButton);
        toolBar.getItems().add(nrNodesCaption);
        toolBar.getItems().add(nrBuildings);
        toolBar.getItems().add(nrNodesValue);
        toolBar.getItems().add(distanceCaption);
        toolBar.getItems().add(distance);
        root.setTop(toolBar);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Skyline");
        primaryStage.setScene(scene);
        primaryStage.show();
        canvas.drawBuildings(buildings);

    }

}
