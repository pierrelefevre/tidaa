package f.f4;

import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class InfixToPostfixParens {

    /**
     * The operators
     */
    private static final String OPERATORS = "+-*/()";

    //Data Fields
    /**
     * The precedence of the operators, matches order of OPERATORS
     */
    private static final int[] PRECEDENCE = {1, 1, 2, 2, -1, -1};
    /**
     * The operator stack
     */
    private Stack<Character> operatorStack;
    /**
     * The postfix string
     */
    private StringBuilder postfix;

    /**
     * Convert a string from infix to postfix.
     *
     * @param infix The infix expression
     * @throws SyntaxErrorException
     */
    public String convert(String infix) throws SyntaxErrorException {
        operatorStack = new Stack<>();
        postfix = new StringBuilder();
        try {
            String nextToken;
            Scanner scan = new Scanner(infix);
            while ((nextToken = scan.findInLine("[\\p{L}\\p{N}]+|[-+/\\*()]")) != null) {
                char firstChar = nextToken.charAt(0);
                //Is it an operand?
                if (Character.isJavaIdentifierStart(firstChar) || Character.isDigit(firstChar)) {
                    postfix.append(nextToken);
                    postfix.append(' ');
                }//Is it an operator?
                else if (isOperator(firstChar)) {
                    processOperator(firstChar);
                } else {
                    throw new SyntaxErrorException("Unexpected Character Encountered" + firstChar);
                }
            } //End loop
            //Pop any remaining operatros
            //and append them to postfix
            while (!operatorStack.empty()) {
                char op = operatorStack.pop();
                //Any ( on the stack is not matched.
                if (op == '(')
                    throw new SyntaxErrorException("Unmatched opening parenthesis");
                postfix.append(op);
                postfix.append(' ');
            }
            return postfix.toString();
        } catch (EmptyStackException ex) {
            throw new SyntaxErrorException("Syntax Error: The stack is empty");
        }
    }

    private void processOperator(char op) {
        if (operatorStack.empty() || op == '(') {
            operatorStack.push(op);
        } else {
            char topOp = operatorStack.peek();
            if (precedence(op) > precedence(topOp)) {
                operatorStack.push(op);
            } else {
                while (!operatorStack.empty() && precedence(op) <= precedence(topOp)) {
                    operatorStack.pop();
                    if (topOp == '(') {
                        break;
                    }
                    postfix.append(topOp);
                    postfix.append(' ');
                    if (!operatorStack.empty()) {
                        topOp = operatorStack.peek();
                    }
                }

                if (op != ')')
                    operatorStack.push(op);
            }
        }
    }

    private boolean isOperator(char ch) {
        return OPERATORS.indexOf(ch) != -1;
    }

    private int precedence(char op) {
        return PRECEDENCE[OPERATORS.indexOf(op)];
    }

    public class SyntaxErrorException extends Exception {
        public SyntaxErrorException(String msg) {
            super(msg);
        }
    }
}
