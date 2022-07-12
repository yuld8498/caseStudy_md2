package views;

import model.Book;
import service.ProduceService;
import util.AppUtils;


public class ProductView {
    private static ProductView instance;

    private ProductView() {
    }

    public static ProductView getInstance() {
        if (instance == null)
            instance = new ProductView();
        return instance;
    }

    ProduceService produceService;

    public void menuProduct() {
        produceService = ProduceService.getInstance();
        int chose;
        do {
            System.out.println("==================== PRODUCTS MANAGEMENT ======================");
            System.out.printf("%10s%s", "", "1. Show Products List\n");
            System.out.printf("%10s%s", "", "2. Add a New Product\n");
            System.out.printf("%10s%s", "", "3. Delete a Product By ID\n");
            System.out.printf("%10s%s", "", "4. Update a Product\n");
            System.out.printf("%10s%s", "", "5. Update Quaility of a Product in Products List\n");
            System.out.printf("%10s%s", "", "6. Find a Product By Name\n");
            System.out.printf("%10s%s", "", "7. Find a Product By ID\n");
            System.out.printf("%10s%s", "", "8. Sort Products List for Quaility ASC\n");
            System.out.printf("%10s%s", "", "9. Sort Products List for Quaility ESC\n");
            System.out.printf("%10s%s", "", "10. Back to Menu Admin\n");
            System.out.println("====================================================================");
            chose = AppUtils.choseAgain(1, 10);
            switch (chose) {
                case 1:
                    produceService.showListProduct(produceService.findAll());
                    break;
                case 2:
                    produceService.addProduct(produceService.createProduct());
                    produceService.showListProduct(produceService.findAll());
                    break;
                case 3:
                    System.out.println("Enter Product ID: ");
                    Long ID = AppUtils.retryParseLong();
                    produceService.removeProductByID(ID);
                    break;
                case 4:
                    System.out.println("Enter the product ID you want to update: ");
                    Long id = AppUtils.retryParseLong();
                    for (Book book : produceService.findAll()) {
                        if (book.getID().equals(id)) {
                            Book books = produceService.createProduct();
                            books.setID(id);
                            produceService.updateProduct(books);
                            return;
                        }
                    }
                    System.out.println("Can't find this ID, please check again.");
                    break;
                case 5:
                    System.out.println("Enter the product ID you want to update quaility: ");
                    Long Id = AppUtils.retryParseLong();
                    System.out.println("Enter new Quaility: ");
                    int newQuaility = AppUtils.inputNumberAgain();
                    produceService.updateQuaility(Id, newQuaility);
                    break;
                case 6:
                    System.out.println("Enter the product NAME you want to find:");
                    String name = AppUtils.inputStringAgain("Name ");
                    produceService.findProductbyName(name);
                    break;
                case 7:
                    System.out.println("Enter the product ID you want to find:");
                    Long iD = AppUtils.retryParseLong();
                    produceService.findProductByID(iD);
                    break;
                case 8:
                    produceService.sortQuantityASC();
                    break;
                case 9:
                    produceService.sortQuantityESC();
                    break;
            }
        } while (chose != 10);
    }

//    public static Product createProduct() {
//        Long ID = System.currentTimeMillis() / 1000;
//        System.out.println("Enter Product Name: ");
//        String name = AppUtils.inputStringAgain("Product name ");
//        System.out.println("Enter Product Author Name: ");
//        String author = AppUtils.inputStringAgain("Author name ");
//        System.out.println("Enter Product Quaility: ");
//        int quaility = AppUtils.inputNumberAgain();
//        System.out.println("Enter Price of Product: ");
//        Double price = AppUtils.retryParseDouble();
//        Instant createAt = Instant.now();
//        Instant updateAt = null;
//        return new Product(ID, name, author, quaility, price, createAt, updateAt);
//    }
//    public static void showListProduct(List<Product> list) {
//        System.out.printf("%-58s%-78s%-58s%-48s%-48s%-48s\n","ID","Name","Author","Quaility","Price","Create At\n");
//        for (Product product : list) {
//            System.out.println(InstantUtils.productFomat(product));
//        }
//    }
}
