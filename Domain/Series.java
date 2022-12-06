package Domain;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import java.awt.GridLayout;
import javax.swing.BoxLayout;

import java.awt.image.*;

public class Series extends Media{
    Map<Integer, Integer> seasons;

    
    public Series (int id, String title, String year, List<String> genre, double rating, BufferedImage image, Map<Integer, Integer> seasons) {
        super(id, title, year, genre, rating, image);
        this.seasons = seasons;
    }

    JPanel panel = new JPanel();

    @Override 
    public JPanel display(StateInterface state) {
        //JPanel panel = new JPanel();
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
        JButton returnButton = new JButton("Tilbage");
        JComboBox seasonSelector = new JComboBox<>(getSeasonsAsStringArray());

        informationPanel.add(titleLabel);
        informationPanel.add(yearOfReleaseLabel);
        informationPanel.add(ratingLabel);
        informationPanel.add(genreLabel);
        informationPanel.add(seasonSelector);
        informationPanel.add(watchButton);
        informationPanel.add(addToFavoritesButton);
        informationPanel.add(returnButton);
       
        returnButton.addActionListener(e -> {
            panel.remove(informationPanel);
            panel.add(displayPicture());
            panel.validate();
        });

        addToFavoritesButton.addActionListener(e -> {
            
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

    private String[] getSeasonsAsStringArray() {
        String[] seasonsAsStringArray = new String[seasons.size()];
        int counter = 0;

        for(Map.Entry<Integer, Integer> entry : seasons.entrySet()) {
            seasonsAsStringArray[counter] = "" + entry.getKey() + ": " + entry.getValue();
            counter++;
        }

        return seasonsAsStringArray;
    }
}
