package views;

import model.Order;
import model.Product;
import model.User;
import service.OrderItemService;
import service.ProduceService;
import util.AppUtils;
import util.InstantUtils;

import java.util.List;
import java.util.Scanner;

public class OrderItemView {
    private static final Scanner SCANNER = new Scanner(System.in);
    OrderItemService orderItemService;
    public void menuOrder(){
        orderItemService = OrderItemService.getInstance();
        ProduceService produceService = ProduceService.getInstance();
        int chose;
        do {
            System.out.println("==================== Order Item MANAGEMENT ======================");
            System.out.printf("%10s%s", "", "1. Show Products List\n");
            System.out.printf("%10s%s", "", "2. Show Order List\n");
            System.out.printf("%10s%s", "", "3. Find product by name\n");
            System.out.printf("%10s%s", "", "4. Order Product by ID\n");
            System.out.printf("%10s%s", "", "5. Delete a Product in Order By ID\n");
            System.out.printf("%10s%s", "", "6. Clear Order List\n");
            System.out.printf("%10s%s", "", "7. Confirm Order\n");
            System.out.printf("%10s%s", "", "8. Back to Menu\n");
            System.out.println("Enter you chose: ");
            chose = AppUtils.choseAgain(1,8);
            switch (chose){
                case 1:
                    produceService.showListProduct(orderItemService.findAllProduct());
                    break;
                case 2:
                    orderItemService.showOrderList();
                    break;
                case 3:
                    System.out.println("Enter Name product want to find: ");
                    String name = AppUtils.inputStringAgain("Product name");
                    orderItemService.findByName(name);
                    break;
                case 4:
                    System.out.println("Enter Product ID: ");
                    Long id = AppUtils.retryParseLong();
                    orderItemService.oderByID(id);
                    break;
                case 5:
                    System.out.println("Enter Product ID: ");
                    Long ID = AppUtils.retryParseLong();
                    orderItemService.deleteProductInListByID(ID);
                    break;
                case 6:
                    orderItemService.clearOrderList();
                    break;
                case 7:
                        orderItemService.confirmOrder();
                    break;
            }
        }while (chose!=8);
    }

//    public static void showListProduct(List<Product> list) {
//        System.out.printf("%-58s%-78s%-58s%-48s%-48s%-48s\n","ID","Name","Author","Quaility","Price","Create At\n");
//        for (Product product : list) {
//            System.out.println(InstantUtils.productFomat(product));
//        }
//    }
}
