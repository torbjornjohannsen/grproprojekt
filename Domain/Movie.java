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

    @Override 
    public JPanel display() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout());
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
            panel.add(button);

        return panel;
    }

    @Override
    public JPanel displayInfo() {
        JPanel panel = new JPanel();
        JPanel informationPanel = new JPanel(new GridLayout(3,1));
        //panel.setLayout(new GridLayout(2,1));
        JButton button = new JButton(title);
        JLabel yearOfReleaseLabel = new JLabel("Udgivelses år: " + year);
        JLabel ratingLabel = new JLabel("Bedømmelse: " + rating);
        JLabel genreLabel = new JLabel(getGenreAsOneString());

       
        informationPanel.add(yearOfReleaseLabel);
        informationPanel.add(ratingLabel);
        informationPanel.add(genreLabel);
       
        button.addActionListener(e -> {
            panel.remove(informationPanel);
            panel.remove(button);
            panel.add(display());
            panel.validate();
        });

        button.setBorderPainted(false);
        button.setFocusPainted(false);
        panel.add(button);
        panel.add(informationPanel);

        return panel;
    }

    private String getGenreAsOneString() {
        String genreAsOneString = "";

        for(String element : genre) {
            genreAsOneString += element + ", ";
        }

        return genreAsOneString;
    }
}
