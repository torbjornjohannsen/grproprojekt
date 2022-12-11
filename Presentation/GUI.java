package Presentation;

import java.util.*;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

import Domain.Displayable;
import Domain.State;
import Domain.StateInterface;
import Domain.UserInterface;

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
        });

        JButton moviesButton = new JButton("Film");
        moviesButton.addActionListener(e -> {
            makeView(state.getMovieDisplayables(), state);
        });

        JButton seriesButton = new JButton("Serier");
        seriesButton.addActionListener(e -> {
            makeView(state.getSeriesDisplayables(), state);
        });
        homeButton.setPreferredSize(new Dimension(200,50));

        JTextField searchBar = new JTextField(20);

        JButton searchButton = new JButton("Søg efter titel");
        searchButton.addActionListener(e -> {
            makeView(state.search(searchBar.getText()), state);
        });

        Set<String> genreSet = state.getGenres(); 
        
        JComboBox comboBox = new JComboBox<>(genreSet.toArray());
        comboBox.setEditable(true);

        JButton genreButton = new JButton("Søg efter genre");
        genreButton.addActionListener(e -> {   
            makeView(state.getGenreList(comboBox.getSelectedItem().toString()), state);
                
        });

        JButton chronologicalButton = new JButton("Søg efter release date");
        chronologicalButton.addActionListener(e -> {   
            makeView(state.sortYear(), state);
            
        });

        JButton userButton = new JButton("Profil");
        userButton.addActionListener(e -> {
            makeUserView();
        });

        menuBar.add(homeButton);
        menuBar.add(moviesButton);
        menuBar.add(seriesButton);
        menuBar.add(searchBar);
        menuBar.add(searchButton);
        menuBar.add(comboBox);
        menuBar.add(genreButton);
        menuBar.add(chronologicalButton);
        menuBar.add(userButton);

        return menuBar;
    }

    private static void makeView(List<? extends Displayable> contents, StateInterface s) {
        int elementsInCol = 1512 / 156;
        int elementsInRow = contents.size() / elementsInCol + 1;
        contentPanel.removeAll();
        for(Displayable element : contents) {
            contentPanel.add(element.display());
        }

        if(contents.size() < elementsInCol) {
            contentPanel.setLayout(new FlowLayout());
        } else {
            contentPanel.setLayout(new GridLayout(elementsInRow, elementsInCol));
        }

        frame.validate(); 
    }

    private static void makeUserView() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        UserInterface user = state.getUser();
        JLabel userName = new JLabel("Hej " + user.getName());
        JLabel userAge = new JLabel("Alder: " + user.getAge());
        contentPanel.add(userName);
        contentPanel.add(userAge);

        Set<Integer> watchedSet = user.getWatchHistory();
        /*JPanel userWatchHistoryPanel = new JPanel();
        userWatchHistoryPanel.setLayout(new FlowLayout());
        userWatchHistoryPanel.add(new JLabel("Din historik:"));*/
        for(int element : watchedSet) {
            Displayable watched = state.getMedia(element); 
            contentPanel.add(watched.display());
        }
        //contentPanel.add(contentPanel);

        Set<Integer> favoriteSet = user.getFavoriteList();
        /*JPanel userFavoritesPanel = new JPanel();
        userFavoritesPanel.setLayout(new FlowLayout());
        userFavoritesPanel.add(new JLabel("Dine favoritter: "));*/
        for(int element : favoriteSet) {
            Displayable favorite = state.getMedia(element); 
            contentPanel.add(favorite.display());
        }
        //contentPanel.add(contentPanel);

        frame.validate();
    }
}
