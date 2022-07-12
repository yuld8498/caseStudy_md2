package service;

import model.Order;
import model.Book;
import model.User;
import util.AppUtils;
import util.CSVUtils;
import util.InstantUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LoanProductService implements ILoanProduct {
    private static final String PATHPRODUCTS = "data\\product.csv";
    private static final String PATHLOAN = "data\\OrderItem.csv";
    private static final String PATHREVENUE = "data\\revenue.csv";
    private static LoanProductService instance;

    private LoanProductService() {
    }

    public static LoanProductService getInstance() {
        if (instance == null)
            instance = new LoanProductService();
        return instance;
    }

    ProduceService produceService = ProduceService.getInstance();
    OrderItemService orderItemService = OrderItemService.getInstance();

    @Override
    public void loanProducts() {
        List<Order> loanList = new ArrayList<>();
        List<String> orderList = CSVUtils.read(PATHLOAN);
        for (String s : orderList) {
            loanList.add(Order.parseOrder(s));
        }
        boolean isready;
        Long id;
        do {
            System.out.print("Enter ID product want to loan: ");
            id = AppUtils.retryParseLong();
            for (Book book : produceService.findAll()) {
                if (book.getID().equals(id)) {
                    for (Order order : loanList) {
                        if (book.getName().equalsIgnoreCase(order.getProductName()) && order.getNote().equalsIgnoreCase("loan") || book.getName().equalsIgnoreCase(order.getProductName()) && order.getNote().equalsIgnoreCase("confirm loan")) {
                            System.out.println("This Item isready in Loan list");
                            return;
                        }
                    }
                    System.out.println(InstantUtils.productFomat(book));
                    if (AppUtils.areYouSure("Add product to loan list")) {
                        Order order = new Order();
                        order.setID(System.currentTimeMillis() / 1000);
                        order.setProductName(book.getName());
                        order.setQuaility(1);
                        order.setPrice(book.getPrice());
                        order.setUserNameOrder(callUser().getUserName());
                        order.setAddressOfUser(callUser().getAddress());
                        order.setPhoneNumberOfUser(callUser().getMobile());
                        order.setCreateAt(Instant.now());
                        order.setNote("Loan");
                        loanList.add(order);
                        CSVUtils.write(PATHLOAN, loanList);
                        System.out.println("Add product is succes.");
                        return;
                    }
                }
            }
            System.out.println("Cant find this ID, please check again.");
            isready = AppUtils.areYouSure("Add more Product");
            List<Order> revenueList = new ArrayList<>();
            for (String s : CSVUtils.read(PATHLOAN)) {
                revenueList.add(Order.parseOrder(s));
            }
            for (Order order : revenueList) {
                if (order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName()) && order.getNote().equalsIgnoreCase("confirm loan")) {
                    isready = false;
                    return;
                }
            }
            for (Order order : loanList) {
                int count = 0;
                if (order.getNote().equalsIgnoreCase("loan")) {
                    count += 1;
                    if (count > 2) {
                        System.out.println("Loan is less than 3 items.");
                        isready = false;
                        return;
                    }
                }
            }
        } while (isready);
    }

    @Override
    public void deleteItemInLoanList() {
        List<String> strings = CSVUtils.read(PATHLOAN);
        List<Order> loanList = new ArrayList<>();
        boolean repeat;
        for (String s : strings) {
            loanList.add(Order.parseOrder(s));
        }
        do {
            System.out.println("Enter Product name want to delete: ");
            String name = AppUtils.inputStringAgain("Product name");
            for (Order order : loanList) {
                if (order.getProductName().equalsIgnoreCase(name) && order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName()) && order.getNote().equalsIgnoreCase("loan")) {
                    System.out.println(InstantUtils.orderFomat(order));
                    if (AppUtils.areYouSure("delete this product")) {
                        loanList.remove(order);
                        CSVUtils.write(PATHLOAN, loanList);
                    }
                }
            }
            repeat = AppUtils.areYouSure("Delete more");
        } while (repeat);
    }

    @Override
    public void showLoanList() {
        List<String> strings = CSVUtils.read(PATHLOAN);
        List<Order> loanList = new ArrayList<>();
        for (String s : strings) {
            loanList.add(Order.parseOrder(s));
        }
        int count = 0;
        for (Order order : loanList) {
            if ((order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName()) && order.getNote().equalsIgnoreCase("loan")) || callUser().getROLE().equalsIgnoreCase("ADMin")) {
                System.out.println(InstantUtils.orderFomat(order));
                count++;
            }
        }
        if (count == 0) {
            System.err.println("Loan list is empty.");
        }
    }

    @Override
    public void confirmLoanList() {
        if (AppUtils.areYouSure("Loan Items")) {
            List<Order> orderList = new ArrayList<>(orderItemService.findAllOrder());
            List<Book> bookList = new ArrayList<>(orderItemService.findAllProduct());
            int count = 0;
            for (Order order : orderList) {
                if (order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName()) && order.getNote().equalsIgnoreCase("loan")) {
                    for (Book book : bookList) {
                        if (book.getName().equalsIgnoreCase(order.getProductName()) && book.getPrice().equals(order.getPrice())) {
                            book.setQuaility(book.getQuaility() - order.getQuaility());
                        }
                    }
                    order.setNote("Confirm loan");
                    Double priceLoan = order.getPrice();
                    order.setPrice(priceLoan * 0.02);
                    count++;
                }
            }

            if (count == 0) {
                System.err.println("Order list is empty, please add product to list.");
            } else {
                System.out.println("Complete!");
            }
            CSVUtils.write(PATHLOAN, orderList);
            CSVUtils.write(PATHPRODUCTS, bookList);
        }
    }

    @Override
    public void showLoandinglist() {
        List<Order> list = new ArrayList<>();
        int count = 0;
        for (String s : CSVUtils.read(PATHREVENUE)) {
            list.add(Order.parseOrder(s));
        }
        System.out.println("List product loanding: \n");
        for (Order order : list) {
            if (order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName()) && order.getNote().equalsIgnoreCase("confirm loan")) {
                System.out.println(InstantUtils.orderFomat(order));
                count++;
            }
        }
        if (count == 0) {
            System.err.println("Loanding is empty");
        }
    }

    public static User callUser() {
        User user = null;
        List<String> record = CSVUtils.read("data\\login.csv");
        for (String s : record) {
            user = User.parseUser(s);
        }
        return user;
    }
}
