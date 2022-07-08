package service;

import model.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();

    User loginUser(String userName, String password);

    void addNewUser(User user);
    void deleteAUserByID(Long userID);

    User showInfomationUserbyID(Long userID);

    void updateInfomationUser(User user);

    User findUserByID(Long ID);

    boolean existsByID(Long ID);

    boolean existsByUserName(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);
}
