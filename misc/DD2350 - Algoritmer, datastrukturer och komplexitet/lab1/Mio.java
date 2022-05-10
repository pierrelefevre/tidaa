import java.io.*;

/** Enkel in- och utmatning, Henrik Eriksson, 1997-02-03 */
public class Mio {
  static BufferedInputStream keyboard = (BufferedInputStream) System.in;

  static boolean IsWhitespace(char c) {
    return c == ' ' || c == '\t' || c == '\n' || c == '\r';
  }

  /** �ppna filen f�r l�sning */
  public static BufferedInputStream OpenRead(String s) {
    try {
      return new BufferedInputStream(new FileInputStream(new File(s)));
    } catch (IOException e) {
      System.err.println("Kan inte �ppna " + s);
    }
    return null;
  }

  /** L�s en rad fr�n filen, glufsa radbytet, returnera raden */
  public static String GetLine(BufferedInputStream in) {
    StringBuffer word = new StringBuffer();
    char tkn;
    if (EOF(in))
      return "";
    while ((tkn = GetChar(in)) != '\n')
      word.append(tkn);
    return word.toString();
  }

  /** L�s en rad fr�n tangenterna, glufsa radbytet, returnera raden */
  public static String GetLine() {
    return GetLine(keyboard);
  }

  /** L�s ett ord avgr�nsat av blanka fr�n filen och returnera det */
  public static String GetWord(BufferedInputStream in) {
    StringBuffer word = new StringBuffer();
    SkipBlanks(in);
    if (EOF(in))
      return "";
    while (!IsWhitespace(NextChar(in)))
      word.append(GetChar(in));
    return word.toString();
  }

  /** L�s ett ord avgr�nsat av blanka fr�n tangenterna och returnera det */
  public static String GetWord() {
    return GetWord(keyboard);
  }

  /** L�s ett heltal avgr�nsat av blanka fr�n filen och returnera det */
  public static int GetInt(BufferedInputStream in) {
    String word = GetWord(in);
    try {
      return Integer.parseInt(word);
    } catch (NumberFormatException e) {
      System.err.println("Not an integer:" + word);
    }
    return 0;
  }

  /** L�s ett heltal avgr�nsat av blanka fr�n tangenterna och returnera det */
  public static int GetInt() {
    return GetInt(keyboard);
  }

  /** L�s ett doubletal avgr�nsat av blanka fr�n filen och returnera det */
  public static double GetReal(BufferedInputStream in) {
    String word = GetWord(in);
    try {
      Double d = Double.valueOf(word);
      return d.doubleValue();
    } catch (NumberFormatException e) {
      System.err.println("Not a number:" + word);
    }
    return 0;
  }

  /** L�s ett doubletal avgr�nsat av blanka fr�n tangenterna och returnera det */
  public static double GetReal() {
    return GetReal(keyboard);
  }

  /** L�s ett tecken fr�n filen och returnera det */
  public static char GetChar(BufferedInputStream in) {
    char tkn = '\000';
    try {
      tkn = (char) in.read();
    } catch (IOException e) {
      System.err.println("Fel i GetChar");
    }
    return tkn;
  }

  /** L�s ett tecken fr�n tangenterna och returnera det */
  public static char GetChar() {
    return GetChar(keyboard);
  }

  /** Tjuvtitta p� n�sta tecken i filen och returnera det */
  public static char NextChar(BufferedInputStream in) {
    char tkn = '\000';
    in.mark(1);
    try {
      tkn = (char) in.read();
      in.reset();
    } catch (IOException e) {
      System.err.println("Fel i NextChar\n");
    }
    return tkn;
  }

  /** Tjuvtitta p� n�sta tecken fr�n tangenterna och returnera det */
  public static char NextChar() {
    return NextChar(keyboard);
  }

  /** Glufsa eventuella blanka p� tur att l�sas i filen */
  public static void SkipBlanks(BufferedInputStream in) {
    while (IsWhitespace(NextChar(in)))
      GetChar(in);
  }

  /** Glufsa eventuella blanka p� tur att l�sas fr�n tangenterna */
  public static void SkipBlanks() {
    SkipBlanks(keyboard);
  }

  /** Returnera true om filen �r slutl�st */
  public static boolean EOF(BufferedInputStream in) {
    int n = 0;
    in.mark(1);
    try {
      n = in.read();
      in.reset();
    } catch (IOException e) {
      System.err.println("Fel i EOF\n");
    }
    return (n == -1);
  }

  /** Returnera true om ctrl-D st�r i tur fr�n tangenterna */
  public static boolean EOF() {
    return EOF(keyboard);
  }
}
