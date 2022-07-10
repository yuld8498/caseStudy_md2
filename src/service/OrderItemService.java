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

public class OrderItemService implements IOrderItemService {
    public final static String PATHREVENUE = "src/data/revenue.csv";
    public final static String PATHPRODUCTS = "src/data/product.csv";
    public final static String PATHORDER = "src/data/OrderItem.csv";
    private static OrderItemService instance;
    ProduceService produceService = ProduceService.getInstance();
    private OrderItemService(){
    }

    public static OrderItemService getInstance() {
        if (instance == null)
            instance = new OrderItemService();
        return instance;
    }

    @Override
    public List<Product> findAllProduct() {
        List<Product> products = new ArrayList<>();
        List<String> record = CSVUtils.read(PATHPRODUCTS);
        for (String string : record) {
            products.add(Product.parseProduct(string));
        }
        return products;
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
        for (Product product : findAllProduct()){
            if (product.getName().equalsIgnoreCase(name)){
                System.out.println(InstantUtils.productFomat(product));
            }
        }
    }

    @Override
    public Order oderByID(Long ID) {
        List<Order> list = new ArrayList<>();
        List<String> record = CSVUtils.read(PATHORDER);
        for (String s : record) {
            list.add(Order.parseOrder(s));
        }
        Product newProduct = null;
        for (Product product : findAllProduct()) {
            if (product.getID().equals(ID)) {
                System.out.println(InstantUtils.productFomat(product));
                System.out.println("Enter quaility");
                int quaility = AppUtils.choseAgain(1,product.getQuaility());
                newProduct = product;
                newProduct.setQuaility(quaility);
                if (AppUtils.areYouSure("Order this")){
                    Order order = new Order();
                    order.setID(System.currentTimeMillis()/1000);
                    order.setUserNameOrder(callUser().getUserName());
                    order.setPrice(newProduct.getPrice());
                    order.setQuaility(newProduct.getQuaility());
                    order.setProductName(newProduct.getName());
                    order.setAddressOfUser(callUser().getAddress());
                    order.setPhoneNumberOfUser(callUser().getMobile());
                    order.setCreateAt(Instant.now());
                    order.setNote("Order");
                    list.add(order);
                    CSVUtils.write(PATHORDER,list);
                    return order;
                }
            }
        }
        System.out.println("Can't find this ID, please check again.");
        return null;
    }
    public static User callUser(){
        User user = null ;
        List<String> record = CSVUtils.read("src\\data\\login.csv");
        for (String s : record) {
            user = User.parseUser(s);
        }
        return user;
    }

    @Override
    public void showOrderList() {
        for (Order order : findAllOrder()) {
            if ((order.getNote().equalsIgnoreCase("order")&&order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName()))||callUser().getROLE().equalsIgnoreCase("Admin")){
                System.out.println(order.toString());
            }
        }
    }

    @Override
    public void deleteProductInListByID(Long ID) {
        List<Order> list = new ArrayList<>(findAllOrder());
        for (Order order : list) {
            if (order.getID().equals(ID)&& order.getNote().equalsIgnoreCase("order")) {
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
        for (Order order : list){
            if (order.getNote().equalsIgnoreCase("loan")){
                newlist.add(order);
            }
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
        List<Product> productList = new ArrayList<>(findAllProduct());
        int count =0;
        for (Order order : orderList){
            if (order.getUserNameOrder().equalsIgnoreCase(callUser().getUserName())&&order.getNote().equalsIgnoreCase("order")){
                for (Product product : productList){
                    if (product.getName().equalsIgnoreCase(order.getProductName())&&product.getPrice().equals(order.getPrice())){
                        product.setQuaility(product.getQuaility()- order.getQuaility());
                    }
                }
                order.setNote("Confirm order");
                count++;
            }
        }

        if (count==0){
            System.out.println("Order list is empty, please add product to list.");
        }else {
            System.out.println("Complete!");
        }
        CSVUtils.write(PATHORDER,orderList);
        CSVUtils.write(PATHPRODUCTS, productList);
    }
    @Override
    public void showRevenue(){
        List<String> list = new ArrayList<>(CSVUtils.read(PATHREVENUE));
        List<Order> orderList = new ArrayList<>();
        for (String strings : list){
            orderList.add(Order.parseOrder(strings));
        }
        for (Order order : orderList){
            System.out.println(InstantUtils.orderFomat(order));
        }
    }
    public List<Order> cashier(String UserName){
        List<Order> orderList = new ArrayList<>();
        for (String s : CSVUtils.read(PATHREVENUE)){
            orderList.add(Order.parseOrder(s));
        }
        List<Order> list = new ArrayList<>();
        List<Order> listOrderBeforeCashier = new ArrayList<>(findAllOrder());
        for (Order order : listOrderBeforeCashier){
            if ((order.getNote().equalsIgnoreCase("confirm order")||order.getNote().equalsIgnoreCase("confirm loan"))&&order.getUserNameOrder().equalsIgnoreCase(UserName)){
                list.add(order);
                orderList.add(order);
            }
        }
        for (Order order : listOrderBeforeCashier){
            if (order.getNote().equalsIgnoreCase("confirm order")||order.getNote().equalsIgnoreCase("confirm loan")){
                listOrderBeforeCashier.remove(order);
            }
        }
        CSVUtils.write(PATHORDER,listOrderBeforeCashier);
        if (list.size()==0){
            System.out.println("Confirm order is empty, please check order list or User cashier.");
        }else {
            CSVUtils.write(PATHREVENUE,list);
        }
        return list;
    }
    public void returnProduct(){

    }
}
