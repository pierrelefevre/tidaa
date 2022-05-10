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
public class InvertTool implements ITool
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

        float inversion = modifiers[0];
        float inversionNorm = inversion / 100.0f;

        for (int i = 0; i < buffer.length; i++)
        {
            int a = ((buffer[i] >> 24) & 0xff);
            int r = ((buffer[i] >> 16) & 0xff);
            int g = ((buffer[i] >> 8) & 0xff);
            int b = ((buffer[i] >> 0) & 0xff);

            int rInv = 255 - r;
            int gInv = 255 - g;
            int bInv = 255 - b;

            int rDiff = rInv - r;
            int gDiff = gInv - g;
            int bDiff = bInv - b;

            r += rDiff * inversionNorm;
            g += gDiff * inversionNorm;
            b += bDiff * inversionNorm;
            returnBuffer[i] = (a << 24) | (r << 16) | (g << 8) | b;
        }
        return returnBuffer;
    }
}
