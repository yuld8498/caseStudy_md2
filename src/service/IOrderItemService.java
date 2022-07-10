package service;

import model.Order;
import model.Product;

import java.util.List;

public interface IOrderItemService {
    List<Product> findAllProduct();
    List<Order> findAllOrder();
    void findByName(String name);
    Order oderByID(Long ID);
    void showOrderList();
    void deleteProductInListByID(Long ID);
    void clearOrderList();
    void confirmOrder();
    void showRevenue();
     List<Order> cashier(String UserName);
}
