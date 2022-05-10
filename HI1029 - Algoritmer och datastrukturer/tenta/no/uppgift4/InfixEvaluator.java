package uppgift4;

import java.util.Stack;
import java.util.EmptyStackException;

public class InfixEvaluator {

    static Stack<Integer> operands;
    static Stack<Character> operators;

    public InfixEvaluator() {
        operands = new Stack<>();
        operators = new Stack<>();
    }

    public static class SyntaxErrorException extends Exception {
        SyntaxErrorException(String message) {
            super(message);
        }
    }

    private static final String OPERATORS = "+-*/";

    private boolean isOperator(char ch) {
        return OPERATORS.indexOf(ch) != -1;
    }

    private void calculate(char operator) {
        int right = operands.pop();
        int left = operands.pop();

        switch (operator) {
            case '+' -> operands.push(left + right);
            case '-' -> operands.push(left - right);
            case '*' -> operands.push(left * right);
            case '/' -> operands.push(left / right);
        }
    }

    public int eval(String expression) throws SyntaxErrorException {

        String[] tokens = expression.split(" +");

        try {
            for (String nextToken : tokens) {
                if (Character.isDigit(nextToken.charAt(0))) {
                    //If a number is found
                    operands.push(Integer.parseInt(nextToken));
                } else if (isOperator(nextToken.charAt(0))) {
                    //If an operator is found
                    char operator = nextToken.charAt(0);

                    while (!operators.isEmpty() && (OPERATORS.indexOf(operator) <= OPERATORS.indexOf(operators.peek()))) {
                        calculate(operators.pop());
                    }

                    operators.push(operator);

                } else {
                    throw new SyntaxErrorException("Invalid expression encountered");
                }
            }

        } catch (EmptyStackException ex) {
            throw new SyntaxErrorException("Syntax Error: The stack is empty");
        }

        while (!operators.empty()) {
            calculate(operators.pop());
        }

        if (operands.size() > 1)
            throw new SyntaxErrorException("There were operands left");

        return operands.pop();
    }
}