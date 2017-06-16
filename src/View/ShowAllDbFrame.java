package View;

import Model.DataBases;
import Model.MyMoviesDataBase;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by hugo on 05/05/2017.
 */
public class ShowAllDbFrame extends Frame{
    private JButton backBtn;
    private JToolBar toolbar;
    private JPanel rootPanel;
    private JList panelList;
    private JScrollPane scrollPane;


    public ShowAllDbFrame(DataBases dbs) {
        setContentPane(rootPanel);

        Object[] temp = new Object[dbs.getSize()];

        int i = 0;
        for (MyMoviesDataBase db : dbs.getDatabases()) {
            temp[i] = db.getName();
            i++;
        }

        panelList.setListData(temp);
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public JToolBar getToolbar() {
        return toolbar;
    }

    public JList getPanelList() {
        return panelList;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
