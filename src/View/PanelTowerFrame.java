package View;

import Model.MovieSerie;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelTowerFrame extends JFrame {

    public PanelTowerFrame(int length, ArrayList<MovieSerie> movies) {

        JPanel towerPanel = new JPanel();
        towerPanel.setLayout(new BoxLayout(towerPanel, BoxLayout.Y_AXIS));

        ImagePanel[] panels = new ImagePanel[length];

        for (int i = 0; i < length; i++) {
            ImagePanel ip = new ImagePanel(movies.get(i).getPosterLink());
            panels[i] = new ImagePanel(movies.get(i).getPosterLink());

            towerPanel.add(panels[i]);
        }

        //Scrollable window
        JScrollPane scrPane = new JScrollPane(towerPanel);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();     //set window size to screen size
        scrPane.setPreferredSize(screenSize);
        this.add(scrPane);
//        scrPane.setLayout(new ScrollPaneLayout());

        //resize window to fit panels and pops the window
        this.pack();

        //show window
        setVisible(true);
        //Exits programme when window is closed
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
