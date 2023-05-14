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

    //postfix method for expression
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

    //prefix method for expression
    public static String infixToPrefix(String infix) {
        String reversed = reverseString(infix);
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (char c : reversed.toCharArray()) {
            if(c == ' '){
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
    
    //reverses the string(for prefix)
    public static String reverseString(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        return reverseString(str.substring(1)) + str.charAt(0);
    }

    //for prefix and postfix stack algorithm
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

    //checks if the character is an operator
    private static boolean isOperator(char character) {
        return character == '+' || character == '-' || character == '*' || character == '/' || character == '^';
    }

    //method that generates an expression tree from postfix expression
    public static Node constructExpressionTree(String postfix) {
        char[] characters = postfix.toCharArray();
        Stack<Node> stack = new Stack<Node>();

        for (char c : characters) {
            if(c == ' ') continue;
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

    //display inorder tree
    public static void displayInorderTree(Node root) {
        if (root == null)
            return;

        displayInorderTree(root.left);
        System.out.print(root.data + " ");
        displayInorderTree(root.right);
    }

    //checks if the node is leaf
    public static boolean isLeaf(Node root) {
        return root.left == null && root.right == null;
    }

    //method to update variable
    public static void updateVariable(Node root, HashMap<Character, Integer> map, int userInput){
        if(root == null) return;

        if(isLeaf(root)){
            map.put(root.data, userInput);
            root.x = map.getOrDefault(root.data, userInput); //A , 23 --> map.get('A') = 23
        }
    }
    
    //display prefix and postfix from the updated expression tree
    public static String displayUpdatePrefixAndPostfix(String expression, HashMap<Character, Integer> map){
        if(expression == null || expression.isEmpty()) return null;

        StringBuilder sb = new StringBuilder();

        for(char c : expression.toCharArray()){
            if(c == ' '){
                continue;
            }
            else if(Character.isLetterOrDigit(c)){
                if(map.get(c) == null){
                    sb.append("0 ");
                }else{
                    sb.append(map.get(c) + " ");
                }
            }else if(isOperator(c)){
                sb.append(c + " ");
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        HashMap<Character, Integer> map = new HashMap<>();
        String expresion = "(A + B - C)";
        String postfixOfExpression = infixToPostfix(expresion);
        Node r = constructExpressionTree(postfixOfExpression);
        System.out.print("Expression Tree: ");
        displayInorderTree(r);
        System.out.println();
        System.out.println("root: "+ r.data); // -
        System.out.println("root.left: " + r.left.data); // + 
        System.out.println("root.left.left:  " + r.left.left.data); // A
        System.out.println("root.left.right: "+ r.left.right.data); // B
        System.out.println("root.right: " + r.right.data); // C

        System.out.println("Before update: "+ r.left.left.x);
        updateVariable(r.left.left, map, 23); 
        System.out.println("After update: " + + r.left.left.x);

        System.out.println("prefix : " + infixToPrefix(expresion));
        System.out.println("postfix : " +infixToPostfix(expresion));

        
        String prefix = infixToPrefix(expresion);
        System.out.println();//new line in console
        updateVariable(r.left.right, map, 12);
        updateVariable(r.right, map, 69);

        System.out.println(displayUpdatePrefixAndPostfix(prefix, map));
        System.out.println(displayUpdatePrefixAndPostfix(postfixOfExpression, map));

        
    }
}
