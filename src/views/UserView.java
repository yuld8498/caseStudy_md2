package views;

import model.Order;
import model.Product;
import model.User;
import service.LoanProductService;
import service.ProduceService;
import service.UserManagement;
import util.AppUtils;
import util.CSVUtils;
import util.InstantUtils;
import util.ValidateUtils;

import javax.swing.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserView {
    UserManagement userManagement;

    public void showMenu() {
        userManagement = UserManagement.getInstance();
        int chose;
        do {
            System.out.println("==================== User View ======================");
            System.out.printf("%10s%s", "", "1. Show Infomation\n");
            System.out.printf("%10s%s", "", "2. Change Infomation\n");
            System.out.printf("%10s%s", "", "3. Order Service\n");
            System.out.printf("%10s%s", "", "4. Loan Items\n");
            System.out.printf("%10s%s", "", "5. Back to menu\n");
            System.out.println("Enter chose : ");
            chose = AppUtils.choseAgain(1, 5);
            switch (chose) {
                case 1:
                    System.out.println(InstantUtils.userFormat(callUser()));
                    break;
                case 2:
                    changeInfo();
                    break;
                case 3:
                    OrderItemView orderItemView = new OrderItemView();
                    orderItemView.menuOrder();
                    break;
                case 4:
                    loanItems();
                    break;
                case 5:
                    List<User> list = new ArrayList<>();
                    CSVUtils.write("src\\data\\login.csv", list);
                    break;
            }
        } while (chose != 5);
    }

    public void loanItems() {
        LoanProductService loanProductService = LoanProductService.getInstance();
        int chose;
        do {
            System.out.println("==================== LOAN ITEMS ======================");
            System.out.printf("%10s%s", "", "1. Show Loan List\n");
            System.out.printf("%10s%s", "", "2. Add Items to loan\n");
            System.out.printf("%10s%s", "", "3. Delete Items to loan\n");
            System.out.printf("%10s%s", "", "4. Confirm\n");
            System.out.printf("%10s%s", "", "5. Back \n");
            System.out.println("Enter you chose: ");
            chose = AppUtils.choseAgain(1, 5);
            switch (chose){
                case 1:
                    loanProductService.showLoanList();
                    break;
                case 2:
                    loanProductService.loanProducts();
                    break;
                case 3:
                    loanProductService.deleteItemInLoanList();
                    break;
                case 4:
                    loanProductService.confirmLoanList();
                    break;
            }
        } while (chose != 5);

    }

    public void changeInfo() {
        userManagement.getInstance();
        int chose;
        do {
            System.out.println("==================== CHANGE INFOMATION ======================");
            System.out.printf("%10s%s", "", "1. Change Password\n");
            System.out.printf("%10s%s", "", "2. Change phone number\n");
            System.out.printf("%10s%s", "", "3. Change email\n");
            System.out.printf("%10s%s", "", "4. Change address\n");
            System.out.printf("%10s%s", "", "5. Change full name\n");
            System.out.printf("%10s%s", "", "6. Back to menu\n");
            System.out.println("Enter chose : ");
            chose = AppUtils.choseAgain(1, 6);
            switch (chose) {
                case 1:
                    String newPw;
                    do {
                        System.out.println("Enter new password: ");
                        newPw = AppUtils.inputStringAgain("pw");
                        if (ValidateUtils.isPasswordValid(newPw)) {
                            userManagement.changepassword(callUser().getID(), newPw);
                        }
                    } while (!ValidateUtils.isPasswordValid(newPw));
                    break;
                case 2:
                    String newPN;
                    do {
                        System.out.println("Enter new Phone number: ");
                        newPN = AppUtils.inputStringAgain("Phone number");
                        if (ValidateUtils.isPhoneValid(newPN)) {
                            userManagement.changePhoneNumber(callUser().getID(), newPN);
                        }
                    } while (!ValidateUtils.isPhoneValid(newPN));
                    break;
                case 3:
                    String newEmail;
                    do {
                        System.out.println("Enter new email: ");
                        newEmail = AppUtils.inputStringAgain("email");
                        if (ValidateUtils.isEmailValid(newEmail)) {
                            userManagement.changePhoneNumber(callUser().getID(), newEmail);
                        }
                    } while (!ValidateUtils.isEmailValid(newEmail));
                    break;
                case 4:
                    System.out.println("Enter new Address");
                    String address = AppUtils.inputStringAgain("address");
                    userManagement.changeAddress(callUser().getID(), address);
                    break;
                case 5:
                    System.out.println("Enter new Full Name");
                    String fullname = AppUtils.inputStringAgain("full name");
                    userManagement.changeFullname(callUser().getID(), fullname);
                    break;
            }
        } while (chose != 6);
    }

    public static User callUser() {
        User user = null;
        List<String> record = CSVUtils.read("src\\data\\login.csv");
        for (String s : record) {
            user = User.parseUser(s);
        }
        return user;
    }

}
