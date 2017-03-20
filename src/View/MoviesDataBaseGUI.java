package View;

import javax.swing.*;

/**
 * Created by hugo on 15/03/2017.
 */
public class MoviesDataBaseGUI extends JFrame {
    private JPanel panel;
    private JLabel label;

    public MoviesDataBaseGUI(String labelName, int width, int height){
        panel = new JPanel();
        label = new JLabel(labelName);

        panel.add(label);
        this.setContentPane(panel);
        this.setSize(width, height);
        this.setVisible(true);
    }
}
