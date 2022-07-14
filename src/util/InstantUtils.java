package util;

import model.Order;
import model.Book;
import model.User;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class InstantUtils {
    private static final String PATTERN_FORMAT = "HH:mm dd-MM-yyyy";

    //    public static String instantToString(Instant instant) {
//        return instantToString(instant, null);
//    }
    public static String instantToString(Instant instant) {
        return instantToString(instant, PATTERN_FORMAT);
    }

    public static String instantToString(Instant instant, String patternFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternFormat != null ? patternFormat : PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }


    public static String doubleToVND(double value) {
        String patternVND = "###,###,### VND";
        DecimalFormat decimalFormat = new DecimalFormat(patternVND);
        return decimalFormat.format(value);
    }

    public static String productFomat(Book book) {
        return String.format("\n\t%-18s%-58s%-28s%-18s%-18s%-28s%s\n",
                book.getID(),
                book.getName(),
                book.getAuthor(),
                book.getQuaility(),
                InstantUtils.doubleToVND(book.getPrice()),
                InstantUtils.instantToString(book.getCreatedAt()),
                InstantUtils.instantToString(book.getUpdateAt()));
//                InstantUtils.instantToString(product.getUpdateAt()));
    }

    public static String orderFomat(Order order) {
        return String.format("\n\t%-48d%-78s%-24d%-24s%-48s%-28s%-28s%-28s%-28s",
                order.getID(),
                order.getProductName(),
                order.getQuaility(),
                InstantUtils.doubleToVND(order.getPrice()),
                order.getUserNameOrder(),
                order.getPhoneNumberOfUser(),
                order.getNote(),
                order.getAddressOfUser(),
                InstantUtils.instantToString(order.getCreateAt()));
    }

    public static String userFormat(User user) {
        return String.format("%10d%20s%30s%26s%26s%38s%38s%38s%38s\n",
                user.getID(),
                user.getUserName(),
                user.getPassword(),
                user.getFullName(),
                user.getMobile(),
                user.getEmail(),
                user.getAddress(),
                InstantUtils.instantToString(user.getCreatedAt()),
                InstantUtils.instantToString(user.getUpdatedAt()));
    }

    public static long howLong(Instant instant1, Instant instant2) {
        String star = InstantUtils.instantToString(instant1, "dd/MM/yyyy");
        String end = InstantUtils.instantToString(instant2, "dd/MM/yyyy");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date starDate = simpleDateFormat.parse(star);
            Date endDate = simpleDateFormat.parse(end);
            long valuea = starDate.getTime();
            long valueb = endDate.getTime();
            long tmb = Math.abs(valueb - valuea);
            long result = tmb / (24 * 60 * 60 * 1000);
            return result;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
