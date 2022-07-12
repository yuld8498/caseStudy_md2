package service;

import model.Book;

import java.util.List;

public interface IProduceService {
    List<Book> findAll();

    Book findProductByID(Long id);

    Book findProductbyName(String name);

    void addProduct(Book book);

    void updateProduct(Book newBook);

    void removeProductByID(Long productID);

    Book sortQuantityASC();

    Book sortQuantityESC();

    void updateQuaility(long id, int newQuaility);

    void showListProduct(List<Book> list);

    Book createProduct();

    void findByAuthorName(String authorName);
}
