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

    private static JFrame frame = makeMainFrame();
    private static JComponent currentView = makeHomeView();
    
    public static void main(String[] args) {
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
            frame.add(makeHomeView());
            frame.validate();
        });

        JButton moviesButton = new JButton("Film");
        moviesButton.addActionListener(e -> {
            frame.remove(currentView);
            frame.add(makeMovieView());
            frame.validate();
        });

        JButton seriesButton = new JButton("Serier");
        seriesButton.addActionListener(e -> {
            frame.remove(currentView);
            frame.add(makeSeriesView());
            frame.validate();
        });
        homeButton.setPreferredSize(new Dimension(200,50));

        JTextField searchBar = new JTextField(20);

        JButton searchButton = new JButton("Søg");
        searchButton.addActionListener(e -> {
            //needs to be retrieved from main method
            State state = new State();
            state.init();
            state.search(searchBar.getText());
            
            frame.remove(currentView);
            frame.add(makeSearchView(searchBar.getText()));
            frame.validate();
                
        });

        menuBar.add(homeButton);
        menuBar.add(moviesButton);
        menuBar.add(seriesButton);
        menuBar.add(searchBar);
        menuBar.add(searchButton);

        return menuBar;
    }

    private static JScrollPane makeHomeView() {
        State state = new State();
        state.init();
        List<? extends Displayable> allDisplayables = state.getDisplayables();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(allDisplayables.size() / 8, 9));
        JScrollPane scrPane = new JScrollPane(panel);
        
        for(Displayable element : allDisplayables) {
            panel.add(element.display());
        }
        
        currentView = scrPane;
        return  scrPane;
    }

    private static JScrollPane makeMovieView() {
        State state = new State(); 
        state.init();
        List<? extends Displayable> allDisplayables = state.getMovieDisplayables();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 9));
        JScrollPane scrPane = new JScrollPane(panel);
        
        for(Displayable element : allDisplayables) {
            
            panel.add(element.display());
            
        }

        currentView = scrPane;
        return  scrPane;
    }

    private static JScrollPane makeSeriesView() {
        State state = new State(); 
        state.init();
        List<? extends Displayable> sereDisplayables = state.getSeriesDisplayables();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 9));
        JScrollPane scrPane = new JScrollPane(panel);
        
        for(Displayable element : sereDisplayables) {
            
            panel.add(element.display());
            
        }

        currentView = scrPane;
        return  scrPane;
    }

    private static JScrollPane makeSearchView(String seachString) {
        State state = new State();
        state.init();

        List<? extends Displayable> allMatches = state.search(seachString);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 9));
        JScrollPane scrPane = new JScrollPane(panel);
        
        for(Displayable element : allMatches) {
            
            panel.add(element.display());
            
        }

        currentView = scrPane;
        return  scrPane;
    }

}
