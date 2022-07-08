package util;

import model.Order;
import model.Product;
import model.User;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class InstantUtils {
    private static final String PATTERN_FORMAT = "HH:mm dd-MM-yyyy";
//    public static String instantToString(Instant instant) {
//        return instantToString(instant, null);
//    }
    public static String instantToString(Instant instant){
        return instantToString(instant, null);
    }

    public static String instantToString(Instant instant, String patternFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternFormat != null ? patternFormat : PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }


    public static String doubleToVND(double value) {
        String patternVND = ",### VND";
        DecimalFormat decimalFormat = new DecimalFormat(patternVND);
        return decimalFormat.format(value);
    }
    public static String productFomat(Product product){
        return String.format("%-48d%-78s%-48s%-48d%-48.2f%-48s",
                product.getID(),
                product.getName(),
                product.getAuthor(),
                product.getQuaility(),
                product.getPrice(),
                InstantUtils.instantToString(product.getCreatedAt()));
//                InstantUtils.instantToString(product.getUpdateAt()));
    }
    public static String orderFomat(Order order){
        return String.format("%-48d%-78s%-24d%-24.2f%-48s%-28s%-28s%-28s",
                order.getID(),
                order.getProductName(),
                order.getQuaility(),
                order.getPrice(),
                order.getUserNameOrder(),
                order.getPhoneNumberOfUser(),
                order.getAddressOfUser(),
                InstantUtils.instantToString(order.getCreateAt()));
//                InstantUtils.instantToString(product.getUpdateAt()));
    }
    public static String userFormat(User user){
        return String.format("%10d%20s%30s%26s%26s%38s%38s%38s\n",
                user.getID(),
                user.getUserName(),
                user.getPassword(),
                user.getFullName(),
                user.getMobile(),
                user.getEmail(),
                user.getAddress(),
                InstantUtils.instantToString(user.getCreatedAt()));
    }
}
