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
        menuBar.setLayout(new GridLayout(1, 3));
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
            JLabel label = new JLabel("Hey");
            currentView = label;
            frame.add(label);
            frame.validate();
        });

        JButton seriesButton = new JButton("Serier");
        homeButton.setPreferredSize(new Dimension(200,50));

        menuBar.add(homeButton);
        menuBar.add(moviesButton);
        menuBar.add(seriesButton);

        return menuBar;
    }

    private static JScrollPane makeHomeView() {
        State stateMovie = new State(); 
        State stateSerie = new State();
        TextDataAccess tda = new TextDataAccess();
        stateMovie.init(tda.load("film"));
        stateSerie.init(tda.load("serier"));
        List<? extends Displayable> allMovies = stateMovie.getDisplayables();
        List<? extends Displayable> allSeries = stateSerie.getDisplayables();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 9));
        JScrollPane scrPane = new JScrollPane(panel);
        
        for(Displayable movie : allMovies) {
            panel.add(movie.display());
        }
        
        currentView = scrPane;
        return  scrPane;
    }

    private static JScrollPane makeMovieView() {
        State state = new State(); 
        TextDataAccess tda = new TextDataAccess();
        state.init(tda.load("film"));
        List<? extends Displayable> allDisplayables = state.getDisplayables();
        System.out.println(allDisplayables.size());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 9));
        JScrollPane scrPane = new JScrollPane(panel);
        
        for(Displayable element : allDisplayables) {
            
            panel.add(element.display());
            
        }

        currentView = scrPane;
        return  scrPane;
    }

}
