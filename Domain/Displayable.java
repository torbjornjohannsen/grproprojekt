package Domain;

import javax.swing.JPanel;

public interface Displayable {
    JPanel display(StateInterface state);
    int getId();
}
