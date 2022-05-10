package f.f6;

public class NB18 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Maze m = new Maze();
        m.print();
        System.out.println(m.solve() ? "Solution found" : "Invalid maze");
        System.out.println();
        m.print();


    }


}
