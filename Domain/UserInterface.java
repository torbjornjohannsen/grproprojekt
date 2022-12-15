package Domain;

import java.util.Set;

public interface UserInterface {
    public String getName();
    public int getAge();
    public String getGender();
    public Set<Integer> getFavoriteList();
    public void addFavorite(int favoriteID);
    public void removeFavorite(int favoriteID);
}
