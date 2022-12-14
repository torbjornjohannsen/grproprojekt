package Domain;

import javax.swing.JPanel;

public interface Displayable {
    public JPanel display(StateInterface state);
    public String getTitle();
    public String getYear(); 
}
