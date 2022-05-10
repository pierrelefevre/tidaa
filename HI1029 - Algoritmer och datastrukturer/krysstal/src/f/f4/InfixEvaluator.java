package f.f4;


import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class InfixEvaluator {
    private static final String OPERATORS = "+-*/()";
    private static final int[] PRECEDENCE = {1, 1, 2, 2, -1, -1};

    private Stack<Character> operatorStack;
    private Stack<Integer> operandStack;

    public InfixEvaluator() {
        this.operandStack = new Stack<>();
        this.operatorStack = new Stack<>();
    }

    private boolean isOperator(char ch) {
        return OPERATORS.indexOf(ch) != -1;
    }

    private int precedence(char op) {
        return PRECEDENCE[OPERATORS.indexOf(op)];
    }

    public int eval(String infix) throws Exception {
        //Reset the stacks
        operatorStack = new Stack<>();
        operandStack = new Stack<>();

        //Calculate the expression
        try {
            String nextToken;
            Scanner scan = new Scanner(infix);
            while ((nextToken = scan.findInLine("[\\p{L}\\p{N}]+|[-+/\\*()]")) != null) {
                char firstChar = nextToken.charAt(0);

                //Is it an operand?
                if (Character.isJavaIdentifierStart(firstChar) || Character.isDigit(firstChar)) {
                    operandStack.push(Integer.parseInt(nextToken));
                }

                //Is it an operator?
                else if (isOperator(firstChar)) {
                    processOperator(firstChar);
                } else {
                    throw new Exception("Unexpected Character Encountered" + firstChar);
                }
            }

            while (!operatorStack.empty()) {
                operandStack.push(evalOp(operatorStack.pop()));
            }

            //Check if anything is left on the stack
            int result = operandStack.pop();
            if (!operandStack.empty() || !operatorStack.empty()) {

                throw new Exception("Stack not empty...");
            }

            //Calculation finished. Display results
            return result;

        } catch (EmptyStackException ex) {
            throw new Exception("Syntax Error: The stack is empty");
        }
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

                    //here is the calculation

                    if (topOp == '(') {
                        break;
                    }

                    operandStack.push(evalOp(topOp));

                    if (!operatorStack.empty()) {
                        topOp = operatorStack.peek();
                    }
                }

                if (op != ')')
                    operatorStack.push(op);
            }
        }
    }
}
