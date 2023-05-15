import java.text.*;
import java.util.*;

public class Number2 {

    static Scanner sc = new Scanner(System.in);

    static class Node {
        char data;
        Node left, right;
        int x;

        Node(char data) {
            this.data = data;
            left = null;
            right = null;
            x = 0;
        }
    }

    // postfix method for expression
    public static String infixToPostfix(String expression) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == ' ') {
                continue;
            } else if (Character.isLetterOrDigit(c)) {
                postfix.append(c + " ");
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop() + " ");
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedenceOrder(c) <= precedenceOrder(stack.peek())) {
                    postfix.append(stack.pop() + " ");
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop() + " ");
        }

        return postfix.toString().trim();
    }

    // prefix method for expression
    public static String infixToPrefix(String infix) {
        String reversed = reverseString(infix);
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (char c : reversed.toCharArray()) {
            if (c == ' ') {
                continue;
            } else if (Character.isLetterOrDigit(c)) {
                result.append(c + " ");
            } else if (c == ')') {
                stack.push(c);
            } else if (c == '(') {
                while (!stack.isEmpty() && stack.peek() != ')') {
                    result.append(stack.pop() + " ");
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedenceOrder(stack.peek()) >= precedenceOrder(c)) {
                    result.append(stack.pop() + " ");
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop() + " ");
        }
        return result.reverse().toString().trim();
    }

    // reverses the string(for prefix)
    public static String reverseString(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        return reverseString(str.substring(1)) + str.charAt(0);
    }

    // for prefix and postfix stack algorithm
    private static int precedenceOrder(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    // checks if the character is an operator
    private static boolean isOperator(char character) {
        return character == '+' || character == '-' || character == '*' || character == '/' || character == '^';
    }

    // method that generates an expression tree from postfix expression
    public static Node constructExpressionTree(String postfix) {
        char[] characters = postfix.toCharArray();
        Stack<Node> stack = new Stack<Node>();

        for (char c : characters) {
            if (c == ' ')
                continue;
            else if (isOperator(c)) {
                Node rightNode = stack.pop();
                Node leftNode = stack.pop();

                Node operatorNode = new Node(c);
                operatorNode.left = leftNode;
                operatorNode.right = rightNode;

                stack.push(operatorNode);
            } else {
                stack.push(new Node(c));
            }
        }

        return stack.pop();
    }

    // display inorder tree
    public static void displayInorderTreeStructure(Node root) {
        if (root == null)
            return;

        displayInorderTreeStructure(root.left);
        System.out.print(root.data + " ");
        displayInorderTreeStructure(root.right);
    }

    // checks if the node is leafed
    public static boolean isLeaf(Node root) {
        return root.left == null && root.right == null;
    }

    // method to update variable
    public static void updateVariable(Node root, HashMap<Character, Integer> map, int userInput) {
        if (root == null)
            return;

        if (isLeaf(root)) {
            map.put(root.data, userInput);
            root.x = map.getOrDefault(root.data, userInput);
        }
    }

    // display the prefix or postfix of the updated expression tree
    public static String displayUpdatePrefixAndPostfix(String expression, HashMap<Character, Integer> map) {
        if (expression == null || expression.isEmpty())
            return null;

        StringBuilder sb = new StringBuilder();
        for (char c : expression.toCharArray()) {
            if (c == ' ') {
                continue;
            } else if (Character.isLetterOrDigit(c)) {
                if (map.get(c) == null) {
                    sb.append("0 ");
                } else {
                    sb.append(map.get(c) + " ");
                }
            } else if (isOperator(c)) {
                sb.append(c + " ");
            }
        }
        return sb.toString();
    }

    // evaluate tree(user optional)
    public static double evaluateTree(String postfix, HashMap<Character, Integer> map) {
        if (postfix.length() <= 1) {
            if (map.get(postfix.charAt(0)) == null) {
                return 0;
            }
            return map.get(postfix.charAt(0));
        }
        Stack<Double> nums = new Stack<>();
        double result = 0.0;
        for (char c : postfix.toCharArray()) {
            if (c == ' ') {
                continue;
            } else if (Character.isLetterOrDigit(c)) {
                if (map.get(c) == null) {
                    nums.push(0.0);
                } else {
                    nums.push(Double.valueOf(map.get(c)));
                }
            } else if (isOperator(c)) {
                try {
                    switch (c) {
                        case '+' -> {
                            double right = nums.pop();
                            double left = nums.pop();
                            result = left + right;
                        }
                        case '-' -> {
                            double right = nums.pop();
                            double left = nums.pop();
                            result = left - right;
                        }
                        case '*' -> {
                            double right = nums.pop();
                            double left = nums.pop();
                            result = left * right;
                        }
                        case '/' -> {
                            double right = nums.pop();
                            double left = nums.pop();
                            result = left / right;
                        }
                        case '^' -> {
                            double right = nums.pop();
                            double left = nums.pop();
                            result = 1;
                            while (right != 0) {
                                result *= left;
                                --right;
                            }
                        }

                    }
                } catch (Exception e) {
                    System.out.println(
                            "Can't perform arithmetic operation, Try changing the leaf order or add a new value.");
                }
                nums.push(result);
            }
        }
        return result;
    }

    // Displays the updated expression tree(infix structure)
    public static String displayUpdatedExpression(String expression, HashMap<Character, Integer> map) {
        StringBuilder sb = new StringBuilder();
        for (char c : expression.toCharArray()) {
            if (c == ' ') {
                continue;
            } else if (Character.isLetterOrDigit(c)) {
                if (map.get(c) == null) {
                    sb.append("0 ");
                } else {
                    sb.append(map.get(c) + " ");
                }
            } else if (isOperator(c)) {
                sb.append(c + " ");
            }
        }
        return sb.toString();
    }

    // checks if the parenthesis is balanced
    public static boolean isBalancedParenthesis(String expression) {
        if (!expression.contains("(") && !expression.contains(")")) {
            System.out.println("No parenthesis found!");
            return false;
        }
        Stack<Character> parenthesisStack = new Stack<Character>();
        for (int i = 0; i < expression.length(); i++) {
            char par = expression.charAt(i);
            if (par == '(') {
                parenthesisStack.push(par);
            } else if (par == ')' && parenthesisStack.empty()) {
                parenthesisStack.push(par);
            } else if (par == ')' && parenthesisStack.peek() == '(' && !parenthesisStack.isEmpty()) {
                parenthesisStack.pop();
            }
        }
        if (parenthesisStack.isEmpty()) {
            return true;
        } else {
            System.out.println("The parentheses in the expression you entered are not balanced.");
            return false;
        }
    }

    // asking the user to input the value of the variables or operand
    public static void inputValue(Node root, HashMap<Character, Integer> map, Scanner sc) {
        if (root == null)
            return;

        if (isLeaf(root)) {
            char choice;
            do {
                System.out.print("Do you want to update the value of " + root.data + "? (Y/N): ");
                choice = Character.toLowerCase(sc.next().charAt(0));

                if (choice != 'y' && choice != 'n') {
                    System.out.println("Please enter 'Y' or 'N' only.");
                }
            } while (choice != 'y' && choice != 'n');

            if (choice == 'y') {
                System.out.print("Enter the new value of " + root.data + ": ");
                int userInput = sc.nextInt();
                updateVariable(root, map, userInput);
            }
        } else {
            inputValue(root.left, map, sc);
            inputValue(root.right, map, sc);
        }
    }

    public static void main(String[] args) {
        HashMap<Character, Integer> map = new HashMap<>();
        DecimalFormat df = new DecimalFormat("###,###.##");

        System.out.print("Enter a fully parenthesized arithmetic expression: ");
        String expression = sc.nextLine();

        if (isBalancedParenthesis(expression)) {
            String postfix = infixToPostfix(expression);
            Node root = constructExpressionTree(postfix);

            System.out.println("\nExpression Tree: ");
            displayInorderTreeStructure(root);

            System.out.println("\nPrefix: " + infixToPrefix(expression));
            System.out.println("Postfix: " + postfix);
            System.out.println("Root: " + root.data);

            System.out.println();
            inputValue(root, map, sc);

            System.out.println("\nUpdated Expression: ");
            String prefix = infixToPrefix(expression);
            System.out.println("Prefix: " + displayUpdatePrefixAndPostfix(prefix, map));
            System.out.println("Postfix: " + displayUpdatePrefixAndPostfix(postfix, map));

            System.out.println("\nEvaluated Expression: ");
            System.out.print(displayUpdatedExpression(expression, map));
            System.out.print(" = ");
            System.out.println(df.format(evaluateTree(postfix, map)));
        } else {
            return;
        }
        sc.close();
    }
}
