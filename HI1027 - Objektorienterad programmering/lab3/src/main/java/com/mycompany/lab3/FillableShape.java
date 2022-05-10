/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab3;

import javafx.scene.paint.Color;
import com.mycompany.lab3.Shape;

/**
 *
 * @author Pierre & Emil
 */
abstract public class FillableShape extends Shape
{

    private boolean filled;

    public FillableShape(double x, double y)
    {
        super(x, y, Color.color(Math.random(), Math.random(), Math.random()));
        this.filled = filled;
    }

    public boolean isFilled()
    {
        return filled;
    }

    public void setFilled(boolean filled)
    {
        this.filled = filled;
    }
}
