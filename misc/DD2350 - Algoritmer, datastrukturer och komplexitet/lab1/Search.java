import java.io.*;
import java.util.*;

class Search {
    static int A[];

    static RandomAccessFile tokenized;
    static RandomAccessFile korpus;
    static RandomAccessFile presenceCount;

    public static void main(String[] args) {
        try {
            long startTime = System.nanoTime();

            args[0] = args[0].toLowerCase();

            // Initiate
            loadArray();
            tokenized = new RandomAccessFile("rawindex.txt", "r");
            korpus = new RandomAccessFile("korpus.txt", "r");
            presenceCount = new RandomAccessFile("presenceCount.txt", "r");

            // Get occurences for this word
            long presence = getPresenceCount(args[0]);
            System.out.println("\nDet finns " + presence + " förekomster av ordet");

            // If there are more than 25 occurrences, stop the timer and ask the user
            if (presence > 25) {
                double totalTime = (System.nanoTime() - startTime) / 1000000000.00;
                System.out.println("Time taken: " + String.format("%.3f", totalTime) + " seconds");
                System.out.print("Det finns fler än 25 förekomster av ordet. Skriv ut (Enter) - Avsluta (q)");
                Scanner input = new Scanner(System.in);
                String answer = input.nextLine();
                if (answer.compareTo("") != 0) {
                    input.close();
                    return;
                }
                input.close();
            }

            // Generate prefix and insert in I and J variables for binary search
            int wprefix = getWprefix(args[0]);
            long i = A[wprefix];

            // Skip wprefix +1 in case of lookup with "ööö"
            if (wprefix < 26999) {
                long j = A[wprefix + 1];

                while (j - i > 1000) {
                    // Binary search, split in two each loop
                    long mRaw = (i + j) / 2;

                    // Adjust to line start from pointer and extract word
                    long m = adjustToLineStart(mRaw, tokenized);
                    String s = getLineFromTokenized(m).line.split("\\s+")[0];

                    // Continue with lower bounds, upper bounds or exit if the correct word has been
                    // found
                    if (s.compareTo(args[0]) < 0) {
                        i = mRaw;
                    } else if (s.compareTo(args[0]) == 0) {
                        // i = mRaw;
                        break;
                    } else {
                        j = mRaw;
                    }
                }
            }

            i = adjustToLineStart(i, tokenized);

            // Get all correct words, or linear search if there are less than 1000 words in
            // that lazy hashing index
            while (true) {
                // Get current line and pointer for the next line
                Pair p = getLineFromTokenized(i);
                String line = p.line;
                i = p.nextPointer;

                String splitted[] = line.split("\\s+");
                String s = splitted[0];

                // Check if word is equal
                if (s.compareTo(args[0]) == 0) {
                    System.out.println(getFromKorpus(Integer.parseInt(splitted[1]), args[0]));
                }

                // Exit when next word has been found
                if (s.compareTo(args[0]) > 0) {
                    break;
                }
            }

            // Stop the time (only for <=25 due to this happening earlier for >25)
            if (presence <= 25) {
                double totalTime = (System.nanoTime() - startTime) / 1000000000.00;
                System.out.println("Time taken: " + String.format("%.3f", totalTime) + " seconds");
            }

            // Close files
            tokenized.close();
            korpus.close();
            presenceCount.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Load in lazy hashing index array (binary format)
    public static void loadArray() throws IOException {
        DataInputStream is = new DataInputStream(new FileInputStream("array.txt"));
        A = new int[27000];
        for (int i = 0; i < 27000; ++i) {
            A[i] = is.readInt();
        }
        is.close();
    }

    // Get a line from korpus, provided a pointer
    public static String getFromKorpus(int pointer, String word) throws IOException {
        byte[] bytes = new byte[60 + word.length()];
        if (pointer > 30) {
            korpus.seek(pointer - 30);
        } else {
            korpus.seek(0);
        }
        korpus.read(bytes);
        String s = new String(bytes, "ISO-8859-1").replaceAll("[\\t\\n\\r]+", " ");
        return s;
    }

    // Get a line from the index file
    public static Pair getLineFromTokenized(long pointer) throws IOException {
        tokenized.seek(pointer);
        String line = tokenized.readLine();
        return new Pair(line, tokenized.getFilePointer());
    }

    // Check how many times a word exists in the text file
    public static long getPresenceCount(String word) throws IOException {
        presenceCount.seek(0);
        long i = 0;
        long j = presenceCount.length();

        // Binary search
        while (true) {
            long mRaw = (i + j) / 2;
            long m = adjustToLineStart(mRaw, presenceCount);
            presenceCount.seek(m);
            String line[] = presenceCount.readLine().split("\\s+");
            String s = line[0];

            if (s.compareTo(word) < 0) {
                i = mRaw;
            } else if (s.compareTo(word) == 0) {
                return Long.parseLong(line[1]);
            } else {
                j = mRaw;
            }

            // Exit if the pointers meet, this means that there was no occurrence.
            if ((j - i) <= 1) {
                return 0;
            }
        }
    }

    // Put the pointer at the beginning of a line in the index.
    public static long adjustToLineStart(long pointer, RandomAccessFile f) throws IOException {
        while (true) {
            // Read a character, if its not a newline, go back one character
            f.seek(pointer);
            char c = (char) f.read();
            int charInt = (int) c;
            // Check if its a newline or not.
            if (charInt == 10) {
                return pointer + 1;
            }

            pointer--;
        }
    }

    // Key-value pair containing a word and a pointer to the next line in file
    static class Pair {
        String line;
        long nextPointer;

        public Pair(String line, long nextPointer) {
            this.line = line;
            this.nextPointer = nextPointer;
        }
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