package Domain;

import java.util.List;
import java.util.ArrayList;

public class User {
    protected String name;
    protected int age;
    protected String gender;
    protected List<Media> watchHistory;

    User(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.watchHistory = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }

}
