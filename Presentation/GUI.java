package Presentation;

import javax.swing.*;
import javax.swing.event.MenuEvent;

import Domain.Media;
import Domain.Movie;

public class GUI {
    
    public static void main(String[] args) {
        JFrame frame = makeMainFrame();

        frame.setJMenuBar(makeJMenuBar());

        //***TEST***
        Media tis = new Movie(1, "Dj", "2002", null, 1.1, null);
        frame.add(tis.display());
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
