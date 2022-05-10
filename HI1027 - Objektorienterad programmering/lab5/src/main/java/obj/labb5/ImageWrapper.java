/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj.labb5;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import java.nio.IntBuffer;

/**
 *
 * @author Pierre Le Fevre (pierrelf@kth.se) and Emil Karlsson (emilk2@kth.se)
 */
public class ImageWrapper
{

    private final Image nativeImage;

    public ImageWrapper(Image image)
    {
        this.nativeImage = image;
    }

    /**
     * Get the native JavaFX image object from the image wrapper
     *
     * @return java Image object
     */
    public Image getNativeImage()
    {
        return nativeImage;
    }

    /**
     *
     * @return width of image in pixels
     */
    public int getWidth()
    {
        return (int) nativeImage.getWidth();
    }

    /**
     *
     * @return height of image in pixels
     */
    public int getHeight()
    {
        return (int) nativeImage.getHeight();
    }

    /**
     * Returns pixels from image as int array
     *
     * @return int array of pixels
     */
    public int[] getPixels()
    {
        IntBuffer colors = IntBuffer.allocate(getWidth() * getHeight());

        PixelReader reader = nativeImage.getPixelReader();
        WritablePixelFormat<IntBuffer> wFormat = WritablePixelFormat.getIntArgbInstance();

        reader.getPixels(0, 0, getWidth(), getHeight(), wFormat, colors, getWidth());

        return colors.array();
    }
}
