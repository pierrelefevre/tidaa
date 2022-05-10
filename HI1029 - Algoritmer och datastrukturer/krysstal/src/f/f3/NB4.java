package f.f3;

import java.util.Stack;

public class NB4 {
    public static void main(String[] args) {
        String test1 = "[3+(2*3)+{22}]";
        System.out.println("Should be true: " + isBalanced(test1));

        String test2 = "[3+(2*3)+{22]]";
        System.out.println("Should be false: " + isBalanced(test2));
    }

    public static boolean isBalanced(String check) {
        Stack<Character> chars = new Stack<>();
        boolean balanced = true;
        int index = 0;
        while (balanced && index < check.length()) {
            char current = check.charAt(index);
            if (isOpening(current)) {
                chars.push(current);
            } else if (isClosing(current)) {
                char last = chars.pop();
                if (!isSameType(last, current)) {
                    System.out.println("Invalid closing parenthesis: " + current);
                    balanced = false;
                }
            }
            index++;
        }
        return balanced && chars.size() == 0;
    }

    private static boolean isOpening(char check) {
        return check == '(' || check == '{' || check == '[';
    }

    private static boolean isClosing(char check) {
        return check == ')' || check == '}' || check == ']';
    }

    private static boolean isSameType(char opening, char closing) {
        if (opening == '(' && closing == ')')
            return true;
        if (opening == '{' && closing == '}')
            return true;
        if (opening == '[' && closing == ']')
            return true;
        return false;
    }
}
