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
public interface ITool
{

    /**
     * Modifies a copy of given pixels
     *
     * @param buffer array of pixels
     * @param modifiers array of values to use for the manip-function
     * @return Array of modified buffer
     */
    public int[] manip(int buffer[], Float[] modifiers);

}
