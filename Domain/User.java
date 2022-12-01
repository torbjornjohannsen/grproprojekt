package Domain;

import java.util.*;

public class User {
    protected String name;
    protected int age;
    protected String gender;
    protected List<Integer> watchHistory;
    protected List<Integer> favoriteList; 

    User(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.watchHistory = new ArrayList<>();
        this.favoriteList = new LinkedList<>(); // since deletion & insertion are frequent
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public void AddFavorite(int favoriteID) { favoriteList.add(favoriteID); }
    public void RemoveFavorite(int favoriteID) { favoriteList.remove((Object)favoriteID); } // need to cast to object otherwise it sees the int as an index
    public void AddWHistory(int watchedID) { watchHistory.add(watchedID); }

}
