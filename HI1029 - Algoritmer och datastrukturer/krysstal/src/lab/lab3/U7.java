package lab.lab3;

public class U7 {
    public static void main(String[] args) {
        System.out.println("Lab uppgift 7 - Puzzle solver");
        PuzzleSolver p = new PuzzleSolver(0, 0, 8);
        p.solve();
        System.out.println("Found " + p.getSolutions() + " solutions.");
    }
}
