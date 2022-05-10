package f.f4;

import java.util.Scanner;

public class PP7 {
    public static void main(String[] args) {
        InfixEvaluator evaluator = new InfixEvaluator();
        String line;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a infix expression to evaluate");
            line = in.nextLine();
            if (!line.equals("")) {
                try {
                    int result = evaluator.eval(line);
                    System.out.println("Value is " + result);
                } catch (PostfixEvaluator.SyntaxErrorException | InfixToPostfixParens.SyntaxErrorException ex) {
                    System.out.println("Syntax error " + ex.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                break;
            }
        }
    }
}