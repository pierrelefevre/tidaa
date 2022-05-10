/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj.labb5;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;
import javafx.application.Platform;
import javafx.util.Pair;
import javafx.scene.chart.LineChart;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javax.imageio.*;

/**
 *
 * @author Pierre Le Fevre (pierrelf@kth.se) and Emil Karlsson (emilk2@kth.se)
 */
public class ImageModel
{

    enum ToolType
    {
        WindowLevel, Invert, Blur, Brightness
    }

    private ImageController controller;
    private ImageWrapper image;
    private int[] cachedPixels;
    private int[] manipPixels;
    private Image manipImage;
    private ReentrantLock renderLock;
    private Boolean shouldContinueRendering;
    private LineChart lineChart;

    private ArrayList<Pair<ITool, Float[]>> tools;

    /**
     * Create a new Image Model
     *
     * @param controller A reference to an Image Controller
     */
    public ImageModel(ImageController controller)
    {
        this.controller = controller;

        tools = new ArrayList();
        tools.add(new Pair(new WindowLevelTool(), new Float[2]));
        tools.add(new Pair(new InvertTool(), new Float[1]));
        tools.add(new Pair(new BlurTool(), new Float[3]));
        tools.add(new Pair(new BrightnessTool(), new Float[1]));

        cachedPixels = new int[0];
        manipPixels = new int[0];
        manipImage = null;

        renderLock = new ReentrantLock();
        shouldContinueRendering = false;

        lineChart = IntensityHistogram.generate(manipPixels);
    }

    /**
     * Generates the BufferedImage
     *
     * @return Buffered version of RGB pixel array
     */
    public BufferedImage getBufferedImage(){

        // retrieve image
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.getHeight(); i++)
        {
            for(int j = 0; j < image.getWidth(); j++){
                bufferedImage.setRGB(j, i, manipPixels[j+i*image.getWidth()]);
            }
        }

        return bufferedImage;
    }

    /**
     * Load a new image and cache the pixels
     *
     * @param path path to image
     */
    public void loadImage(String path) throws FileNotFoundException
    {
        image = new ImageWrapper(new Image(new FileInputStream(path)));

        cachedPixels = image.getPixels();
        manipPixels = new int[cachedPixels.length];

        resetManip();
        applyManip();
    }

    /**
     * Generates Image for the current manipulated pixels and returns it
     *
     * @return Image made from manipulated pixels
     */
    public Image getImage()
    {
        if (manipImage == null)
        {
            generateManipImage();
        }
        return manipImage;
    }

    public LineChart getHist()
    {
        return lineChart;
    }

    public void setWindowVal(float value)
    {
        tools.get(ToolType.WindowLevel.ordinal()).getValue()[0] = value;
        applyManip();
    }

    public void setLevelVal(float value)
    {
        tools.get(ToolType.WindowLevel.ordinal()).getValue()[1] = value;
        applyManip();
    }

    public void setBlur(float value)
    {
        tools.get(ToolType.Blur.ordinal()).getValue()[0] = value;
        tools.get(ToolType.Blur.ordinal()).getValue()[1] = (float) image.getWidth();
        tools.get(ToolType.Blur.ordinal()).getValue()[2] = (float) image.getHeight();
        applyManip();
    }

    public void setBrightness(float value)
    {
        tools.get(ToolType.Brightness.ordinal()).getValue()[0] = value;
        applyManip();
    }

    public void setInvert(float value)
    {
        tools.get(ToolType.Invert.ordinal()).getValue()[0] = value;
        applyManip();
    }

    /**
     * Generates new cache image from manipulated pixels
     */
    private void generateManipImage()
    {
        int W = image.getWidth();
        int H = image.getHeight();
        WritableImage img = new WritableImage(W, H);
        PixelWriter pWriter = img.getPixelWriter();
        pWriter.setPixels(0, 0, W, H, PixelFormat.getIntArgbInstance(), manipPixels, 0, W);
        manipImage = img;
    }

    /**
     * Reset every tools values
     */
    private void resetManip()
    {
        for (var tool : tools)
        {
            Arrays.fill(tool.getValue(), 0.0f);
        }
    }

    /**
     * Starts a new thread that renders image and generates histogram
     */
    private void applyManip()
    {
        ImageRenderThread worker = new ImageRenderThread(this);
        worker.start();
    }

    /**
     * Renders image and generates histogram. The function is thread safe as it
     * only allows one thread to render and generate at any given time
     */
    protected void applyManipThreadFn()
    {
        if (!renderLock.tryLock())
        {
            shouldContinueRendering = true;
            return;
        }

        try
        {
            do
            {
                shouldContinueRendering = false;
                manipPixels = cachedPixels;
                for (var tool : tools)
                {
                    manipPixels = tool.getKey().manip(manipPixels, tool.getValue());
                }
                generateManipImage();
                lineChart = IntensityHistogram.generate(manipPixels);
                Runnable updater = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        controller.onRenderFinish();
                    }
                };
                Platform.runLater(updater);
            } while (shouldContinueRendering);
        } finally
        {
            renderLock.unlock();
        }
    }
}
