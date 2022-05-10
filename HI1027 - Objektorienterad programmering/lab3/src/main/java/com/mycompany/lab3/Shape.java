package com.mycompany.lab3; // TODO: Change to your package name

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A representation of an abstract drawable shape. For consistency, (x,y)
 * should represent the upper-left point for all sub shapes. The velocity,
 * (dx,dy), is measured in pixels per second. Subclasses must override the
 * methods - paint(GraphicsContext), defining how to paint the specific subtype
 * - constrain(...), defining how to keep the shape inside a specified area
 * ("window"). Furthermore, som subtypes must override the move methods,
 * depending on whether the added extra members defines absolute or relative
 * coordinates.
 *
 * @author Anders Lindström, anderslm@kth.se 2020-09-15
 */
abstract public class Shape {

    public static final double BILLION = 1_000_000_000.0;

    private double x, y; // position of the balls center
    private double dx, dy; // velocity measured in pixels/second
    private Color color;

    /**
     * Initializes a new Shape with center at (x,y), the specified color and
     * zero velocity.
     *
     * @param x center x
     * @param y center y
     * @param color the color
     */
    protected Shape(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * Initializes a new Shape with center at (0,0), color black and zero
     * velocity.
     */
    protected Shape() {
        this(0.0, 0.0, Color.BLACK);
    }

    /**
     * @return x-coordinate of the upper-left corner
     */
    public double getX() {
        return x;
    }

    /**
     * @return y-coordinate of the upper-left corner
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the new x coordinate of the the upper-left corner
     *
     * @param newX
     */
    public void setX(double newX) {
        x = newX;
    }

    /**
     * Sets the new y coordinate of the the upper-left corner
     *
     * @param newY
     */
    public void setY(double newY) {
        y = newY;
    }

    /**
     * @return the color of this shape
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set the new color for this shape.
     *
     * @param newColor
     */
    public void setColor(Color newColor) {
        this.color = newColor;
    }

    /**
     * @return the velocity in the x-direction, pixels/second
     */
    double getDx() {
        return dx;
    }

    /**
     * @return the velocity in the y-direction, pixels/second
     */
    double getDy() {
        return dy;
    }

    /**
     * Sets the velocity, pixels/second, to (newDx, newDy).
     *
     * @param newDx
     * @param newDy
     */
    public void setVelocity(double newDx, double newDy) {
        dx = newDx;
        dy = newDy;
    }

    /**
     * Moves the center of the ball to (newX, newY).
     *
     * @param newX
     * @param newY
     */
    public void moveTo(double newX, double newY) {
        x = newX;
        y = newY;
    }

    /**
     * Move the shape a distance depending on the elapsed time in nanoseconds.
     * NB - the velocity is measured in pixels/second.
     *
     * @param elapsedTimeNs the elapsed time in nanoseconds.
     */
    public void move(long elapsedTimeNs) {
        x += dx * elapsedTimeNs / BILLION;
        y += dy * elapsedTimeNs / BILLION;
    }

    /**
     * Paint the shape on the screen using the grapchics context. Override this
     * method in subtypes.
     *
     * @param gc the GraphicsContext for drawing
     */
    abstract public void paint(GraphicsContext gc);

    /**
     * Constrains the shape inside the given area/box, by bouncing it off att
     * the edges. The shape is considered a point in this implementation which
     * causes erratic behaviour at the left and bottom edges. Subtypes must
     * override this method to correct this behaviour.
     *
     * @param boxX upper left corner of the "box"
     * @param boxY upper left corner of the "box"
     * @param boxWidth
     * @param boxHeight
     */
    public void constrain(
            double boxX, double boxY, 
            double boxWidth, double boxHeight) {
        // If outside the box - calculate new dx and dy
        if (x < boxX) {
            dx = Math.abs(dx);
        } else if (x > boxWidth) {
            dx = -Math.abs(dx);
        }
        if (y < boxY) {
            dy = Math.abs(dy);
        } else if (y > boxHeight) {
            dy = -Math.abs(dy);
        }
    }

    @Override
    public String toString() {
        String info
                = this.getClass().getName() + ": x=" + x + ", y=" + y
                + ", color=" + color;
        return info;
    }
}
