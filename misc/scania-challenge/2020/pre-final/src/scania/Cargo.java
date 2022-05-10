package scania;

public class Cargo implements Comparable<Cargo> {
    private int value;
    private int weight;
    private double ratio;

    public Cargo(int value, int weight) {
        this.value = value;
        this.weight = weight;
        this.ratio = (double) value / (double) weight;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    public double getRatio() {
        return ratio;
    }

    @Override
    public String toString() {
        return "\n" + value + ", " + weight;
    }

    @Override
    public int compareTo(Cargo o) {
        if(o.ratio > ratio)
            return 1;
        if(ratio > o.ratio)
            return -1;
        return 0;
    }
}
