
/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {

  List<String> closestWords = null;

  int closestDistance = -1;
  int[][] M;

  String lastWord = "";

  int dpDistance(String w1, String w2, int w1len, int w2len) {
    int sameLetter = sameLetterCount(lastWord, w2);

    // Calculate all positions
    for (int row = 1; row <= w1len; row++) {
      for (int col = sameLetter + 1; col <= w2len; col++) {

        int replace = M[row - 1][col - 1] + (w1.charAt(row - 1) == w2.charAt(col - 1) ? 0 : 1);
        int add = M[row - 1][col] + 1;
        int delete = M[row][col - 1] + 1;

        M[row][col] = Math.min(replace, Math.min(add, delete));
      }
    }

    lastWord = w2;
    return M[w1len][w2len];
  }

  int sameLetterCount(String w1, String w2) {
    int count = 0;
    for (int i = 0; i < Math.min(w1.length(), w2.length()); i++) {
      if (w1.charAt(i) == w2.charAt(i)) {
        count++;
      } else {
        return count;
      }
    }
    return count;
  }

  public ClosestWords(String w, List<String> wordList, int longestString) {
    closestWords = new LinkedList<String>();

    // Init M matrix
    M = new int[longestString + 1][longestString + 1];
    for (int i = 0; i <= longestString; i++) {
      M[i][0] = i;
      M[0][i] = i;
    }

    int wLen = w.length();
    for (String s : wordList) {
      int dist = dpDistance(w, s, wLen, s.length());
      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords.clear();
        closestWords.add(s);
      } else if (dist == closestDistance)
        closestWords.add(s);
    }
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }
}
