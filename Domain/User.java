package Domain;

import java.util.*;

public class User implements UserInterface {
    protected String name;
    protected int age;
    protected String gender;
    protected Set<Integer> favoriteList; 

    User(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.favoriteList = new HashSet<>(); // since deletion & insertion are frequent
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public Set<Integer> getFavoriteList() { return favoriteList; }
    public void addFavorite(int favoriteID) { favoriteList.add(favoriteID); }
    public void removeFavorite(int favoriteID) { favoriteList.remove((Object)favoriteID); } // need to cast to object otherwise it sees the int as an index

}
