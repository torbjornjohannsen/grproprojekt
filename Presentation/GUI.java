package Presentation;

import java.util.*;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

import Domain.Displayable;
import Domain.State;
import Domain.StateInterface;

public class GUI {

    private static JFrame frame;
    private static State state;
    private static JScrollPane scrPane;
    private static JPanel contentPanel;
    
    public static void main(String[] args) {
        contentPanel = new JPanel();
        scrPane = new JScrollPane(contentPanel);
        state = new State();
        state.init();
        frame = makeMainFrame();
        makeView(state.getDisplayables(), state);

        //Terminates program when exited
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(scrPane, BorderLayout.CENTER);
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
            makeView(state.getDisplayables(), state);
            frame.validate();
        });

        JButton moviesButton = new JButton("Film");

        
        moviesButton.addActionListener(e -> {
            makeView(state.getMovieDisplayables(), state);
            frame.validate();
        });

        JButton seriesButton = new JButton("Serier");
        seriesButton.addActionListener(e -> {
            makeView(state.getSeriesDisplayables(), state);
            frame.validate();
        });
        homeButton.setPreferredSize(new Dimension(200,50));

        JTextField searchBar = new JTextField(20);

        JButton searchButton = new JButton("Søg efter titel");
        searchButton.addActionListener(e -> {
            makeView(state.search(searchBar.getText()), state);
            frame.validate();
        });

        Set<String> genreSet = state.getGenres(); 
        
        JComboBox comboBox = new JComboBox<>(genreSet.toArray());
        comboBox.setEditable(true);

        JButton genreButton = new JButton("Søg efter genre");
        genreButton.addActionListener(e -> {   
            makeView(state.getGenreList(comboBox.getSelectedItem().toString()), state);
            frame.validate();
                
        });

        JButton chronologicalButton = new JButton("Søg efter release date");
        chronologicalButton.addActionListener(e -> {   
            makeView(state.sortYear(), state);
            frame.validate();
            
        });

        menuBar.add(homeButton);
        menuBar.add(moviesButton);
        menuBar.add(seriesButton);
        menuBar.add(searchBar);
        menuBar.add(searchButton);
        menuBar.add(comboBox);
        menuBar.add(genreButton);
        menuBar.add(chronologicalButton);

        return menuBar;
    }

    private static void makeView(List<? extends Displayable> contents, StateInterface s) {
        contentPanel.removeAll();
        contentPanel.setLayout(new GridLayout(12, 9));
        for(Displayable element : contents) {
            contentPanel.add(element.display());
        }
    }
}
