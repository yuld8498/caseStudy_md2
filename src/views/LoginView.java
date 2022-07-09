package views;

import model.User;
import service.UserService;
import util.AppUtils;
import util.CSVUtils;
import util.InstantUtils;
import util.ValidateUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LoginView {
    private static final String USERLIST = "src\\data\\login.csv";
    UserService userService = UserService.getInstance();
    public void laucherLogin(){
        boolean again;
        int chose;
        do {
            System.out.println("==================== LOGIN ======================");
            System.out.printf("%10s%s","","1. Login By Admin\n");
            System.out.printf("%10s%s","","2. Login By User\n");
            System.out.printf("%10s%s","","3. Create User\n");
            System.out.printf("%10s%s","","4. Out Program\n");
            System.out.println("Enter you chose: ");
            chose = AppUtils.choseAgain(1,4);
            switch (chose){
                case 1:
                    boolean repeatLoginAdmin =false;
                    do {
                        if (loginAdmin()){
                            AdminView adminView = new AdminView();
                            adminView.menuAdmin();
                        }else {
                            repeatLoginAdmin=AppUtils.areYouSure("Login again");
                        }
                    }while (repeatLoginAdmin);
                    break;
                case 2:
                    boolean repeatLoginUser =false;
                    do {
                        if (loginUser()){
                            UserView userView = new UserView();
                            userView.showMenu();
                        }else {
                            repeatLoginUser = AppUtils.areYouSure("Login again");
                        }
                    }while (repeatLoginUser);
                    break;
                case 3:
                    do {
                        userService.addNewUser(createUser());
                    }while (AppUtils.areYouSure("Create more User"));
                    break;
            }
        }while (chose!=4);
    }
    public boolean loginAdmin(){
        System.out.print("Enter Admin Username: ");
        String User = AppUtils.inputStringAgain("Admin Username");
        System.out.print("Enter Admin password: ");
        String password = AppUtils.inputStringAgain("Password");
        for (User user : userService.findAll()){
            if (user.getPassword().equals(password)&&user.getUserName().equals(User)&&user.getROLE().equalsIgnoreCase("admin")){
                List<User> list = new ArrayList<>();
                list.add(user);
                CSVUtils.write(USERLIST,list);
                return true;
            }
        }
        System.out.println("UserName or PassWord is wrong, please check again.");
        return false;
    }
    public boolean loginUser(){
        System.out.println("Enter  Username");
        String User = AppUtils.inputStringAgain("Admin Username");
        System.out.println("Enter password: ");
        String password = AppUtils.inputStringAgain("Password");
        for (User user : userService.findAll()){
            if (user.getPassword().equals(password)&&user.getUserName().equals(User)&&user.getROLE().equalsIgnoreCase("user")){
                List<User> list = new ArrayList<>();
                list.add(user);
                CSVUtils.write(USERLIST,list);
                return true;
            }
        }
        System.out.println("UserName or PassWord is wrong, please check again.");
        return false;
    }
    public static User callUser(){
        User user = null ;
        List<String> record = CSVUtils.read("src\\data\\login.csv");
        for (String s : record) {
            user = User.parseUser(s);
        }
        return user;
    }
    public User createUser(){
        System.out.println("Enter UserName: ");
        String userName;
        boolean repeat ;
        do {
            userName = AppUtils.inputStringAgain("UserName");
            repeat= !userService.existsByUserName(userName);
            if (!repeat){
                System.out.println(userName + " already exists");
            }else {
                System.out.println(userName+"is valid");
            }
        }while (!repeat);
        System.out.println("Enter password: ");
        String password;
        do {
            password=AppUtils.inputStringAgain("Password");
            repeat = !ValidateUtils.isPasswordValid(password);
            if (repeat){
                System.out.println("Password is not valid");
            }
        }while (repeat);
        System.out.println("Enter Full name: ");
        String fullName = AppUtils.inputStringAgain("Full name");
        System.out.println("Enter phone number: ");
        String phoneNumber;
        do {
            phoneNumber=AppUtils.inputStringAgain("Phone Number");
            repeat = (ValidateUtils.isPhoneValid(phoneNumber)&&!userService.existsByPhoneNumber(phoneNumber));
            if (!repeat){
                System.out.println("Phone Number is not valid");
            }
        }while (!repeat);
        System.out.println("Enter Full email: ");
        String email;
        do {
            email=AppUtils.inputStringAgain("email");
            repeat = !(ValidateUtils.isEmailValid(email)&&!userService.existsByEmail(email));
            if (repeat){
                System.out.println("email is not valid");
            }
        }while (repeat);
        System.out.println("Enter Full address: ");
        String address=AppUtils.inputStringAgain("address");
        Instant createAt = Instant.now();
        Instant updateAt = null;
        String ROLE = "User";
        Long ID = System.currentTimeMillis()/1000;
        return new User(ID,userName,password,fullName,phoneNumber,email,address,ROLE,createAt,updateAt);
    }
}
