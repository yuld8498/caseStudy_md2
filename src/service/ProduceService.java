package service;

import model.Product;
import util.AppUtils;
import util.CSVUtils;
import util.InstantUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProduceService implements IProduceService {
    public final static String PATHPRODUCT = "src/data/product.csv";
    private static ProduceService instance;
    private ProduceService(){

    }

    public static  ProduceService getInstance() {
        if (instance == null){
            instance = new ProduceService();
            return instance;
        }
        return instance;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        List<String> record = CSVUtils.read(PATHPRODUCT);
        for (String string : record) {
            products.add(Product.parseProduct(string));
        }
        return products;
    }

    @Override
    public Product findProductByID(Long id) {
        List<Product> list = new ArrayList<>(findAll());
        for (Product product : list) {
            if (product.getID().equals(id)) {
                System.out.println("Product you want to find : ");
                System.out.println(InstantUtils.productFomat(product));
                return product;
            }
        }
        System.out.println("Cant find this ID, please check again!");
        return null;
    }

    @Override
    public Product findProductbyName(String name) {
        List<Product> list = new ArrayList<>(findAll());
        for (Product product : list) {
            if (product.getName().equalsIgnoreCase(name)) {
                System.out.println("Product you want to find : ");
                System.out.println(InstantUtils.productFomat(product));
                return product;
            }
        }
        System.out.println("Cant find this ID, please check again!");
        return null;
    }

    @Override
    public void addProduct(Product newProduct) {
        newProduct.setCreatedAt(Instant.now());
        List<Product> list = findAll();
        for (Product product : list) {
            if (product.getName().equalsIgnoreCase(newProduct.getName())) {
                if (product.getPrice().equals(newProduct.getPrice())) {
                    System.out.println("The product is already in the list. ");
                    if (AppUtils.areYouSure("update")) {
                        product.setQuaility(product.getQuaility() + newProduct.getQuaility());
                        CSVUtils.write(PATHPRODUCT, list);
                        System.out.println("Add Product is Success");
                    }
                    return;
                }
            }
        }
        if (AppUtils.areYouSure("Add new Product")) {
            list.add(newProduct);
            CSVUtils.write(PATHPRODUCT, list);
            System.out.println("Add Product is Success");
        }
    }

    @Override
    public void updateProduct(Product newProduct) {
        newProduct.setCreatedAt(Instant.now());
        List<Product> list = findAll();
        for (Product product : list) {
            if (newProduct.getID().equals(product.getID())) {
                System.out.println("Product after to change:");
                System.out.println(InstantUtils.productFomat(product));
                System.out.println("Product after to change");
                System.out.println(InstantUtils.productFomat(newProduct));
                if (AppUtils.areYouSure("Update")) {
                    product.setName(newProduct.getName());
                    product.setAuthor(newProduct.getAuthor());
                    product.setQuaility(newProduct.getQuaility());
                    product.setPrice(newProduct.getPrice());
                    product.setUpdateAt(Instant.now());
                    System.out.println("Update succesful.");
                    CSVUtils.write(PATHPRODUCT, list);
                    return;
                }
            }
        }
        System.out.println("Can't find this product in product list");
        // thêm xác nh?n có thêm vào danh sách
    }

    @Override
    public void removeProductByID(Long productID) {
        List<Product> list = findAll();
        for (Product product : list) {
            if (productID.equals(product.getID())) {
                System.out.println(InstantUtils.productFomat(product));
                boolean check = AppUtils.areYouSure("delete");
                if (check) {
                    list.remove(product);
                    CSVUtils.write(PATHPRODUCT, list);
                    System.out.println("Delete is success!");
                    return;
                }
            }
        }
        System.out.println("Can't find this ID, please check again.");
    }

    @Override
    public Product sortQuantityASC() {
        List<Product> list = findAll();
        Collections.sort(list, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getQuaility() - o2.getQuaility();
            }
        });
        System.out.printf("%-58s%-78s%-58s%-48s%-48s%-48s\n","ID","Name","Author","Quaility","Price","Create At\n");
        for (Product product : list) {
            System.out.println(InstantUtils.productFomat(product));
        }
        return null;
    }

    @Override
    public Product sortQuantityESC() {
        List<Product> list = findAll();
        Collections.sort(list, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o2.getQuaility() - o1.getQuaility();
            }
        });
        System.out.printf("%-58s%-78s%-58s%-48s%-48s%-48s\n","ID","Name","Author","Quaility","Price","Create At\n");
        for (Product product : list) {
            System.out.println(InstantUtils.productFomat(product));
        }
        return null;
    }

    @Override
    public void updateQuaility(long id, int newQuaility) {
        List<Product> list = findAll();
        for (Product product : list) {
            if (product.getID() == id) {
                product.setQuaility(newQuaility);
                CSVUtils.write(PATHPRODUCT,list);
                System.out.println("update quaility is succes.");
                return;
            }
        }
        System.out.println("Can't find this ID, please check again.");
    }

    @Override
    public void findByAuthorName(String authorName) {
        for (Product product : findAll()){
            if (product.getAuthor().equalsIgnoreCase(authorName)){
                System.out.println("Product of " + authorName +": ");
                System.out.println(InstantUtils.productFomat(product));
            }
        }
    }
}
