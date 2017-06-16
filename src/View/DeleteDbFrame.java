package View;

import javax.swing.*;

/**
 * Created by hugo on 04/05/2017.
 */
public class DeleteDbFrame extends Frame{
    private JLabel dbNameBtn;
    private JTextField dbNametext;
    private JButton deleteDbBtn;
    private JToolBar toolBar;
    private JButton backBtn;
    private JPanel rootPanel;
    private JPanel infoPanel;

    public DeleteDbFrame(){
        setContentPane(rootPanel);
    }

    public JLabel getDbNameBtn() {
        return dbNameBtn;
    }

    public JTextField getDbNametext() {
        return dbNametext;
    }

    public JButton getDeleteDbBtn() {
        return deleteDbBtn;
    }

    public JToolBar getToolBar() {
        return toolBar;
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
