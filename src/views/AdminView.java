package views;

import model.Order;
import model.User;
import service.OrderItemService;
import util.AppUtils;
import util.CSVUtils;

import java.util.ArrayList;
import java.util.List;

public class AdminView {
    ProductView productView;
    UserViewByAdmin userViewByAdmin;
    OrderItemService orderItemService;

    public void menuAdmin() {
        orderItemService = OrderItemService.getInstance();
        productView = ProductView.getInstance();
        userViewByAdmin = new UserViewByAdmin();
        OrderItemView orderItemView = new OrderItemView();
        int chose;
        do {
            System.out.println("========================= ADMIN ========================");
            System.out.printf("%10s%s", "", "1. Management Product\n");
            System.out.printf("%10s%s", "", "2. Managerment User\n");
            System.out.printf("%10s%s", "", "3. Managerment Order\n");
            System.out.printf("%10s%s", "", "4. Show revenue\n");
            System.out.printf("%10s%s", "", "5. Cashier\n");
            System.out.printf("%10s%s", "", "6. Return Product\n");
            System.out.printf("%10s%s", "", "7. Log out Admin\n");
            System.out.println("========================================================");
            chose = AppUtils.choseAgain(1, 7);
            switch (chose) {
                case 1:
                    productView.menuProduct();
                    break;
                case 2:
                    userViewByAdmin.menuUser();
                    break;
                case 3:
                    orderItemView.menuOrder();
                    break;
                case 4:
                    orderItemService.showRevenue();
                    break;
                case 5:
                    boolean repeat = false;
                    do {
                        System.out.println("Enter User Name: ");
                        String userName = AppUtils.inputStringAgain("userName");
                        if (AppUtils.areYouSure("Print bill")) {
                            List<Order> list = orderItemService.cashier(userName);
                            if (list.size() != 0) {
                                AppUtils.bill(list, userName);
                            } else {
                                repeat = AppUtils.areYouSure("Cashier again");
                            }
                        }
                        List<Order> listOrderBeforeCashier = new ArrayList<>();
                        for (Order order : orderItemService.findAllOrder()) {
                            if ((order.getNote().equalsIgnoreCase("confirm order") && order.getUserNameOrder().equalsIgnoreCase(userName)) || (order.getNote().equalsIgnoreCase("confirm loan") && order.getUserNameOrder().equalsIgnoreCase(userName))) {
                                continue;
                            } else {
                                listOrderBeforeCashier.add(order);
                            }
                        }
                        CSVUtils.write("data\\OrderItem.csv", listOrderBeforeCashier);
                    } while (repeat);
                    break;
                case 6:
                    orderItemService.returnProduct();
                    break;
            }
        } while (chose != 7);
    }
}
