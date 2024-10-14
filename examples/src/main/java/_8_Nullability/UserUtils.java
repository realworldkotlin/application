package _8_Nullability;

public class UserUtils {
    public static void printUserInfo(User user) {
        if (user != null) {
            System.out.println("User: " + user.getName());
            System.out.println("Email: " + user.getEmail());
        } else {
            System.out.println("User information is not available.");
        }
    }

    public static String getGreeting(User user) {
        if (user != null) {
            return "Hello, " + user.getName() + "!";
        } else {
            return "Hello, Guest!";
        }
    }
}