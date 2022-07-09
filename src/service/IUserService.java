package service;

import model.User;

import java.util.List;

public interface IUserService {
    void findUserByName(String name);
    User createUser();
    List<User> findAll();
    void sortByIDESC();
    void sortByIDASC();

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
