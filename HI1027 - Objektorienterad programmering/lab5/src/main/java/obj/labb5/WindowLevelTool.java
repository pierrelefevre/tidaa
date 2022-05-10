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
public class WindowLevelTool implements ITool
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

        int window = Math.max(modifiers[0].intValue(), 1);
        int level = modifiers[1].intValue();

        for (int i = 0; i < buffer.length; i++)
        {
            int a = ((buffer[i] >> 24) & 0xff);
            int r = ((buffer[i] >> 16) & 0xff);
            int g = ((buffer[i] >> 8) & 0xff);
            int b = ((buffer[i] >> 0) & 0xff);

            int rNorm = (r - level);
            int gNorm = (g - level);
            int bNorm = (b - level);

            int rNormScaled = rNorm * 255 / window;
            int gNormScaled = gNorm * 255 / window;
            int bNormScaled = bNorm * 255 / window;

            r = rNormScaled + level;
            g = gNormScaled + level;
            b = bNormScaled + level;

            r = Math.max(0, Math.min(r, 255));
            g = Math.max(0, Math.min(g, 255));
            b = Math.max(0, Math.min(b, 255));

            returnBuffer[i] = (a << 24) | (r << 16) | (g << 8) | b;
        }

        return returnBuffer;
    }

}
