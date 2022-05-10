package f.f2;

public class NB3 {

    public static void main(String[] args) {
        //End with last
        Node[] nodes = new Node[4];
        nodes[3] = new Node("stäppen", null);
        nodes[2] = new Node("på", nodes[3]);
        nodes[1] = new Node("löper", nodes[2]);
        nodes[0] = new Node("Gilgamesh", nodes[1]);

        for (Node current = nodes[0]; current != null; current = current.next) {
            System.out.println(current.data);
        }

        nodes[1].next = nodes[3];

        for (Node current = nodes[0]; current != null; current = current.next) {
            System.out.println(current.data);
        }
    }

}
