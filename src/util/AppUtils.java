package util;

import java.util.Scanner;

public class AppUtils {
    static Scanner scanner = new Scanner(System.in);

    public static int choseAgain(int min, int max) {
        int option;
        do {
            System.out.print(" => ");
            try {
                option = Integer.parseInt(scanner.nextLine());
                if (option < min || option > max) {
                    System.out.println("is not valid! please chose again: ");
                    continue;
                }
                break;
            } catch (Exception exception) {
                System.out.println("Input double is wrong, please re-enter: ");
            }
        } while (true);
        return option;
    }

    public static int inputNumberAgain() {
        int result;
        do {
            System.out.print(" => ");
            try {
                result = Integer.parseInt(scanner.nextLine());
                return result;
            } catch (Exception ex) {
                System.out.println("Input double is wrong, please re-enter: ");
            }
        } while (true);
    }

    public static String inputStringAgain(String fieldName) {
        String input;
        System.out.print(" => ");
        while ((input = scanner.nextLine()).isEmpty()) {
            System.out.printf("%s Can not be empty\n", fieldName);
            System.out.print(" => ");
        }
        return input;
    }

    public static double retryParseDouble() {
        Double input;
        do {
            System.out.print(" => ");
            try {
                input = Double.parseDouble(scanner.nextLine());
                return input;
            } catch (Exception ex) {
                System.out.println("Input double is wrong, please re-enter: ");
            }
        } while (true);
    }

    public static Long retryParseLong() {
        Long input;
        do {
            System.out.print(" => ");
            try {
                input = Long.parseLong(scanner.nextLine());
                return input;
            } catch (Exception ex) {
                System.out.println("Input Long is wrong, please re-enter: ");
            }
        } while (true);
    }

    public static boolean areYouSure(String youWant) {
        System.out.println("Are you sure you will " + youWant + " ?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int chose = choseAgain(1, 2);
        if (chose == 1) {
            return true;
        }
        return false;
    }
}
