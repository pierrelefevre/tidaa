/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab3;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Dog
 */
public class Line extends Shape
{

    double x2, y2;
    double dx2, dy2;

    public Line(double x, double y, double x2, double y2)
    {
        super(x, y, Color.color(Math.random(), Math.random(), Math.random()));
        this.x2 = x2;
        this.y2 = y2;
        this.dx2 = getDx();
        this.dy2 = getDy();
    }

    public double getX2()
    {
        return x2;
    }

    public void setX2(double x2)
    {
        this.x2 = x2;
    }

    public double getY2()
    {
        return y2;
    }

    public void setY2(double y2)
    {
        this.y2 = y2;
    }

    public double getDx2()
    {
        return dx2;
    }

    public double getDy2()
    {
        return dy2;
    }

    public void setVelocity2(double dx2, double dy2)
    {
        this.dx2 = dx2;
        this.dy2 = dy2;
    }

    public void paint(GraphicsContext context)
    {
        context.setStroke(getColor());
        context.strokeLine(getX(), getY(), x2, y2);
    }

    @Override
    public void constrain(double left, double top, double width, double height)
    {
        var newDx = getDx();
        var newDy = getDy();
        var newDx2 = getDx2();
        var newDy2 = getDy2();

        // FIRST COORDINATE
        if (getX() < left)
        {
            setX(left);
            newDx = Math.abs(newDx);
        } else if (getX() > left + width)
        {
            setX(left + width);
            newDx = -Math.abs(newDx);
        }

        if (getY() < top)
        {
            setY(top);
            newDy = Math.abs(newDy);
        } else if (getY() > top + height)
        {
            setY(top + height);
            newDy = -Math.abs(newDy);
        }

        // SECOND COORDINATE
        if (getX2() < left)
        {
            setX2(left);
            newDx2 = Math.abs(newDx2);
        } else if (getX2() > left + width)
        {
            setX2(left + width);
            newDx2 = -Math.abs(newDx2);
        }

        if (getY2() < top)
        {
            setY2(top);
            newDy2 = Math.abs(newDy2);
        } else if (getY2() > top + height)
        {
            setY2(top + height);
            newDy2 = -Math.abs(newDy2);
        }

        setVelocity(newDx, newDy);
        setVelocity2(newDx2, newDy2);
    }

    @Override
    public void move(long elapsedTimeNs)
    {
        super.move(elapsedTimeNs);
        x2 += dx2 * elapsedTimeNs / BILLION;
        y2 += dy2 * elapsedTimeNs / BILLION;
    }

    @Override
    public String toString()
    {
        return "Line from (" + getX() + ", " + getY() + ") to (" + this.x2 + ", " + this.y2 + ")";
    }
}
