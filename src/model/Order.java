package model;

import java.time.Instant;

public class Order {
    private Long ID;
    private String productName;
    private int quaility;
    private Double price;
    private String userNameOrder;
    private String addressOfUser;
    private String phoneNumberOfUser;
    private Instant createAt;
    private String note;

    public Order(){
    }

    public Order(Long id, String productName, int quaility, Double price, String userNameOrder, String addressOfUser, String phoneNumberOfUser,String note, Instant createAt) {
        this.ID =id;
        this.productName= productName;
        this.quaility =quaility;
        this.price=price;
        this.userNameOrder =userNameOrder;
        this.addressOfUser =addressOfUser;
        this.phoneNumberOfUser = phoneNumberOfUser;
        this.note = note;
        this.createAt =createAt;
    }

    public static Order parseOrder(String string){
        String[] strings = string.split(",");
        Long ID = Long.valueOf(strings[0].trim());
        String productName = strings[1].trim();
        int quaility = Integer.parseInt(strings[2].trim());
        Double price = Double.valueOf(strings[3].trim());
        String phoneNumberOfUser = strings[7].trim();
        String userNameOrder = strings[5].trim();
        String addressOfUser = strings[6].trim();
        String note = strings[8].trim();
        Instant createAt = Instant.parse(strings[9].trim());
        return new Order(ID,productName,quaility,price,userNameOrder,addressOfUser,phoneNumberOfUser,note,createAt);
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%d,%f,%s,%s,%s,%s,%s",ID,productName,quaility,price,userNameOrder,addressOfUser,phoneNumberOfUser,note,createAt);
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuaility() {
        return quaility;
    }

    public void setQuaility(int quaility) {
        this.quaility = quaility;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUserNameOrder() {
        return userNameOrder;
    }

    public void setUserNameOrder(String userNameOrder) {
        this.userNameOrder = userNameOrder;
    }

    public String getAddressOfUser() {
        return addressOfUser;
    }

    public void setAddressOfUser(String addressOfUser) {
        this.addressOfUser = addressOfUser;
    }

    public String getPhoneNumberOfUser() {
        return phoneNumberOfUser;
    }

    public void setPhoneNumberOfUser(String phoneNumberOfUser) {
        this.phoneNumberOfUser = phoneNumberOfUser;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }
}
