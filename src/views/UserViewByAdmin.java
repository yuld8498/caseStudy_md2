package views;

import model.User;
import service.UserService;
import util.AppUtils;
import util.InstantUtils;
import util.ValidateUtils;

import java.time.Instant;
import java.util.*;

public class UserViewByAdmin {
    private static final Scanner SCANNER = new Scanner(System.in);
    UserService userService;
    public void menuUser(){
        userService = UserService.getInstance();
        int chose;
        do {
            System.out.println("==================== USER MANAGEMENT ======================");
            System.out.printf("%10s%s", "", "1. Show User List\n");
            System.out.printf("%10s%s", "", "2. Show Infomation User List\n");
            System.out.printf("%10s%s", "", "3. Add a New User\n");
            System.out.printf("%10s%s", "", "4. Delete a User By ID\n");
            System.out.printf("%10s%s", "", "5. Find a User By UserName\n");
            System.out.printf("%10s%s", "", "6. Find a User By ID\n");
            System.out.printf("%10s%s", "", "7. Sort User List for ID ASC\n");
            System.out.printf("%10s%s", "", "8. Sort User List for ID ESC\n");
            System.out.printf("%10s%s", "", "9. Back to Menu Admin\n");
            chose = AppUtils.choseAgain(1,9);
            switch (chose){
                case 1:  System.out.printf("%10s%34s%26s%32s%35s%40s%65s%45s\n",
                        "ID","user Name","Password","Full Name","Mobile","Email","Address","Create At\n");
                  for (User user : userService.findAll()){
                      System.out.println(InstantUtils.userFormat(user));
                  }
                    break;
                case 2:
                    System.out.println("Enter ID of User want to find: ");
                    Long ID = AppUtils.retryParseLong();
                    if (userService.showInfomationUserbyID(ID)==null){
                        System.out.println("Can't find this User with " + ID + " in user list, please check again.");
                    }else {
                        System.out.printf("%10s%34s%26s%32s%35s%40s%65s%45s\n",
                                "ID","user Name","Password","Full Name","Mobile","Email","Address","Create At\n");
                        System.out.println(InstantUtils.userFormat(userService.showInfomationUserbyID(ID)));
                    }
                    break;
                case 3:
                    userService.addNewUser(createUser());
                    break;
                case 4:
                    System.out.println("Enter ID of User you want to delete: ");
                    Long iD = AppUtils.retryParseLong();
                    userService.deleteAUserByID(iD);
                    break;
                case 5:
                    System.out.println("Enter userName: ");
                    String name = AppUtils.inputStringAgain("User name");
                    findUserByName(name);
                    break;
                case 6:
                    System.out.println("Enter ID User: ");
                    Long id = AppUtils.retryParseLong();
                    userService.findUserByID(id);
                    break;
                case 7:
                    sortByIDASC();
                    break;
                case 8:
                    sortByIDESC();
                    break;
            }
        }while (chose!=9);
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
    public void findUserByName(String name){
        for (User user : userService.findAll()){
            if (user.getUserName().equals(name)){
                System.out.println(InstantUtils.userFormat(user));
                return;
            }
        }
        System.out.println("Can't find this User name, please check again.");
    }
    public void sortByIDASC(){
        List<User> list = new ArrayList<>();
        for (User user : userService.findAll()){
            list.add(user);
        }
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return (int) (o1.getID()- o2.getID());
            }
        });
        for (User user : list){
            System.out.println(InstantUtils.userFormat(user));
        }
    }
    public void sortByIDESC(){
        List<User> list = new ArrayList<>();
        for (User user : userService.findAll()){
            list.add(user);
        }
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return (int) (o2.getID()- o1.getID());
            }
        });
        for (User user : list){
            System.out.println(InstantUtils.userFormat(user));
        }
    }
}
