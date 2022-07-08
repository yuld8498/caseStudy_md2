package views;

import util.AppUtils;

public class AdminView {
    ProductView productView;
    UserViewByAdmin userViewByAdmin;
    public void menuAdmin(){
        productView = ProductView.getInstance();
        userViewByAdmin = new UserViewByAdmin();
        int chose;
        do {
            System.out.println("==================== ADMIN ======================");
            System.out.printf("%10s%s","","1. Management Product\n");
            System.out.printf("%10s%s","","2. Managerment User\n");
            System.out.printf("%10s%s","","3. Managerment Order\n");
            System.out.printf("%10s%s","","4. Log out Admin\n");
             chose = AppUtils.choseAgain(1,4);
            switch (chose){
                case 1:
                    productView.menuProduct();
                    break;
                case 2:
                    userViewByAdmin.menuUser();
                    break;
                case 3:
                case 4:
            }
        }while (chose!=4);
    }
}
