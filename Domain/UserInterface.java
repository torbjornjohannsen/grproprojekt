package Domain;

import java.util.Set;

public interface UserInterface {
    public String getName();
    public int getAge();
    public String getGender();
    public Set<Integer> getWatchHistory();
    public Set<Integer> getFavoriteList();
    public void AddFavorite(int favoriteID);
    public void RemoveFavorite(int favoriteID);
    public void AddWHistory(int watchedID);
}
