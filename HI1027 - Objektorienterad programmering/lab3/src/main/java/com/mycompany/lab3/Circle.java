/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab3;

import javafx.scene.canvas.GraphicsContext;
import com.mycompany.lab3.FillableShape;
import java.util.ArrayList;

/**
 *
 * @author Pierre & Emil
 */
public class Circle extends FillableShape
{

    private double diameter;

    public Circle(double x, double y, double diameter)
    {
        super(x, y);
        this.diameter = diameter;
    }

    public double getDiameter()
    {
        return diameter;
    }

    public void setDiameter(double diameter)
    {
        this.diameter = diameter;
    }

    public void paint(GraphicsContext context)
    {
        context.setStroke(getColor());
        context.setFill(getColor());
        if (isFilled())
        {
            context.fillOval(getX(), getY(), diameter, diameter);
        } else
        {
            context.strokeOval(getX(), getY(), diameter, diameter);
        }
    }

    @Override
    public void constrain(double left, double top, double width, double height)
    {
        var newDx = getDx();
        var newDy = getDy();

        if (getX() < left)
        {
            setX(left);
            newDx = Math.abs(newDx);
        } else if (getX() + diameter > left + width)
        {
            setX(left + width - diameter);
            newDx = -Math.abs(newDx);
        }

        if (getY() < top)
        {
            setY(top);
            newDy = Math.abs(newDy);
        } else if (getY() + diameter > top + height)
        {
            setY(top + height - diameter);
            newDy = -Math.abs(newDy);
        }

        setVelocity(newDx, newDy);
    }

    public String toString()
    {
        return "Circle - Diameter: " + getDiameter();
    }

}
