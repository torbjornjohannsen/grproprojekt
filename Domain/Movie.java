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

    private JPanel panel;
    StateInterface state;

    @Override
    public JPanel display(StateInterface state) {
        this.state = state;
        panel  = new JPanel(); 
        panel.setLayout(new GridLayout());
            
            panel.add(displayPicture());

        return panel;
    }

    private JButton displayPicture() {
        BufferedImage img = image;
        ImageIcon icon = new ImageIcon(img);
        JButton button = new JButton(icon);
        button.addActionListener(e -> {
            panel.remove(button);
            panel.add(displayInfo());
            panel.validate();
        });
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        return button;
    }

    private JPanel displayInfo() {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.PAGE_AXIS));
        JLabel titleLabel = new JLabel(title);
        JLabel yearOfReleaseLabel = new JLabel("Udgivelses år: " + year);
        JLabel ratingLabel = new JLabel("Bedømmelse: " + rating);
        JLabel genreLabel = new JLabel(getGenreAsOneString());
        JButton watchButton = new JButton("Se");
        JButton addToFavoritesButton = new JButton("Tilføj til favoritter");
        JButton removeFromFavoritesButton = new JButton("Fjern fra favoritter");
        JButton returnButton = new JButton("Tilbage");

        informationPanel.add(titleLabel);
        informationPanel.add(yearOfReleaseLabel);
        informationPanel.add(ratingLabel);
        informationPanel.add(genreLabel);
        informationPanel.add(watchButton);
        if(state.IsFavorite(id)) {
            informationPanel.add(removeFromFavoritesButton);
        } else { 
            informationPanel.add(addToFavoritesButton);
        }
        informationPanel.add(returnButton);
       
        returnButton.addActionListener(e -> {
            panel.remove(informationPanel);
            panel.add(displayPicture());
            panel.validate();
        });

        addToFavoritesButton.addActionListener(e -> {
            state.AddFavorite(id);

            panel.remove(informationPanel);
            panel.add(displayPicture());
            panel.validate();
        });

        watchButton.addActionListener(e -> {
            state.AddWatched(id);
        });

        removeFromFavoritesButton.addActionListener(e -> {
            state.RemoveFavorite(id);
            
            panel.remove(informationPanel);
            panel.add(displayPicture());
            panel.validate();
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
