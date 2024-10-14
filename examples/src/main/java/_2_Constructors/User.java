package _2_Constructors;

public class User {
    private String name;
    private int age;
    private String email;

    public User() {
        this.name = "Unknown";
        this.age = 0;
        this.email = "Unknown";
    }

    public User(String name) {
        this.name = name;
        this.age = 0;
        this.email = "Unknown";
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
        this.email = "Unknown";
    }

    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
}