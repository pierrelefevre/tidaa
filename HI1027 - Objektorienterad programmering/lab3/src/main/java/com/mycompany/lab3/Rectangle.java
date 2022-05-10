/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab3;

import javafx.scene.canvas.GraphicsContext;
import com.mycompany.lab3.FillableShape;

/**
 *
 * @author ownem
 */
public class Rectangle extends FillableShape
{

    private double width, height;

    public Rectangle(double x, double y, double width, double height)
    {
        super(x, y);
        this.width = 20;
        this.height = 20;
    }

    public double getWidth()
    {
        return width;
    }

    public void setWidth(double width)
    {
        this.width = width;
    }

    public double getHeight()
    {
        return height;
    }

    public void setHeight(double height)
    {
        this.height = height;
    }

    public void paint(GraphicsContext context)
    {
        context.setStroke(getColor());
        context.setFill(getColor());
        if (isFilled())
        {
            context.fillRect(getX(), getY(), width, height);
        } else
        {
            context.strokeRect(getX(), getY(), width, height);
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
        } else if (getX() + this.width > left + width)
        {
            setX(left + width - this.width);
            newDx = -Math.abs(newDx);
        }

        if (getY() < top)
        {
            setY(top);
            newDy = Math.abs(newDy);
        } else if (getY() + this.height > top + height)
        {
            setY(top + height - this.height);
            newDy = -Math.abs(newDy);
        }
        setVelocity(newDx, newDy);
    }

    public String toString()
    {
        return "Rectangle - Width: " + getWidth() + " Height: " + getHeight();
    }
}
