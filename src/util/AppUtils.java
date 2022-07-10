package util;

import jdk.swing.interop.SwingInterOpUtils;
import model.Order;
import model.Product;
import model.User;

import javax.print.DocFlavor;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
    public static void bill(List<Order> list){
        System.out.printf("%120s","YUL BOOKs STORE\n");
        System.out.printf("%120s","28 Nguyen Tri Phuong\n");
        System.out.printf("%115s","Mobile: 0962435396 - 01642156169\n");
        System.out.println("-----------------------------------------------------------------------------------ORDER BILL-----------------------------------------------------------------------------------");
        System.out.println("\n");
        System.out.printf("||%s%30d%s","Bill No.",System.currentTimeMillis()/10000,"\n\n");
        System.out.printf("||%s%30s%s","Create at",InstantUtils.instantToString(Instant.now()),"\n\n");
        System.out.printf("||%s%30s%s","User Order",callUser().getUserName(),"\n\n");
        System.out.printf("||%s%20s%s","Cashier","ADMIN","\n\n");
        System.out.printf("||%-70s%-50s%-45s%-45s%s","Product Name","Amount","Price","Total","\n");
        double resul =0;
        for (Order order : list){
            System.out.printf("%-70s%-40s%-40s%-40s%s",order.getProductName(),order.getQuaility(),order.getPrice(),(order.getQuaility()*order.getPrice()),"\n\n");
        }
        System.out.println("RESULT:          " + InstantUtils.doubleToVND(resul));
        System.out.printf("%s","Thank's for using our service!\n");
    }

    public static User callUser(){
        User user = null ;
        List<String> record = CSVUtils.read("src\\data\\login.csv");
        for (String s : record) {
            user = User.parseUser(s);
        }
        return user;
    }
}
