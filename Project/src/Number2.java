import java.util.*;

public class Number2 {

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

    // checks if the node is leaf
    public static boolean isLeaf(Node root) {
        return root.left == null && root.right == null;
    }

    // method to update variable
    public static void updateVariable(Node root, HashMap<Character, Integer> map, int userInput) {
        if (root == null)
            return;

        if (isLeaf(root)) {
            map.put(root.data, userInput);
            root.x = map.getOrDefault(root.data, userInput); // A , 23 --> map.get('A') = 23
        }
    }

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
    public static int evaluateTree(String postfix, HashMap<Character, Integer> map) {
        Stack<Integer> nums = new Stack<>();
        int result = 0;
        for (char c : postfix.toCharArray()) {
            try {
                if (c == ' ') {
                    continue;
                } else if (Character.isLetterOrDigit(c)) {
                    nums.push(map.get(c));
                } else if (isOperator(c)) {

                    while (!nums.isEmpty()) {
                        switch (c) {
                            case '+':
                                result += nums.pop();
                                break;
                            case '-':
                                result -= nums.pop();
                                break;
                            case '*':
                                result *= nums.pop();
                                break;
                            case '/':
                                result /= nums.pop();
                                break;
                        }
                    }

                }
            } catch (Exception e) {
                System.out.println("Can't Perform Arithmetic Operation, Try changing the leaf order");
            }

        }

        return result;
    }

    // display the updated expression tree(infix structure)
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

    public static void main(String[] args) {
        HashMap<Character, Integer> map = new HashMap<>();
        String expression = "(A + B - C)";
        String postfix = infixToPostfix(expression);
        Node root = constructExpressionTree(postfix);
        System.out.print("Expression Tree: ");
        displayInorderTreeStructure(root);
        System.out.println();
        updateVariable(root.left.left, map, 23); // A
        updateVariable(root.left.right, map, 12);// B
        updateVariable(root.right, map, 69); // C

        System.out.print(displayUpdatedExpression(expression, map));
        System.out.print(" = ");
        System.out.println(evaluateTree(postfix, map));

    }
}
