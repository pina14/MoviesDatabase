package View;

import javax.swing.*;

/**
 * Created by hugo on 25/04/2017.
 */
public class MainFrame extends Frame{
    private JPanel mainPanel;
    private JButton createDbBtn;
    private JButton listDbBtn;
    private JButton exitBtn;
    private JLabel welcomeLabel;
    private JButton deleteDbBtn;

    public MainFrame(){
        setContentPane(mainPanel);
    }

    public JButton getCreateDbBtn() {
        return createDbBtn;
    }

    public JButton getListDbBtn() {
        return listDbBtn;
    }

    public JButton getExitBtn() {
        return exitBtn;
    }

    public JLabel getWelcomeLabel() {
        return welcomeLabel;
    }

    public JButton getDeleteDbBtn() {
        return deleteDbBtn;
    }
}
