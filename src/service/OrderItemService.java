package service;

import model.Order;
import model.Book;
import model.User;
import util.AppUtils;
import util.CSVUtils;
import util.InstantUtils;
import views.ProductView;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderItemService implements IOrderItemService {
    public final static String PATHREVENUE = "data/revenue.csv";
    public final static String PATHPRODUCTS = "data/product.csv";
    public final static String PATHORDER = "data/OrderItem.csv";
    private static OrderItemService instance;
//    ProduceService produceService = ProduceService.getInstance();

    private OrderItemService() {
    }

    public static OrderItemService getInstance() {
        if (instance == null)
            instance = new OrderItemService();
        return instance;
    }

    @Override
    public List<Book> findAllProduct() {
        List<Book> books = new ArrayList<>();
        List<String> record = CSVUtils.read(PATHPRODUCTS);
        for (String string : record) {
            books.add(Book.parseProduct(string));
        }
        return books;
    }

    @Override
    public List<Order> findAllOrder() {
        List<Order> orderList = new ArrayList<>();
        List<String> record = CSVUtils.read(PATHORDER);
        for (String string : record) {
            orderList.add(Order.parseOrder(string));
        }
        return orderList;
    }

    @Override
    public void findByName(String name) {
        int count = 0;
        String nameIgnoreCase = name.toLowerCase();
        List<Book> list = findAllProduct();
        System.out.println("Product you want to find : ");
        System.out.printf("\n\t%8s%50s%58s%28s%18s%28s\n", "ID", "Name", "Author", "Quaility", "Price", "Create At\n");
        for (Book book : list) {
            if (book.getName().toLowerCase().contains(nameIgnoreCase)) {
                System.out.println(InstantUtils.productFomat(book));
                count++;
            }
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        if (count == 0) {
            System.out.println("Cant find this NAME, please check again!");
        }
    }

    @Override
    public Order oderByID(Long ID) {
        List<Order> list = new ArrayList<>();
        List<String> record = CSVUtils.read(PATHORDER);
        for (String s : record) {
            list.add(Order.parseOrder(s));
        }
        Book newBook;
        for (Book book : findAllProduct()) {
            if (book.getID().equals(ID)) {
                System.out.println(InstantUtils.productFomat(book));
                System.out.println("Enter quaility");
                int quaility = AppUtils.choseAgain(1, book.getQuaility());
                newBook = book;
                newBook.setQuaility(quaility);
                if (AppUtils.areYouSure("Order this")) {
                    Order order = new Order();
                    order.setID(System.currentTimeMillis() / 1000);
                    order.setUserNameOrder(callUser().getUserName());
                    order.setPrice(newBook.getPrice());
                    order.setQuaility(newBook.getQuaility());
                    order.setProductName(newBook.getName());
                    order.setAddressOfUser(callUser().getAddress());
                    order.setPhoneNumberOfUser(callUser().getMobile());
                    order.setCreateAt(Instant.now());
                    order.setNote("Order");
                    list.add(order);
                    CSVUtils.write(PATHORDER, list);
                    return order;
                }
            }
        }
        System.out.println("Can't find this ID, please check again.");
        return null;
    }

    public static User callUser() {
        User user = null;
        List<String> record = CSVUtils.read("data\\login.csv");
        for (String s : record) {
            user = User.parseUser(s);
        }
        return user;
    }

    @Override
    public void showOrderList() {
        int count = 0;
        for (Order order : findAllOrder()) {
            if ((order.getNote().equalsIgnoreCase("order") && order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName())) || callUser().getROLE().equalsIgnoreCase("Admin")) {
                System.out.println(InstantUtils.orderFomat(order));
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Order list is empty.");
        } else {
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }
    }

    @Override
    public void deleteProductInListByID(Long ID) {
        List<Order> list = new ArrayList<>(findAllOrder());
        for (Order order : list) {
            if (order.getID().equals(ID) && order.getNote().equalsIgnoreCase("order")) {
                System.out.println(InstantUtils.orderFomat(order));
                if (AppUtils.areYouSure("Delete product in order list")) {
                    list.remove(order);
                    CSVUtils.write(PATHORDER, list);
                    System.out.println("Delete is complete.");
                    return;
                }
            }
        }
        System.out.println("Can't find this ID, please check again.");
    }


    @Override
    public void clearOrderList() {
        List<Order> newlist = new ArrayList<>();
        List<Order> list = new ArrayList<>(findAllOrder());
        for (Order order : list) {
            if (callUser().getUserName().equalsIgnoreCase(order.getUserNameOrder()) && order.getNote().equalsIgnoreCase("order")) {
                continue;
            }
            newlist.add(order);
        }
        if (AppUtils.areYouSure("Clear order list")) {
            CSVUtils.write(PATHORDER, newlist);
            System.out.println("List order is clear.");
        } else {
            System.out.println("Clear order list is cancel.");
        }
    }

    @Override
    public void confirmOrder() {
        List<Order> orderList = new ArrayList<>(findAllOrder());
        List<Book> bookList = new ArrayList<>(findAllProduct());
        int count = 0;
        for (Order order : orderList) {
            if (order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName()) && order.getNote().equalsIgnoreCase("order")) {
                for (Book book : bookList) {
                    if (book.getName().equalsIgnoreCase(order.getProductName()) && book.getPrice().equals(order.getPrice())) {
                        book.setQuaility(book.getQuaility() - order.getQuaility());
                    }
                }
                order.setNote("Confirm order");
                count++;
            }
        }

        if (count == 0) {
            System.out.println("Order list is empty, please add product to list.");
        } else {
            System.out.println("Complete!");
        }
        CSVUtils.write(PATHORDER, orderList);
        CSVUtils.write(PATHPRODUCTS, bookList);
    }

    @Override
    public void showRevenue() {
        List<String> list = new ArrayList<>(CSVUtils.read(PATHREVENUE));
        List<Order> orderList = new ArrayList<>();
        for (String strings : list) {
            orderList.add(Order.parseOrder(strings));
        }
        for (Order order : orderList) {
            System.out.println(InstantUtils.orderFomat(order));
        }
    }

    public List<Order> cashier(String UserName) {
        List<Order> list = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();
        for (String s : CSVUtils.read(PATHREVENUE)) {
            orderList.add(Order.parseOrder(s));
        }
        for (Order order : findAllOrder()) {
            if ((order.getNote().equalsIgnoreCase("confirm order") && order.getUserNameOrder().equalsIgnoreCase(UserName)) || (order.getNote().equalsIgnoreCase("confirm loan") && order.getUserNameOrder().equalsIgnoreCase(UserName))) {
                list.add(order);
                orderList.add(order);
            }
        }
        CSVUtils.write(PATHREVENUE, orderList);
        if (list.size() == 0) {
            System.out.println("Confirm order is empty, please check order list or User cashier.");
        }
        return list;
    }

    public void returnProduct() {
        System.out.println("Enter User want to return Books: ");
        String userName = AppUtils.inputStringAgain("userName");
        System.out.println("Enter name of book want to return: ");
        String bookName = AppUtils.inputStringAgain("name of book");
        List<Order> list = new ArrayList<>();
        List<Book> books = new ArrayList<>(findAllProduct());
        for (String s : CSVUtils.read(PATHREVENUE)) {
            list.add(Order.parseOrder(s));
        }
        int bookname = 0;
        int count = 0;
        int name = 0;
        for (Order order : list) {
            if (order.getUserNameOrder().equalsIgnoreCase(userName) && order.getProductName().equalsIgnoreCase(bookName) && order.getNote().equalsIgnoreCase("Confirm loan")) {
                bookname++;
                name++;
                for (Book book : books) {
                    if (book.getName().equalsIgnoreCase(order.getProductName()) && book.getPrice().equals(order.getPrice() / 0.02)) {
                        book.setQuaility(book.getQuaility() + 1);
                        count++;
                        if ((InstantUtils.howLong(order.getCreateAt(), Instant.now())) > 7) {
                            long money = InstantUtils.howLong(order.getCreateAt(), Instant.now()) * 2000;
                            System.out.println("If you return the book late, you will be fined, late day penalty (2000vnd/Day ).");
                            System.out.println("Fine amount: " + InstantUtils.howLong(order.getCreateAt(), Instant.now()) * 2000);
                            order.setPrice(order.getPrice() + money);
                        }
                    }
                }
                order.setNote("returned");
            }
        }
        if (count == 0) {
            if (bookname == 0) {
                System.out.println("Name of book input is wrong.");
            }
            if (name == 0) {
                System.out.println("user Name input is wrong");
            }
            System.out.println("User returned all loan product.");
        } else {
            System.out.println("Loan product is complete.");
        }
        CSVUtils.write(PATHREVENUE, list);
        CSVUtils.write(PATHPRODUCTS, books);
    }
}
