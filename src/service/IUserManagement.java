package service;

public interface IUserManagement {
    void showInfodmation(Long userID);
    void changeInfomation(Long userID);
    void changepassword(Long userID, String newPassword);
    void changeEmail(Long userID, String newEmail);
    void changeAddress(Long userID, String newAddress);
    void changPhoneNumber(Long userID, String newPhoneNumber);
}
