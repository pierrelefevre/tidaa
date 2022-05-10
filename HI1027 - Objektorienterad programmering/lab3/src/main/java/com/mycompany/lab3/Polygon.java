/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab3;

import static com.mycompany.lab3.Shape.BILLION;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 *
 * @author ownem
 */
public class Polygon extends Shape
{

    private ArrayList<Double> vXs;
    private ArrayList<Double> vYs;
    private ArrayList<Double> vDxs;
    private ArrayList<Double> vDys;
    private int noVertices;

    public Polygon(double x, double y, int noVertices, int radius)
    {
        super(x, y, Color.color(Math.random(), Math.random(), Math.random()));
        this.vXs = new ArrayList<>();
        this.vYs = new ArrayList<>();
        this.vDxs = new ArrayList<>();
        this.vDys = new ArrayList<>();
        this.noVertices = noVertices;

        double ranDx = Math.random() * 50;
        double ranDy = Math.random() * 50;

        for (int i = 0; i < noVertices; i++)
        {
            vXs.add(0.0);
            vYs.add(0.0);
            vDxs.add(0.0);
            vDys.add(0.0);

            vXs.set(i, radius * Math.cos(2 * Math.PI * ((double) i / (double) noVertices)) + 140);
            vYs.set(i, radius * Math.sin(2 * Math.PI * ((double) i / (double) noVertices)) + 200);

            vDxs.set(i, ranDx);
            vDys.set(i, ranDy);
        }
    }

    @Override
    public void paint(GraphicsContext context)
    {
        context.setStroke(getColor());

        for (int i = 0; i < noVertices - 1; i++)
        {
            context.strokeLine(vXs.get(i), vYs.get(i), vXs.get(i + 1), vYs.get(i + 1));
        }

        context.strokeLine(vXs.get(0), vYs.get(0), vXs.get(vXs.size() - 1), vYs.get(vXs.size() - 1));

    }

    @Override
    public void constrain(double left, double top, double width, double height)
    {
        for (int i = 0; i < noVertices; i++)
        {
            var newDx = vDxs.get(i);
            var newDy = vDys.get(i);

            if (vXs.get(i) < left)
            {
//                vXs.set(i, left);
                vDxs.set(i, Math.abs(newDx));
            } else if (vXs.get(i) > left + width)
            {
//                vXs.set(i, left + width);
                vDxs.set(i, -Math.abs(newDx));
            }

            if (vYs.get(i) < top)
            {
//                vYs.set(i, top);
                vDys.set(i, Math.abs(newDy));
            } else if (vYs.get(i) > top + height)
            {
//                vYs.set(i, top + height);
                vDys.set(i, -Math.abs(newDy));
            }
        }
    }

    @Override
    public void move(long elapsedTimeNs)
    {

        for (int i = 0; i < noVertices; i++)
        {
            vXs.set(i, vXs.get(i) + vDxs.get(i) * elapsedTimeNs / BILLION);
            vYs.set(i, vYs.get(i) + vDys.get(i) * elapsedTimeNs / BILLION);
        }
    }

}
