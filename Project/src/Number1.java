import java.util.InputMismatchException;
import java.util.Scanner;

public class Number1 {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        String[] options = { "Leap Year Checker", "Compare Height", "Series", "Generate a Diamond", "Exit" };

        System.out.print("Enter your name: ");
        String name = input.nextLine();
        System.out.println("Hello, " + name + "!\n");

        int option = -1;
        while (option != 5) {
            // choicesmenu
            System.out.println("Tell me what you want to do, here are your options: ");
            for (int i = 0, number = 1; i < options.length; i++) {
                System.out.println(number++ + ". " + options[i]);
            }
            System.out.println();
            try {
                System.out.print("Enter your choice: ");
                option = input.nextInt();
                switch (option) {

                    // Angas naman niyan, naka switch case na, mwehehehe
                    // HAHHAHAHAHAHAH
                    case 1:
                        System.out.print("Enter a year: ");
                        int year = input.nextInt();
                        if (leapYearOrNot(year))
                            System.out.print("Year " + year + " is a leap year.\n");
                        else
                            System.out.print("Year " + year + " is not a leap year.\n");
                        break;
                    case 2:
                        System.out.print("Enter height of person 1(cm): ");
                        int a = input.nextInt();
                        System.out.print("Enter height of person 2(cm): ");
                        int b = input.nextInt();
                        System.out.print("Enter height of person 3(cm): ");
                        int c = input.nextInt();
                        System.out.println(compareHeights(a, b, c));
                        break;
                    case 3:
                        System.out.print("Enter the value of n: ");
                        int n = input.nextInt();
                        fibonacciSeries(n);
                        break;
                    case 4:
                        System.out.print("Enter the size of diamond(starting from top to middle): ");
                        int size = input.nextInt();
                        createDiamond(size);
                        break;
                    case 5:
                        System.out.print("Program Exited.\n");
                        break;
                    default:
                        System.out.print("Invalid choice, choose from 1 to 5!\n");
                        break;
                }
            } catch (NumberFormatException e) {
                // user input a wrong number format
                System.out.print("Only whole numbers are allowed, please try again.");
                input.nextLine();
            } catch (InputMismatchException e) {
                // wrong input from scanner example: int but input was string
                System.out.println("Your input is invalid, please enter a correct format.");
                input.nextLine();
            }
            System.out.println();
        }
        input.close();
    }

    // Methods used is switch case
    // Leap year checker method
    public static boolean leapYearOrNot(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 != 0) {
            return true;
        } else
            return year % 400 == 0;
    }

    // Compare height method
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

    // fibonacci series method
    // find a way to do recursively?
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
        System.out.println();
    }

    // diamond generator method
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
}