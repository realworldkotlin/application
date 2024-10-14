package _7_Smart_casts;

public class Descriptinator {
    public String describe(Object obj) {
        if (obj instanceof Integer) {
            Integer number = (Integer) obj;
            return "The number is: " + number + ", Squared: " + (number * number);
        } else if (obj instanceof String) {
            String str = (String) obj;
            return "The string is: " + str + ", Length: " + str.length();
        }
        return "Unknown type";
    }
}
