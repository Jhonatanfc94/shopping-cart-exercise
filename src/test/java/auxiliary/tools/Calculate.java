package auxiliary.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculate {
    public static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double floor(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, RoundingMode.FLOOR);
        return bd.doubleValue();
    }

    public static String roundString(String d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        return bd.toString();
    }
}