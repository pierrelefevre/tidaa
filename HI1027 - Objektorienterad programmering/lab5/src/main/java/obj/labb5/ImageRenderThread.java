/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj.labb5;

/**
 *
 * @author ownem
 */
public class ImageRenderThread extends Thread
{

    private ImageModel model;

    /**
     * Creates a renderer thread
     *
     * @param model which model to render the image for
     */
    public ImageRenderThread(ImageModel model)
    {
        this.model = model;
    }

    /**
     * Run the renderer
     */
    public void run()
    {
        model.applyManipThreadFn();
    }
}
