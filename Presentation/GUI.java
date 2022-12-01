package Presentation;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.MenuEvent;

import java.awt.GridLayout;

import Data.TextDataAccess;
import Domain.Displayable;
import Domain.Media;
import Domain.Movie;
import Domain.State;

public class GUI {
    
    public static void main(String[] args) {
        JFrame frame = makeMainFrame();
        frame.setLayout(new GridLayout());

        State state = new State(); 
        state.init();
        List<? extends Displayable> allDisplayables = state.getDisplayables();
        System.out.println(allDisplayables.size());

        frame.setJMenuBar(makeJMenuBar());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 10));
        JScrollPane scrPane = new JScrollPane(panel);
        
        for(Displayable element : allDisplayables) {
            
            panel.add(element.display());
            
        }
        frame.add(scrPane);
        frame.validate();
    } 

    private static JFrame makeMainFrame() {
        JFrame frame = new JFrame("NutFlix");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        return frame;
    }

    private static JMenuBar makeJMenuBar() {
        JMenuBar menubar = new JMenuBar();
        JMenuItem home = new JMenu("Hjem");
        JMenuItem movies = new JMenu("Film");
        JMenuItem series = new JMenu("Serier");

        menubar.add(home);
        menubar.add(movies);
        menubar.add(series);

        return menubar;
    }

}
