package f.f3;

import java.util.Stack;
import java.util.EmptyStackException;

public class PostfixEvaluator {
    private static final String OPERATORS = "+-*/";
    private Stack<Integer> operandStack;

    public PostfixEvaluator() {
        this.operandStack = new Stack<>();
    }

    private int evalOp(char op) {
        int rightSide = operandStack.pop();
        int leftSide = operandStack.pop();

        if (op == '+')
            return leftSide + rightSide;
        if (op == '-')
            return leftSide - rightSide;
        if (op == '*')
            return leftSide * rightSide;
        if (op == '/')
            return leftSide / rightSide;
        return 0;
    }

    private boolean isOperator(char ch) {
        return OPERATORS.indexOf(ch) != -1;
    }

    public int eval(String expression) throws SyntaxErrorException {
        String[] tokens = expression.split(" +");
        try {
            for (String nextToken : tokens) {
                if (Character.isDigit(nextToken.charAt(0))) {
                    operandStack.push(Integer.parseInt(nextToken));
                } else if (isOperator(nextToken.charAt(0))) {
                    operandStack.push(evalOp(nextToken.charAt(0)));
                } else {
                    throw new SyntaxErrorException("Invalid character encountered");
                }
            }
            if (operandStack.size() > 0)
                return operandStack.pop();
        } catch (EmptyStackException ex) {
            throw new SyntaxErrorException("Syntax Error: The stack is empty");
        }
        return 0;
    }

    public static class SyntaxErrorException extends Exception {
        SyntaxErrorException(String message) {
            super(message);
        }
    }
}