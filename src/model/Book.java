package model;

import java.time.Instant;

public class Book {
    private Long ID;
    private String name;
    private String author;
    private Double price;
    private int quaility;
    private Instant createdAt;
    private Instant updateAt;

    public Book() {
    }

    public Book(Long ID, String name, String author, Double price, int quaility) {
        this.ID = ID;
        this.name = name;
        this.author = author;
        this.price = price;
        this.quaility = quaility;
    }

    public Book(Long ID, String name, String author, int quaility, Double price, Instant createdAt, Instant updateAt) {
        this.ID = ID;
        this.name = name;
        this.author = author;
        this.price = price;
        this.quaility = quaility;
        this.createdAt =createdAt;
        this.updateAt =updateAt;

    }

    public static Book parseProduct(String string){
        String[] strings =string.split(",");
        Long ID = Long.parseLong(strings[0].trim());
        String name = strings[1].trim();
        String author = strings[2].trim();
        int quaility = Integer.parseInt(strings[3].trim());
        Double price = Double.parseDouble(strings[4].trim());
        Instant createdAt = Instant.parse(strings[5].trim());
        Instant updateAt= Instant.parse(strings[6].trim());
//        String temp = strings[6].trim();
//        if (temp==null&&temp.equals("null")){
//            updateAt = Instant.parse(temp);
//        }
        return new Book(ID,name,author,quaility,price,createdAt,updateAt);
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuaility() {
        return quaility;
    }

    public void setQuaility(int quaility) {
        this.quaility = quaility;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,%s,%s",ID,name,author,quaility,price,createdAt,updateAt);
    }
}
