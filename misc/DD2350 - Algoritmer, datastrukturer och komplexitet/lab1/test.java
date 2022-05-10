
public class test {

    public static void main(String[] args) {
        try {
            // RandomAccessFile tokenized = new RandomAccessFile("rawindex.txt", "r");
            // tokenized.seek(0);
            // for (int i = 0; i < 100; i++) {
            // char c = (char) tokenized.read();
            // System.out.println(c + ": " + (int) c);
            // }
            // tokenized.close();

            System.out.println("lasagneer".compareTo("lasagne"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
