package service;

import model.User;
import util.AppUtils;
import util.CSVUtils;
import util.InstantUtils;
import util.ValidateUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserService implements IUserService {
    public final static String PATHUSER = "data/User.csv";
    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    @Override
    public List<User> findAll() {
        List<User> listUser = new ArrayList<>();
        List<String> record = CSVUtils.read(PATHUSER);
        for (String s : record) {
            listUser.add(User.parseUser(s));
        }
        return listUser;
    }

    @Override
    public void sortByIDASC() {
        List<User> list = new ArrayList<>();
        for (User user : findAll()) {
            list.add(user);
        }
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return (int) (o1.getID() - o2.getID());
            }
        });
        for (User user : list) {
            System.out.println(InstantUtils.userFormat(user));
        }
    }

    @Override
    public void sortByIDESC() {
        List<User> list = new ArrayList<>();
        for (User user : findAll()) {
            list.add(user);
        }
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return (int) (o2.getID() - o1.getID());
            }
        });
        for (User user : list) {
            System.out.println(InstantUtils.userFormat(user));
        }
    }

    @Override
    public User createUser() {
        System.out.println("Enter UserName: ");
        String userName;
        boolean repeat;
        do {
            userName = AppUtils.inputStringAgain("UserName");
            repeat = !existsByUserName(userName);
            if (!repeat) {
                System.out.println(userName + " already exists");
            } else {
                System.out.println(userName + "is valid");
            }
        } while (!repeat);
        System.out.println("Enter password: ");
        String password;
        do {
            password = AppUtils.inputStringAgain("Password");
            repeat = !ValidateUtils.isPasswordValid(password);
            if (repeat) {
                System.out.println("Password is not valid");
            }
        } while (repeat);
        System.out.println("Enter Full name: ");
        String fullName = AppUtils.inputStringAgain("Full name");
        System.out.println("Enter phone number: ");
        String phoneNumber;
        do {
            phoneNumber = AppUtils.inputStringAgain("Phone Number");
            repeat = (ValidateUtils.isPhoneValid(phoneNumber) && !existsByPhoneNumber(phoneNumber));
            if (!repeat) {
                System.out.println("Phone Number is not valid");
            }
        } while (!repeat);
        System.out.println("Enter Full email: ");
        String email;
        do {
            email = AppUtils.inputStringAgain("email");
            repeat = !(ValidateUtils.isEmailValid(email) && !existsByEmail(email));
            if (repeat) {
                System.out.println("email is not valid");
            }
        } while (repeat);
        System.out.println("Enter Full address: ");
        String address = AppUtils.inputStringAgain("address");
        Instant createAt = Instant.now();
        Instant updateAt = Instant.now();
        String ROLE = "User";
        Long ID = System.currentTimeMillis() / 1000;
        return new User(ID, userName, password, fullName, phoneNumber, email, address, ROLE, createAt, updateAt);
    }

    @Override
    public User loginUser(String userName, String password) {
        List<User> userList = findAll();
        for (User user : userList) {
            if (user.getUserName().equalsIgnoreCase(userName) && user.getPassword().equalsIgnoreCase(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void addNewUser(User newUser) {
        newUser.setROLE("USER");
        newUser.setID(System.currentTimeMillis() / 1000);
        newUser.setCreatedAt(Instant.now());
        newUser.setUpdatedAt(Instant.now());
        List<User> userList = findAll();
        for (User user1 : userList) {
            if (user1.getEmail().equalsIgnoreCase(newUser.getEmail())) {
                System.out.println("1 EMAIL can't create 2 ACCOUNTS ");
                return;
            }
            if (user1.getMobile().equalsIgnoreCase(newUser.getMobile())) {
                System.out.println("1 PHONE NUMBER can't create 2 ACCOUNTS ");
                return;
            }
        }
        System.out.println(InstantUtils.userFormat(newUser));
        if (AppUtils.areYouSure("Add new User")) {
            userList.add(newUser);
            CSVUtils.write(PATHUSER, userList);
            System.out.println("Add User is Success");
            return;
        }
        System.out.println("Add new User is cancel");
    }

    @Override
    public void deleteAUserByID(Long userID) {
        List<User> list = findAll();
        for (User user : list) {
            if (user.getID().equals(userID)) {
                System.out.println(InstantUtils.userFormat(user));
                if (AppUtils.areYouSure("Delete User")) {
                    list.remove(user);
                    CSVUtils.write(PATHUSER, list);
                    System.out.println("Delete User is complete.");
                    return;
                }
                System.out.println("Delete user is cancel.");
                return;
            }
        }
        System.err.println("Can't find this ID, please check again.");
    }

    @Override
    public User showInfomationUserbyID(Long userID) {
        for (User user1 : findAll()) {
            if (user1.getID().equals(userID)) {
                return user1;
            }
        }
        return null;
    }

    @Override
    public void updateInfomationUser(User newUser) {
        List<User> userList = findAll();
        for (User user : userList) {
            if (user.getID() == newUser.getID() || user.getUserName().equalsIgnoreCase(newUser.getUserName())) {
                user.setUserName(newUser.getUserName());
                user.setUpdatedAt(Instant.now());
                user.setAddress(newUser.getAddress());
                user.setEmail(newUser.getEmail());
                user.setMobile(newUser.getMobile());
                user.setPassword(newUser.getPassword());
                user.setFullName(newUser.getFullName());
                CSVUtils.write(PATHUSER, userList);
                return;
            }
        }
        System.err.println("Can't find this User in user list, please check again.");
    }

    @Override
    public User findUserByID(Long ID) {
        for (User user : findAll()) {
            if (user.getID().equals(ID)) {
                System.out.println("User you want to find: ");
                System.out.println(InstantUtils.userFormat(user));
                return user;
            }
        }
        System.err.println("can't find this ID.");
        return null;
    }

    @Override
    public void findUserByName(String name) {
        for (User user : findAll()) {
            if (user.getUserName().equals(name)) {
                System.out.println(InstantUtils.userFormat(user));
                return;
            }
        }
        System.err.println("Can't find this User name, please check again.");
    }

    @Override
    public boolean existsByID(Long ID) {
        List<User> users = new ArrayList<>();
        for (User user : users) {
            if (user.getID() == ID) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsByUserName(String username) {
        List<User> users = new ArrayList<>();
        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        List<User> users = new ArrayList<>();
        for (User user : users) {
            if (user.getMobile().equalsIgnoreCase(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        List<User> users = new ArrayList<>();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}
