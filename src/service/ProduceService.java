package service;

import model.Book;
import util.AppUtils;
import util.CSVUtils;
import util.InstantUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProduceService implements IProduceService {
    public final static String PATHPRODUCT = "data/product.csv";
    private static ProduceService instance;

    private ProduceService() {

    }

    public static ProduceService getInstance() {
        if (instance == null) {
            instance = new ProduceService();
            return instance;
        }
        return instance;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        List<String> record = CSVUtils.read(PATHPRODUCT);
        for (String string : record) {
            books.add(Book.parseProduct(string));
        }
        return books;
    }

    @Override
    public Book findProductByID(Long id) {
        List<Book> list = new ArrayList<>(findAll());
        for (Book book : list) {
            if (book.getID().equals(id)) {
                System.out.println("Product you want to find : ");
                System.out.printf("\n\t%8s%50s%58s%28s%18s%28s\n", "ID", "Name", "Author", "Quaility", "Price", "Create At\n");
                System.out.println(InstantUtils.productFomat(book));
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                return book;
            }
        }
        System.out.println("Cant find this ID, please check again!");
        return null;
    }

    @Override
    public Book findProductbyName(String name) {
        int count =0;
        String nameIgnoreCase = name.toLowerCase();
        List<Book> list = new ArrayList<>(findAll());
        System.out.println("Product you want to find : ");
        System.out.printf("\n\t%8s%50s%58s%28s%18s%28s\n", "ID", "Name", "Author", "Quaility", "Price", "Create At\n");
        for (Book book : list) {
            if (book.getName().toLowerCase().contains(nameIgnoreCase)) {
                System.out.println(InstantUtils.productFomat(book));
                count++;
            }
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        if (count==0){
            System.out.println("Cant find this NAME, please check again!");
        }
        return null;
    }

    @Override
    public void addProduct(Book newBook) {
        newBook.setCreatedAt(Instant.now());
        newBook.setUpdateAt(Instant.now());
        List<Book> list = findAll();
        for (Book book : list) {
            if (book.getName().equalsIgnoreCase(newBook.getName())) {
                if (book.getPrice().equals(newBook.getPrice())) {
                    System.out.println("The product is already in the list. ");
                    if (AppUtils.areYouSure("update")) {
                        book.setQuaility(book.getQuaility() + newBook.getQuaility());
                        CSVUtils.write(PATHPRODUCT, list);
                        System.out.println("Add Product is Success");
                    }
                    return;
                }
            }
        }
        System.out.printf("\n\t%8s%50s%58s%28s%18s%28s\n", "ID", "Name", "Author", "Quaility", "Price", "Create At\n");
        System.out.println(InstantUtils.productFomat(newBook));
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        if (AppUtils.areYouSure("Add new Product")) {
            list.add(newBook);
            CSVUtils.write(PATHPRODUCT, list);
            System.out.println("Add Product is Success");
        }
    }

    @Override
    public void updateProduct(Book newBook) {
        List<Book> list = findAll();
        for (Book book : list) {
            if (newBook.getID().equals(book.getID())) {
                System.out.println("Product after to change:");
                System.out.println(InstantUtils.productFomat(book));
                System.out.println("Product after to change");
                System.out.printf("\n\t%8s%50s%58s%28s%18s%28s\n", "ID", "Name", "Author", "Quaility", "Price", "Create At\n");
                System.out.println(InstantUtils.productFomat(newBook));
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

                if (AppUtils.areYouSure("Update")) {
                    book.setName(newBook.getName());
                    book.setAuthor(newBook.getAuthor());
                    book.setQuaility(newBook.getQuaility());
                    book.setPrice(newBook.getPrice());
                    book.setUpdateAt(Instant.now());
                    book.setCreatedAt(newBook.getCreatedAt());
                    System.out.println("Update succesful.");
                    CSVUtils.write(PATHPRODUCT, list);
                    return;
                }else {
                    System.out.println("Update Book in List is cancel");
                    return;
                }
            }
        }
        System.out.println("Can't find this product in product list");
        // thêm xác nh?n có thêm vào danh sách
    }

    @Override
    public void removeProductByID(Long productID) {
        List<Book> list = findAll();
        for (Book book : list) {
            if (productID.equals(book.getID())) {
                System.out.printf("\n\t%8s%50s%58s%28s%18s%28s\n", "ID", "Name", "Author", "Quaility", "Price", "Create At\n");
                System.out.println(InstantUtils.productFomat(book));
                boolean check = AppUtils.areYouSure("delete");
                if (check) {
                    list.remove(book);
                    CSVUtils.write(PATHPRODUCT, list);
                    System.out.println("Delete is success!");
                    return;
                }else {
                    System.out.println("Delete" + book.getName() + " is cancel");
                    return;
                }
            }
        }
        System.out.println("Can't find this ID, please check again.");
    }

    @Override
    public Book sortQuantityASC() {
        List<Book> list = findAll();
        Collections.sort(list, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o1.getQuaility() - o2.getQuaility();
            }
        });
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.printf("\n\t%8s%50s%58s%28s%18s%28s\n", "ID", "Name", "Author", "Quaility", "Price", "Create At\n");
        for (Book book : list) {
            System.out.println(InstantUtils.productFomat(book));
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        return null;
    }

    @Override
    public Book sortQuantityESC() {
        List<Book> list = findAll();
        Collections.sort(list, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o2.getQuaility() - o1.getQuaility();
            }
        });
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.printf("\n\t%8s%50s%58s%28s%18s%28s\n", "ID", "Name", "Author", "Quaility", "Price", "Create At\n");
        for (Book book : list) {
            System.out.println(InstantUtils.productFomat(book));
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        return null;
    }

    @Override
    public void updateQuaility(long id, int newQuaility) {
        List<Book> list = findAll();
        for (Book book : list) {
            if (book.getID() == id) {
                book.setQuaility(newQuaility);
                CSVUtils.write(PATHPRODUCT, list);
                System.out.println("update quaility is succes.");
                return;
            }
        }
        System.out.println("Can't find this ID, please check again.");
    }

    @Override
    public void findByAuthorName(String authorName) {
        for (Book book : findAll()) {
            if (book.getAuthor().equalsIgnoreCase(authorName)) {
                System.out.println("Product of " + authorName + ": ");
                System.out.println(InstantUtils.productFomat(book));
            }
        }
    }

    @Override
    public Book createProduct() {
        Long ID = System.currentTimeMillis() / 1000;
        System.out.println("Enter Product Name: ");
        String name = AppUtils.inputStringAgain("Product name ");
        System.out.println("Enter Product Author Name: ");
        String author = AppUtils.inputStringAgain("Author name ");
        System.out.println("Enter Product Quaility: ");
        int quaility = AppUtils.inputNumberAgain();
        System.out.println("Enter Price of Product: ");
        Double price = AppUtils.retryParseDouble();
        Instant createAt = Instant.now();
        Instant updateAt = null;
        return new Book(ID, name, author, quaility, price, createAt, updateAt);
    }

    @Override
    public void showListProduct(List<Book> list) {
        int count = 0;
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.printf("\n\t%8s%50s%58s%28s%18s%28s%38s\n", "ID", "Name", "Author", "Quaility", "Price", "Create At", "Update At\n");
        for (Book book : list) {
            System.out.println(InstantUtils.productFomat(book));
            count++;
        }
        if (count == 0) {
            System.out.println("Order list is empty.");
        }else {
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }
    }
}
