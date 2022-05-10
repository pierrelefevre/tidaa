/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj.labb5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.chart.LineChart;
import javafx.scene.image.Image;
import javax.imageio.*;

/**
 *
 * @author Pierre Le Fevre (pierrelf@kth.se) and Emil Karlsson (emilk2@kth.se)
 */
public class ImageController
{

    private View view;
    private ImageModel model;
    private String currentFilepath;
    private String defaultFilepath;

    /**
     * Creates an Image Controller
     *
     * @param view A reference to the UI-view
     */
    public ImageController(View view)
    {
        this.view = view;
        this.model = new ImageModel(this);

        // Load default path
        defaultFilepath = new File("").getAbsolutePath();
        defaultFilepath = defaultFilepath.concat("\\src\\main\\java\\obj\\labb5\\skull_ct.png");
        loadImage(defaultFilepath);
    }

    /**
     * Save current image inside the controller
     *
     * @param saveAs if the user should be prompted to choose filepath
     */
    public void saveImage(boolean saveAs)
    {
        if (currentFilepath == null || saveAs)
        {
            String path = view.saveFilePrompt();
            if (path == null)
            {
                return;
            }
            currentFilepath = path;
        }

        try
        {
            File outputFile = new File(currentFilepath);
            ImageIO.write(model.getBufferedImage(), "PNG", outputFile);
            view.updateInfoText("Successfully saved image");
        } catch (IOException e)
        {
            // Load a default image?
            view.showError("ImageController", "Failed to save image: " + currentFilepath + "\nReason: " + e.getMessage());
        }
    }

    /**
     * Loads image into current image inside the controller
     *
     * @param path path to new image
     */
    public void loadImage(String path)
    {
        if (path != defaultFilepath)
        {
            currentFilepath = path;
        }

        try
        {
            model.loadImage(path);
            view.updateInfoText("Sucessfully loaded new image: " + path);
        } catch (FileNotFoundException e)
        {
            // Load a default image?
            view.showError("ImageController", "Failed to load image: " + path + "\nReason: " + e.getMessage());
        }
    }

    public void setWindowVal(float value)
    {
        model.setWindowVal(value);
        view.updateInfoText("Updating Window value to " + value);
    }

    public void setLevelVal(float value)
    {
        model.setLevelVal(value);
        view.updateInfoText("Updating Level value to " + value);
    }

    public void setBlur(float value)
    {
        model.setBlur(value);
        view.updateInfoText("Updating Blur value to " + value);
    }

    public void setBrightness(float value)
    {
        model.setBrightness(value);
        view.updateInfoText("Updating Brightness value to " + value);
    }

    public void setInvert(float value)
    {
        model.setInvert(value);
        view.updateInfoText("Updating Invert value to " + value);
    }

    public void onRenderFinish()
    {
        view.updateImage(model.getImage());
        view.updateHistogram(model.getHist());
    }

}
