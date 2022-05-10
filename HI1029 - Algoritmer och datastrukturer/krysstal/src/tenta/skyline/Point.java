package tenta.skyline;

public class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[x: " + x + ", y: " + y + "]";
    }

    public int distance(Point p) {
        return (int) Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
    }
}
