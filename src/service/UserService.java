package service;

import model.User;
import util.AppUtils;
import util.CSVUtils;
import util.InstantUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {
    public final static String PATHUSER = "src/data/User.csv";
    private static UserService instance;
    private UserService(){}
    public static UserService getInstance(){
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
       for (User user:list){
           if (user.getID().equals(userID)){
               System.out.println(InstantUtils.userFormat(user));
               if (AppUtils.areYouSure("Delete User")){
                   list.remove(user);
                   CSVUtils.write(PATHUSER,list);
                   System.out.println("Delete User is complete.");
                   return;
               }
               System.out.println("Delete user is cancel.");
               return;
           }
       }
        System.out.println("Can't find this ID, please check again.");
    }

    @Override
    public User showInfomationUserbyID(Long userID) {
        for (User user1 : findAll()){
            if (user1.getID().equals(userID)){
                return user1;
            }
        }
        return null;
    }

    @Override
    public void updateInfomationUser(User newUser) {
        List<User> userList = findAll();
        for (User user : userList) {
            if (user.getID() == newUser.getID()||user.getUserName().equalsIgnoreCase(newUser.getUserName())) {
                user.setUserName(newUser.getUserName());
                user.setUpdatedAt(Instant.now());
                user.setAddress(newUser.getAddress());
                user.setEmail(newUser.getEmail());
                user.setMobile(newUser.getMobile());
                user.setPassword(newUser.getPassword());
                user.setFullName(newUser.getFullName());
                CSVUtils.write(PATHUSER,userList);
                return;
            }
        }
        System.out.println("Can't find this User in user list, please check again.");
    }

    @Override
    public User findUserByID(Long ID) {
        for (User user : findAll()){
            if (user.getID().equals(ID)){
                System.out.println("User you want to find: ");
                System.out.println(InstantUtils.userFormat(user));
                return user;
            }
        }
        System.out.println("can't find this ID.");
        return null;
    }

    @Override
    public boolean existsByID(Long ID) {
        List<User> users = new ArrayList<>();
        for (User user : users){
            if (user.getID()==ID){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsByUserName(String username) {
        List<User> users = new ArrayList<>();
        for (User user : users){
            if (user.getUserName().equalsIgnoreCase(username)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        List<User> users = new ArrayList<>();
        for (User user : users){
            if (user.getMobile().equalsIgnoreCase(phoneNumber)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        List<User> users = new ArrayList<>();
        for (User user : users){
            if (user.getEmail().equalsIgnoreCase(email)){
                return true;
            }
        }
        return false;
    }
}
