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
    private static StateInterface state;
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
        JPanel menuBarPanel = new JPanel();
        menuBarPanel.setLayout(new GridLayout(1, 5));
        menuBarPanel.setSize(10, 10);

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

        JButton searchButton = new JButton("S??g efter titel");
        searchButton.addActionListener(e -> {
            List<? extends Displayable> results = state.search(searchBar.getText()); 
            if(results == null) {
                contentPanel.removeAll(); 
                JLabel noneFoundLabel = new JLabel("No matches found for your search query: \"" + searchBar.getText() + "\"");
                contentPanel.add(noneFoundLabel); 
                frame.repaint();
                frame.validate(); 
            } else {
                makeView(results, state);
            }
            
        });

        Set<String> genreSet = state.getGenres(); 
        
        JComboBox comboBox = new JComboBox<>(genreSet.toArray());
        comboBox.setEditable(true);

        JButton genreButton = new JButton("S??g efter genre");
        genreButton.addActionListener(e -> {   
            makeView(state.getGenreList(comboBox.getSelectedItem().toString()), state);
                
        });

        JButton userButton = new JButton("Profil");
        userButton.addActionListener(e -> {
            makeUserView();
        });

        menuBarPanel.add(homeButton);
        menuBarPanel.add(moviesButton);
        menuBarPanel.add(seriesButton);
        menuBarPanel.add(searchBar);
        menuBarPanel.add(searchButton);
        menuBarPanel.add(comboBox);
        menuBarPanel.add(genreButton);
        menuBarPanel.add(userButton);

        return menuBarPanel;
    }

    private static void makeView(List<? extends Displayable> content, StateInterface s) {
        int elementsInCol = 1512 / 156;
        int elementsInRow = content.size() / elementsInCol + 1;
        contentPanel.removeAll();

        for(Displayable element : content) {
            contentPanel.add(element.display(state));
        }

        if(content.size() < elementsInCol) {
            contentPanel.setLayout(new FlowLayout());
        } else {
            contentPanel.setLayout(new GridLayout(elementsInRow, elementsInCol));
        }

        frame.repaint();
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
        
        Set<Integer> favoriteSet = user.getFavoriteList();
        JPanel userFavoritesPanel = new JPanel();
        userFavoritesPanel.removeAll();
        userFavoritesPanel.setLayout(new FlowLayout());
        userFavoritesPanel.add(new JLabel("Dine favoritter: "));
        for(int element : favoriteSet) {
            Displayable favorite = state.getMedia(element); 
            userFavoritesPanel.add(favorite.display(state));
        }
        contentPanel.add(userFavoritesPanel);
        
        frame.repaint();
        frame.validate();
        
    }
}
