import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.Arrays;

public class Create {
    static int A[] = new int[27000];

    // Key-value pair containing a word and its presence count
    static class Pair {
        String word;
        long count;

        public Pair(String word, long count) {
            this.word = word;
            this.count = count;
        }
    }

    public static void main(String[] args) {
        try {
            long startTime = System.nanoTime();

            // Init array
            Arrays.fill(A, -1);

            // Open file streams
            RandomAccessFile raf = new RandomAccessFile("rawindex.txt", "r");
            FileInputStream fis = new FileInputStream(raf.getFD());
            BufferedInputStream bis = new BufferedInputStream(fis);
            BufferedReader r = new BufferedReader(new InputStreamReader(bis, StandardCharsets.ISO_8859_1));
            DataOutputStream presenceFile = new DataOutputStream(new FileOutputStream("presenceCount.txt"));

            // Prepare loop variables, start at 0 in file.
            raf.seek(0);
            int index = 0;
            String lastWord = "";
            int lastWordCount = 0;

            // Read line by line in sorted file
            // - add its index to the Array if it is the first word with that wprefix
            // - add/increment its count in the presence array
            while (true) {
                String line = r.readLine();

                // If we reach end of file
                if (line == null)
                    break;

                String[] splitted = line.split("\\s+");
                String word = splitted[0];
                var prefix = getWprefix(word);

                // Add in index array
                if (A[prefix] == -1)
                    A[prefix] = index;

                // Counting file pointer, keeps track of position in file. +1 for '\n'
                index += line.length() + 1;

                // Count presence
                if (lastWord.compareTo(word) != 0) {
                    // Save last word and reset
                    String s = lastWord + " " + Long.toString(lastWordCount + 1) + "\n";
                    presenceFile.write(s.getBytes("ISO-8859-1"));
                    lastWordCount = 0;
                    lastWord = word;
                } else {
                    lastWordCount++;
                }

            }

            // Close file
            raf.close();
            presenceFile.close();

            // Write results to file
            writeArrayToFile();
            // writeListToFile();

            // End time counter and print
            long totalTime = (System.nanoTime() - startTime) / 1000000000;
            System.out.println("Time taken: " + totalTime + " seconds");

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // Write array in binary format to file "array.txt"
    private static void writeArrayToFile() throws IOException {
        DataOutputStream os = new DataOutputStream(new FileOutputStream("array.txt"));

        for (int i = 0; i < A.length; ++i) {
            os.writeInt(A[i]);
        }

        os.close();
    }

    // Calculates the index for the first three letters of a word
    // index is used in prefix array (lazy hashing)
    private static int getWprefix(String word) {
        char chars[] = new char[3];

        // Break up the word
        switch (word.length()) {
            case 1:
                chars[0] = word.charAt(0);
                chars[1] = ' ';
                chars[2] = ' ';
                break;

            case 2:
                chars[0] = word.charAt(0);
                chars[1] = word.charAt(1);
                chars[2] = ' ';
                break;

            default:
                chars[0] = word.charAt(0);
                chars[1] = word.charAt(1);

                chars[2] = word.charAt(2);
        }

        int cNums[] = new int[3];

        // Calculate the relative character number, ex. ' ' -> 0, 'a' -> 1, 'ö' -> 29
        for (int i = 0; i < 3; i++) {
            if (chars[i] == ' ') {
                cNums[i] = 0;
            } else if (chars[i] == 229) {
                // å
                cNums[i] = 28;
            } else if (chars[i] == 228) {
                // ä
                cNums[i] = 27;
            } else if (chars[i] == 246) {
                // ö
                cNums[i] = 29;
            } else {
                cNums[i] = (int) chars[i] - 96;
            }
        }

        // Multiply to create the index, with 30^2, 30^1, 30^0 position system.
        int index = cNums[0] * 900 + cNums[1] * 30 + cNums[2];
        return index;
    }
}
