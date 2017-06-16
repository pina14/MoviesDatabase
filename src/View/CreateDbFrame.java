package View;

import javax.swing.*;

/**
 * Created by hugo on 04/05/2017.
 */
public class CreateDbFrame extends Frame{
    private JPanel rootPanel;
    private JTextField pathText;
    private JTextField dbNametext;
    private JLabel dbPathBtn;
    private JLabel dbNameBtn;
    private JButton createDbBtn;
    private JButton backMenuBtn;
    private JToolBar toolBar;
    private JPanel infoPanel;

    public CreateDbFrame(){
        setContentPane(rootPanel);
    }

    public JTextField getPathText() {
        return pathText;
    }

    public JTextField getDbNametext() {
        return dbNametext;
    }

    public JLabel getDbPathBtn() {
        return dbPathBtn;
    }

    public JLabel getDbNameBtn() {
        return dbNameBtn;
    }

    public JButton getCreateDbBtn() {
        return createDbBtn;
    }

    public JButton getBackMenuBtn() {
        return backMenuBtn;
    }

    public JToolBar getToolBar() {
        return toolBar;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
