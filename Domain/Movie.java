package Domain;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.image.*;

public class Movie extends Media{
    public Movie(int id, String title, String year, List<String> genre, double rating, BufferedImage image) {
        super(id, title, year, genre, rating, image);
    }

    private JPanel mainPanel;
    private StateInterface state;

    @Override
    public JPanel display(StateInterface state) {
        this.state = state;
        mainPanel  = new JPanel(); 
        mainPanel.setLayout(new GridLayout());
            
            mainPanel.add(getDisplayPicture());

        return mainPanel;
    }

    private JButton getDisplayPicture() {
        BufferedImage img = image;
        ImageIcon icon = new ImageIcon(img);
        JButton button = new JButton(icon);
        button.addActionListener(e -> {
            mainPanel.remove(button);
            mainPanel.add(getDisplayInfo());
            mainPanel.validate();
        });
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        return button;
    }

    private JPanel getDisplayInfo() {
        JPanel informationPanel = new JPanel();
        JLabel titleLabel = new JLabel(title);
        JLabel yearOfReleaseLabel = new JLabel("Udgivelses år: " + year);
        JLabel ratingLabel = new JLabel("Bedømmelse: " + rating);
        JLabel genreLabel = new JLabel(getGenreAsOneString());
        JButton watchButton = new JButton("Se");
        JButton addToFavoritesButton = new JButton("Tilføj til favoritter");
        JButton removeFromFavoritesButton = new JButton("Fjern fra favoritter");
        JButton returnButton = new JButton("Tilbage");

        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.PAGE_AXIS));

        informationPanel.add(titleLabel);
        informationPanel.add(yearOfReleaseLabel);
        informationPanel.add(ratingLabel);
        informationPanel.add(genreLabel);
        informationPanel.add(watchButton);

        if(state.isFavorite(id)) {
            informationPanel.add(removeFromFavoritesButton);
        } else { 
            informationPanel.add(addToFavoritesButton);
        }

        informationPanel.add(returnButton);
       
        returnButton.addActionListener(e -> {
            mainPanel.remove(informationPanel);
            mainPanel.add(getDisplayPicture());
            mainPanel.validate();
        });

        addToFavoritesButton.addActionListener(e -> {
            state.addFavorite(id);

            mainPanel.remove(informationPanel);
            mainPanel.add(getDisplayPicture());
            mainPanel.validate();
        });

        watchButton.addActionListener(e -> {
            state.addWatched(id);
        });

        removeFromFavoritesButton.addActionListener(e -> {
            state.removeFavorite(id);
            mainPanel.remove(informationPanel);
            mainPanel.add(getDisplayPicture());
            mainPanel.validate();
        });

        return informationPanel;
    }

    private String getGenreAsOneString() {
        String genreAsOneString = "";

        for(String element : genre) {
            genreAsOneString += element + ", ";
        }

        return genreAsOneString;
    }
}
