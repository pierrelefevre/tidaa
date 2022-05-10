/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tenta.skyline;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class MyCanvas extends Canvas {

    private static final int SIZE = 5;
    private final GraphicsContext context;

    public MyCanvas(double width, double height) {
        super(width, height);

        context = this.getGraphicsContext2D();
        drawBg();
    }

    public void drawBuildings(ArrayList<Skyline.Building> b) {
        int mod = 20;
        //Draw black background
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, this.getWidth(), this.getHeight());

        context.setLineWidth(2);
        int color = 0;
        for (int i = 0; i < b.size(); i++) {
            setColor(color++ % 10);
            double x1 = b.get(i).x1 * mod - SIZE / 2;
            double y = b.get(i).y * mod - SIZE / 2;

            double x2 = b.get(i).x2 * mod - SIZE / 2;

            context.strokeLine(x1, this.getHeight(), x1, this.getHeight() - y);
            context.strokeLine(x1, this.getHeight() - y, x2, this.getHeight() - y);
            context.strokeLine(x2, this.getHeight() - y, x2, this.getHeight());
        }
    }

    public void drawPoints(List<Point> b) {
        int mod = 20;

        context.setLineWidth(5);
        context.setStroke(Color.WHITE);

        //First line
        context.strokeLine(b.get(0).x * mod, this.getHeight(), b.get(0).x * mod, this.getHeight() - b.get(0).y * mod);

        //Other lines
        for (int i = 0; i < b.size() - 1; i++) {
            double x1 = b.get(i).x * mod;
            double y1 = b.get(i).y * mod;

            double x2 = b.get(i + 1).x * mod;
            double y2 = b.get(i + 1).y * mod;

            context.strokeLine(x1, this.getHeight() - y1, x2, this.getHeight() - y1);
            context.strokeLine(x2, this.getHeight() - y1, x2, this.getHeight() - y2);
        }
    }

    private void setColor(int r) {
        switch (r) {
            case 0 -> context.setStroke(Color.RED);
            case 1 -> context.setStroke(Color.BEIGE);
            case 2 -> context.setStroke(Color.GREEN);
            case 3 -> context.setStroke(Color.YELLOW);
            case 4 -> context.setStroke(Color.CYAN);
            case 5 -> context.setStroke(Color.PINK);
            case 6 -> context.setStroke(Color.PALEGOLDENROD);
            case 7 -> context.setStroke(Color.DARKGREEN);
            case 8 -> context.setStroke(Color.BLUE);
            case 9 -> context.setStroke(Color.PURPLE);
            case 10 -> context.setStroke(Color.VIOLET);
            default -> context.setStroke(Color.WHITE);
        }
    }

    public void drawBg() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void draw() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, this.getWidth(), this.getHeight());
        context.setFill(Color.CYAN);
        Point[] positions = null;
        for (Point p : positions) {
            context.fillOval(p.x - SIZE / 2, p.y - SIZE / 2, SIZE, SIZE);
        }
        context.setStroke(Color.RED);
        context.setLineWidth(2);
    }

    public void drawRoute(Point[] route) {
        draw();
        for (int i = 0; i < route.length - 1; i++) {
            context.strokeLine(route[i].x, route[i].y, route[i + 1].x, route[i + 1].y);
        }
    }
}
