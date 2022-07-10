package service;

import model.Product;

import java.util.List;

public interface IProduceService {
    List<Product> findAll();

    Product findProductByID(Long id);

    Product findProductbyName(String name);

    void addProduct(Product product);

    void updateProduct(Product newProduct);

    void removeProductByID(Long productID);

    Product sortQuantityASC();

    Product sortQuantityESC();

    void updateQuaility(long id, int newQuaility);

    void showListProduct(List<Product> list);

    Product createProduct();

    void findByAuthorName(String authorName);
}
