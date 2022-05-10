/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj.labb5;

/**
 *
 * @author Pierre Le Fevre (pierrelf@kth.se) and Emil Karlsson (emilk2@kth.se)
 */
public class BlurTool implements ITool
{

    /**
     * Modifies a copy of given pixels
     *
     * @param buffer array of pixels
     * @param modifiers array of values to use for the manip-function
     * @return Array of modified buffer
     */
    @Override
    public int[] manip(int[] buffer, Float[] modifiers)
    {
        int[] returnBuffer = new int[buffer.length];

        float x_value = modifiers[0] / 50.0f;
        float blurLevelNorm = Math.min(((float) Math.pow(x_value, 6.6f) + x_value * 10.0f) / 100.0f, 1);

        if (blurLevelNorm <= 0)
        {
            return buffer;
        }

        int imageWidth = modifiers[1].intValue();
        int imageHeight = modifiers[2].intValue();

        int boxWidth = Math.max((int) ((float) imageWidth * blurLevelNorm), 1);
        int boxHeight = Math.max((int) ((float) imageHeight * blurLevelNorm), 1);

        for (int h = 0; h < imageHeight; h += boxHeight)
        {
            for (int w = 0; w < imageWidth; w += boxWidth)
            {
                int rTotal = 0;
                int gTotal = 0;
                int bTotal = 0;

                int nPixels = 0;
                for (int i = 0; i < boxHeight; i++)
                {
                    for (int j = 0; j < boxWidth; j++)
                    {
                        int index_w = w + j;
                        int index_h = h + i;
                        if (index_w < imageWidth && index_h < imageHeight)
                        {
                            int index = index_w + index_h * imageWidth;
                            rTotal += (buffer[index] >> 16) & 0xff;
                            gTotal += (buffer[index] >> 8) & 0xff;
                            bTotal += (buffer[index] >> 0) & 0xff;
                            nPixels++;
                        }
                    }
                }

                int rBox = rTotal / nPixels;
                int gBox = gTotal / nPixels;
                int bBox = bTotal / nPixels;

                for (int i = 0; i < boxHeight; i++)
                {
                    for (int j = 0; j < boxWidth; j++)
                    {
                        int index_w = w + j;
                        int index_h = h + i;
                        if (index_w < imageWidth && index_h < imageHeight)
                        {
                            int index = index_w + index_h * imageWidth;
                            int a = (buffer[index] >> 24) & 0xff;
                            returnBuffer[index] = (a << 24) | (rBox << 16) | (gBox << 8) | bBox;
                        }
                    }
                }
            }
        }
        return returnBuffer;
    }
}
