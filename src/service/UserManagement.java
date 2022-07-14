package service;

import model.Order;
import model.User;
import util.AppUtils;
import util.CSVUtils;
import util.InstantUtils;

import java.util.ArrayList;
import java.util.List;

public class UserManagement implements IUserManagement {
    private static final String PATHLOGIN = "data\\login.csv";
    private static final String PATHUSER = "data\\User.csv";
    private static final String PATHORDER = "data\\OrderItem.csv";
    private static UserManagement instance;

    private UserManagement() {

    }

    public static UserManagement getInstance() {
        if (instance == null)
            instance = new UserManagement();
        return instance;
    }

    public List<Order> findAllOrder() {
        List<Order> orderList = new ArrayList<>();
        List<String> record = CSVUtils.read(PATHORDER);
        for (String string : record) {
            orderList.add(Order.parseOrder(string));
        }
        return orderList;
    }

    public List<User> findAllUser() {
        List<User> userList = new ArrayList<>();
        List<String> record = CSVUtils.read(PATHUSER);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        System.out.printf("%10s%34s%26s%32s%35s%40s%65s%45s%45s\n",
                "ID", "user Name", "Password", "Full Name", "Mobile", "Email", "Address", "Create At", "Update At\n");
        for (String string : record) {
            userList.add(User.parseUser(string));
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        return userList;
    }

    @Override
    public void showInfodmation(Long userID) {
        for (User user : findAllUser()) {
            if (user.getID().equals(userID)) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

                System.out.printf("%10s%34s%26s%32s%35s%40s%65s%45s%45s\n",
                        "ID", "user Name", "Password", "Full Name", "Mobile", "Email", "Address", "Create At", "Update At\n");
                System.out.println(InstantUtils.userFormat(user));
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

                return;
            }
        }
        System.out.println("Can't find user this ID, please check again.");
    }

    @Override
    public void changeInfomation(Long userID) {
        for (User user : findAllUser()) {
            if (user.getID().equals(userID)) {
                System.out.println(InstantUtils.userFormat(user));
                System.out.println("\n");
            }
            System.out.println("Can't find user this ID, please check again.");
        }
    }

    public void changePhoneNumber(Long userID, String newPhoneNumber) {
        List<User> fullUser = new ArrayList<>(findAllUser());
        for (User user : findAllUser()) {
            if (user.getID().equals(userID)) {
                System.out.println(InstantUtils.userFormat(user));
                if (AppUtils.areYouSure("change PhoneNumber")) {
                    user.setMobile(newPhoneNumber);
                    System.out.println("PhoneNumber change is correct");
                    List<User> list = new ArrayList<>();
                    list.add(user);
                    CSVUtils.write(PATHLOGIN, list);
                    CSVUtils.write(PATHUSER, fullUser);
                    return;
                }
            }
            System.out.println("Can't find user this ID, please check again.");
        }
    }

    public void changeFullname(Long userID, String fullname) {
        List<User> fullUser = new ArrayList<>(findAllUser());
        for (User user : findAllUser()) {
            if (user.getID().equals(userID)) {
                System.out.println(InstantUtils.userFormat(user));
                if (AppUtils.areYouSure("change FullName")) {
                    user.setFullName(fullname);
                    System.out.println("FullName change is correct");
                    List<User> list = new ArrayList<>();
                    list.add(user);
                    CSVUtils.write(PATHLOGIN, list);
                    CSVUtils.write(PATHUSER, fullUser);
                    return;
                }
            }
            System.out.println("Can't find user this ID, please check again.");
        }
    }

    @Override
    public void changepassword(Long userID, String newPassword) {
        List<User> fullUser = new ArrayList<>(findAllUser());
        for (User user : fullUser) {
            if (user.getID().equals(userID)) {
                System.out.println(InstantUtils.userFormat(user));
                if (AppUtils.areYouSure("change password")) {
                    user.setPassword(newPassword);
                    System.out.println("Password change is correct");
                    List<User> list = new ArrayList<>();
                    list.add(user);
                    CSVUtils.write(PATHUSER, fullUser);
                    CSVUtils.write(PATHLOGIN, list);
                    return;
                }
            }
            System.out.println("Can't find user this ID, please check again.");
        }
    }

    @Override
    public void changeEmail(Long userID, String newEmail) {
        List<User> fullUser = new ArrayList<>(findAllUser());
        for (User user : findAllUser()) {
            if (user.getID().equals(userID)) {
                System.out.println(InstantUtils.userFormat(user));
                if (AppUtils.areYouSure("change Email")) {
                    user.setEmail(newEmail);
                    System.out.println("Password change is correct");
                    List<User> list = new ArrayList<>();
                    list.add(user);
                    CSVUtils.write(PATHLOGIN, list);
                    CSVUtils.write(PATHUSER, fullUser);
                    return;
                }
            }
            System.out.println("Can't find user this ID, please check again.");
        }
    }

    @Override
    public void changeAddress(Long userID, String newAddress) {
        List<User> fullUser = new ArrayList<>(findAllUser());
        for (User user : findAllUser()) {
            if (user.getID().equals(userID)) {
                System.out.println(InstantUtils.userFormat(user));
                if (AppUtils.areYouSure("change Address")) {
                    user.setAddress(newAddress);
                    System.out.println("Password change is correct");
                    List<User> list = new ArrayList<>();
                    list.add(user);
                    CSVUtils.write(PATHLOGIN, list);
                    CSVUtils.write(PATHUSER, fullUser);
                    return;
                }
            }
            System.out.println("Can't find user this ID, please check again.");
        }
    }

    @Override
    public void changPhoneNumber(Long userID, String newPhoneNumber) {
        List<User> fullUser = new ArrayList<>(findAllUser());
        for (User user : findAllUser()) {
            if (user.getID().equals(userID)) {
                System.out.println(InstantUtils.userFormat(user));
                if (AppUtils.areYouSure("change Phone number")) {
                    user.setMobile(newPhoneNumber);
                    System.out.println("Password change is correct");
                    List<User> list = new ArrayList<>();
                    list.add(user);
                    CSVUtils.write(PATHLOGIN, list);
                    CSVUtils.write(PATHUSER, fullUser);
                    return;
                }
            }
            System.out.println("Can't find user this ID, please check again.");
        }
    }
}
