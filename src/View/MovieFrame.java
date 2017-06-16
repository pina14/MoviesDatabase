package View;

import Model.Movie;
import javax.swing.*;
import java.awt.*;

/**
 * Created by hugo on 05/05/2017.
 */
public class MovieFrame extends Frame{
    private JPanel rootPanel;
    private JPanel imagePanel;
    private JPanel infoPanel;
    private JLabel titleLabel;
    private JLabel titleValueLabel;
    private JLabel plotLabel;
    private JTextArea plotValueTextArea; //todo mudar para outro tipo, porque JTEXTArea da para apagar o texto
    private JLabel typeLabel;
    private JLabel typeValueLabel;
    private JLabel releasedLabel;
    private JLabel ratingLabel;
    private JLabel runtimeLabel;
    private JLabel releasedValueLabel;
    private JLabel ratingValueLabel;
    private JLabel runtimeValueLabel;
    private JButton backBtn;
    private JToolBar toolbar;

    public MovieFrame(Movie movie){
        SpringLayout layout = new SpringLayout();
        setContentPane(rootPanel);

        //set image panel
        imagePanel.setLayout(layout);
        Image image = getScaledImage(movie.getPoster().getImage(), 200, 300);
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(image));
        imagePanel.setPreferredSize(new Dimension(200, 300));
        imagePanel.add(imageLabel);

        //set info panel
        titleValueLabel.setText(movie.getTitle());
        plotValueTextArea.setText(movie.getPlot());
        plotValueTextArea.setLineWrap(true);
        plotValueTextArea.setBackground(infoPanel.getBackground());
        typeValueLabel.setText(movie.getType());
        releasedValueLabel.setText(movie.getReleased());
        ratingValueLabel.setText(String.valueOf(movie.getImdbRating()));
        runtimeValueLabel.setText(String.valueOf(movie.getRuntime()));
        this.pack();
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
