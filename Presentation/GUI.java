package Presentation;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

import Data.TextDataAccess;
import Domain.Displayable;
import Domain.Media;
import Domain.Movie;
import Domain.State;

public class GUI {

    private static JFrame frame;
    private static JComponent currentView;
    private static State state;
    
    public static void main(String[] args) {
        state = new State();
        state.init();
        frame = makeMainFrame();
        currentView = makeView(state.getDisplayables());

        //Terminates program when exited
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(currentView, BorderLayout.CENTER);
        frame.validate();

        frame.add(makeMenuBar(), BorderLayout.PAGE_START);
        frame.validate();
    }

    private static JFrame makeMainFrame() {
        JFrame frame = new JFrame("NutFlix");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        return frame;
    }

    private static JPanel makeMenuBar() {
        JPanel menuBar = new JPanel();
        menuBar.setLayout(new GridLayout(1, 5));
        menuBar.setSize(10, 10);

        JButton homeButton = new JButton("Hjem");
        homeButton.addActionListener(e -> {
            frame.remove(currentView);
            frame.add(makeView(state.getDisplayables()));
            frame.validate();
        });

        JButton moviesButton = new JButton("Film");
        moviesButton.addActionListener(e -> {
            frame.remove(currentView);
            frame.add(makeView(state.getMovieDisplayables()));
            frame.validate();
        });

        JButton seriesButton = new JButton("Serier");
        seriesButton.addActionListener(e -> {
            frame.remove(currentView);
            frame.add(makeView(state.getSeriesDisplayables()));
            frame.validate();
        });
        homeButton.setPreferredSize(new Dimension(200,50));

        JTextField searchBar = new JTextField(20);

        JButton searchButton = new JButton("Søg efter titel");
        searchButton.addActionListener(e -> {
            frame.remove(currentView);
            frame.add(makeView(state.search(searchBar.getText())));
            frame.validate();
        });

        //Better way to get genres?
        String[] genres = {"Crime", "Drama", "Biography", "History", "Thriller", "Horror",
                            "Sport", "Romance", "War", "Mystery", "Adventure", "Family",
                            "Fantasy", "Film-Noir", "Musical", "Sci-fi", "Comedy", "Action",
                            "Western", "Music", "Animation", "Talk-show", "Documentary"};
        
        JComboBox comboBox = new JComboBox<>(genres);
        comboBox.setEditable(true);

        JButton genreButton = new JButton("Søg efter genre");
        genreButton.addActionListener(e -> {            
            frame.remove(currentView);
            frame.add(makeView(state.getGenreList(comboBox.getSelectedItem().toString())));
            frame.validate();
                
        });

        menuBar.add(homeButton);
        menuBar.add(moviesButton);
        menuBar.add(seriesButton);
        menuBar.add(searchBar);
        menuBar.add(searchButton);
        menuBar.add(comboBox);
        menuBar.add(genreButton);

        return menuBar;
    }

    private static JScrollPane makeView(List<? extends Displayable> contents) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 9));
        JScrollPane scrPane = new JScrollPane(panel);
        for(Displayable element : contents) {
            panel.add(element.display(state));
        }
        currentView = scrPane;
        return  scrPane;
    }
}
