package com.mycompany.lab3; // TODO: Change to your package name

import javafx.scene.paint.Color;
import com.mycompany.lab3.Rectangle;
import com.mycompany.lab3.Circle;
import com.mycompany.lab3.Line;
import com.mycompany.lab3.Polygon;

/**
 * A representation of a world containing a set of moving shapes. NB! The worlds
 * y-axis points downward.
 *
 * @author Anders Lindström, anderslm@kth.se 2020-09-15
 */
public class World
{

    private double width, height; // this worlds width and height

    private final Shape[] shapes; // an array of references to the shapes

    /**
     * Creates a new world, containing a pad and a set of balls. NB! The worlds
     * y-axis points downward.
     *
     * @param width the width of this world
     * @param height the height of this world
     */
    public World(double width, double height)
    {
        this.width = width;
        this.height = height;

        shapes = new Shape[12]; // an array of references (change to non-zero size)
        // Create the actual Shape objects (sub types)
        // ....

        for (int i = 0; i < 3; i++)
        {
            shapes[i] = new Rectangle(i * 40, i * 30, i * 10, i * 20);
        }
        for (int i = 0; i < 3; i++)
        {
            shapes[i + 3] = new Circle(i * 20, i * 50, i * 10);
            ((Circle) shapes[i + 3]).setFilled(true);
        }
        for (int i = 0; i < 3; i++)
        {
            shapes[i + 6] = new Line(i * 15 + i * 10, i * 15 + i * 50, i * 30, i * 30);
            ((Line) shapes[i + 6]).setVelocity2(Math.random() * 500, Math.random() * 500);
        }
        for (int i = 0; i < 3; i++)
        {
            shapes[i + 9] = new Polygon(i * 15, i * 10, 10, 100);
        }

        for (var shape : shapes)
        {
            shape.setVelocity(Math.random() * 500, Math.random() * 500);
        }
    }

    /**
     * Sets the new dimensions, in pixels, for this world. The method could be
     * used for example when the canvas is reshaped.
     *
     * @param newWidth
     * @param newHeight
     */
    public void setDimensions(double newWidth, double newHeight)
    {
        this.width = newWidth;
        this.height = newHeight;
    }

    /**
     * Move the world one step, based on the time elapsed since last move.
     *
     * @param elapsedTimeNs the elapsed time in nanoseconds
     */
    public void move(long elapsedTimeNs)
    {
        // alterantive loop: for(Shape s : shapes) { ...
        for (int i = 0; i < shapes.length; i++)
        {
            shapes[i].move(elapsedTimeNs);
            shapes[i].constrain(0, 0, width, height);
        }
    }

    /**
     * Returns a copy of the list of ball references. Due to the implementation
     * of clone, a shallow copy is returned.
     *
     * @return a copy of the list of balls
     */
    public Shape[] getShapes()
    {
        return (Shape[]) shapes.clone();
    }
}
