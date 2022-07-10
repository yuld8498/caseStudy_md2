package service;

import model.Order;
import model.Product;
import model.User;
import util.AppUtils;
import util.CSVUtils;
import util.InstantUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LoanProductService implements ILoanProduct {
    private static final String PATHPRODUCTS = "src\\data\\OrderItem.csv";
    private static final String PATHLOAN = "src\\data\\OrderItem.csv";
    private static final String PATHREVENUE = "src\\data\\revenue.csv";
    private  static  LoanProductService instance;
    private LoanProductService(){
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
            for (Product product : produceService.findAll()) {
                if (product.getID().equals(id)) {
                    for (Order order : loanList){
                        if (product.getName().equalsIgnoreCase(order.getProductName())){
                            System.out.println("This Item isready in Loan list");
                            return;
                        }
                    }
                    System.out.println(InstantUtils.productFomat(product));
                    if (AppUtils.areYouSure("Add product to loan list")) {
                        Order order = new Order();
                        order.setID(System.currentTimeMillis() / 1000);
                        order.setProductName(product.getName());
                        order.setQuaility(1);
                        order.setPrice(product.getPrice());
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
            for (String s : CSVUtils.read(PATHLOAN)){
                revenueList.add(Order.parseOrder(s));
            }
            for (Order order : revenueList){
                if (order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName())&&order.getNote().equalsIgnoreCase("confirm loan")){
                    isready = false;
                    return;
                }
            }
          for (Order order : loanList){
              int count =0;
              if (order.getNote().equalsIgnoreCase("loan")){
                  count+=1;
                  if (count>2){
                      System.out.println("Loan is less than 3 items.");
                      isready=false;
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
                if (order.getProductName().equalsIgnoreCase(name)&&order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName())&&order.getNote().equalsIgnoreCase("loan")) {
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
        for (Order order : loanList){
            if ((order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName())&&order.getNote().equalsIgnoreCase("loan"))||callUser().getROLE().equalsIgnoreCase("ADMin")){
                System.out.println(InstantUtils.orderFomat(order));
            }
        }
    }

    @Override
    public void confirmLoanList() {
        if (AppUtils.areYouSure("Loan Items")){
            List<Order> orderList = new ArrayList<>(orderItemService.findAllOrder());
            List<Product> productList = new ArrayList<>(orderItemService.findAllProduct());
            int count =0;
            for (Order order : orderList){
                if (order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName())&&order.getNote().equalsIgnoreCase("loan")){
                    for (Product product : productList){
                        if (product.getName().equalsIgnoreCase(order.getProductName())&&product.getPrice().equals(order.getPrice())){
                            product.setQuaility(product.getQuaility()- order.getQuaility());
                        }
                    }
                    order.setNote("Confirm loan");
                    double priceLoan = order.getPrice();
                    order.setPrice(priceLoan*0.02);
                    count++;
                }
            }

            if (count==0){
                System.out.println("Order list is empty, please add product to list.");
            }else {
                System.out.println("Complete!");
            }
            CSVUtils.write(PATHLOAN,orderList);
            CSVUtils.write(PATHPRODUCTS, productList);
        }
    }

    @Override
    public void showLoandinglist() {
        List<Order> list = new ArrayList<>();
        for (String s : CSVUtils.read(PATHREVENUE)){
            list.add(Order.parseOrder(s));
        }
        System.out.println("List product loanding: \n");
        for (Order order : list){
            System.out.println();
            if (order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName())&&order.getNote().equalsIgnoreCase("confirm loan")){
                System.out.println(InstantUtils.orderFomat(order));
            }
        }
    }

    public static User callUser() {
        User user = null;
        List<String> record = CSVUtils.read("src\\data\\login.csv");
        for (String s : record) {
            user = User.parseUser(s);
        }
        return user;
    }
}
