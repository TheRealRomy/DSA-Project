import java.util.Stack;

class Node {
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

public class Number2 {
    public static String infixToPostfix(String expression){
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for(int i = 0; i < expression.length(); i++){
            char c = expression.charAt(i);

            if(c == ' '){
                continue;
            }
            else if(Character.isLetter(c)){
                postfix.append(c);
            }else if(c == '('){
                stack.push(c);
            }else if(c == ')'){
                while(!stack.isEmpty() && stack.peek() != '('){
                    postfix.append(stack.pop());
                }
                stack.pop();
            }else{
                while(!stack.isEmpty() && precedenceOrder(c) <= precedenceOrder(stack.peek())){
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while(!stack.isEmpty()){
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    private static int precedenceOrder(char operator){
        switch(operator){
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

    private static boolean isOperator(char character){
        return character == '+' || character == '-' || character == '*' || character == '/' || character == '^';
    }

    public static Node constructExpressionTree(String postfix){
        char[] characters = postfix.toCharArray();
        Stack<Node> stack = new Stack<Node>();

        for(char c : characters){
            if(isOperator(c)){
                Node rightNode = stack.pop();
                Node leftNode = stack.pop();

                Node operatorNode = new Node(c);
                operatorNode.left = leftNode;
                operatorNode.right = rightNode;

                stack.push(operatorNode);
            }else{
                stack.push(new Node(c));
            }
        }

        return stack.pop();
    }

    public static void inorderTraversal(Node root){
        if(root == null) return;

        inorderTraversal(root.left);
        System.out.print(root.data + " ");
        inorderTraversal(root.right);
    }

    public static void main(String[] args) {
        //testo testo delete later - rm
        String expression = "( A + B - C )";
        String postFix = infixToPostfix(expression);
        Node r = constructExpressionTree(postFix);
        System.out.print("Expression Tree: ");
        inorderTraversal(r);
        System.out.println();
        System.out.println("Root of the Expression Tree: " + r.data);
        System.out.println(r.right.data);
        System.out.println(r.left.data);
        System.out.println(r.left.left.data);

        /* di pa ito tapos auuughhhhh antok nako
         *                -
         *              /   \
         *             +     C
         *            / \
         *           A   B        
         */
    }
}
