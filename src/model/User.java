package model;

import java.time.Instant;

public class User {
    private String ROLE;
    private Long ID;
    private String userName;
    private String password;
    private String fullName;
    private String mobile;
    private String email;
    private String address;
    private Instant createdAt;
    private Instant updatedAt;
    public User(){
    }
    public User(Long ID, String userName, String password, String fullName, String mobile, String email, String address){
        this.ID = ID;
        this.userName =userName;
        this.password = password;
        this.fullName = fullName;
        this.mobile=mobile;
        this.email = email;
        this.address =address;
    }

    public User(Long id, String userName, String password, String fullName, String mobile, String email, String address,String ROLE, Instant createAt, Instant updateAt) {
        this.ID = id;
        this.ROLE = ROLE;
        this.userName =userName;
        this.password =password;
        this.fullName =fullName;
        this.mobile =mobile;
        this.email = email;
        this.address=address;
        this.createdAt = createAt;
        this.updatedAt = updateAt;
    }

    public String getROLE() {
        return ROLE;
    }

    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public static User parseUser(String string){
        String[] strings = string.split(",");
        Long ID = Long.parseLong(strings[0].trim());
        String userName = strings[1].trim();
        String password = strings[2].trim();
        String fullName = strings[3].trim();
        String mobile = strings[4].trim();
        String email = strings[5].trim();
        String address = strings[6].trim();
        String ROLE = strings[7].trim();
        Instant createAt = Instant.parse(strings[8].trim());
        Instant updateAt= null;
        String temp = strings[9];
        if (temp==null&&temp.equals("null")){
            updateAt = Instant.parse(temp);
        }
        return new User(ID,userName,password,fullName,mobile,email,address,ROLE,createAt,updateAt);
    }
    public String toString(){
        return String.format("%s, %s, %s, %s, %s, %s, %s,%s, %s, %s",
                ID, userName, password,fullName,mobile,email,address,ROLE,createdAt,updatedAt
                );
    }
}
