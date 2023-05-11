import java.util.InputMismatchException;
import java.util.Scanner;

public class Number1{
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        int[] optionsNumbers = {1, 2, 3, 4};
        String[] options = {"Leap || !Leap", "Height A > Height B == Height C", "Series", "Shine bright like a diamond"};

        String name = stringPrompt("Enter your name: ");
        System.out.println("Hello, " + name + "!\n");

        System.out.println("Tell me what you want to do, here are your options: ");
        for (int i = 0; i < optionsNumbers.length; i++) {
            System.out.println(optionsNumbers[i] + "." + options[i]);
        }

        while (true) {
            try {
                int userInput = intPrompt("Enter your choice: ");
                if (userInput >= 1 && userInput <= 4) {
                    System.out.println("Success");
                    break;
                } else {
                    System.out.println("Your input is not within the choices. Please try again.");
                }
            } catch (NumberFormatException exception) {
                System.out.println("Your input is invalid, please try again.");
            } catch (InputMismatchException exception) {
                System.out.println("Only numbers are allowed, please try again.");
                input.nextLine();
            }
        }
    }

    public static boolean leapYearOrNot(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 != 0) {
            return true;
        } else return year % 400 == 0;
    }

    public static String compareHeights(int a, int b, int c) {
        if (a == b && b == c) {
            return "All persons have equal height.";
        } else if (a == b) {
            if (a > c) {
                return "Person 1 and Person 2 are the tallest.";
            } else {
                return "Person 3 is the tallest.";
            }
        } else if (a == c) {
            if (a > b) {
                return "Person 1 and Person 3 are the tallest.";
            } else {
                return "Person 2 is the tallest.";
            }
        } else if (b == c) {
            if (b > a) {
                return "Person 2 and Person 3 are the tallest.";
            } else {
                return "Person 1 is the tallest.";
            }
        } else if (a > b && a > c) {
            return "Person 1 is the tallest.";
        } else if (b > a && b > c) {
            return "Person 2 is the tallest.";
        } else {
            return "Person 3 is the tallest.";
        }
    }

    public static void fibonacciSeries(int limit) {
        int firstNumber = 1;
        int secondNumber = 1;

        System.out.print(firstNumber + " " + secondNumber + " ");

        for (int i = 3; i <= limit; i++) {
            int next = firstNumber + secondNumber;
            System.out.print(next + " ");
            firstNumber = secondNumber;
            secondNumber = next;
        }
    }

    public static void createDiamond(int rowsFromMiddle) {
        int numberOfColumns = rowsFromMiddle * 2 - 1;
        for (int i = 1; i <= rowsFromMiddle; i++) {
            for (int j = 1; j <= (numberOfColumns - (2 * i - 1)) / 2; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        for (int i = rowsFromMiddle - 1; i >= 1; i--) {
            for (int j = 1; j <= (numberOfColumns - (2 * i - 1)) / 2; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    public static String stringPrompt(String prompt)
    {
        System.out.print(prompt);
        return input.next();
    }

    public static int intPrompt(String prompt)
    {
        System.out.print(prompt);
        return input.nextInt();
    }
}