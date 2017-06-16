package Control;

import Model.SQLAccess.AccessDB;
import View.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by hugo on 04/05/2017.
 */
public class MainFrameController extends FrameController {

    private MainFrame mainFrame;
    private JButton createDbBtn;
    private JButton listDbBtn;
    private JButton exitBtn;
    private JButton deleteDbBtn;

    public MainFrameController(){
        initComponents();
        setListeners();
    }

    private void setListeners() {
        createDbBtn.addActionListener(new CreateDbListener());
        deleteDbBtn.addActionListener(new DeleteDbListener());
        listDbBtn.addActionListener(new ListDbListener());
        exitBtn.addActionListener(new ExitListener());
    }

    private void initComponents() {
        mainFrame = new MainFrame();
        createDbBtn = mainFrame.getCreateDbBtn();
        listDbBtn = mainFrame.getListDbBtn();
        exitBtn = mainFrame.getExitBtn();
        deleteDbBtn = mainFrame.getDeleteDbBtn();
    }

    public void show(){
        mainFrame.setVisible(true);
    }

    private class CreateDbListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CreateDbFrameController cdbCtr = new CreateDbFrameController();
            cdbCtr.show();
            mainFrame.dispose();
        }
    }

    private class DeleteDbListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DeleteDbFrameController deleteDbFrameController = new DeleteDbFrameController();
            deleteDbFrameController.show();
            mainFrame.dispose();
        }
    }

    private class ListDbListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ShowAllDbFrameController showAllDbFrameController = new ShowAllDbFrameController();
            showAllDbFrameController.show();
            mainFrame.dispose();
        }
    }

    private class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                AccessDB.closeConnection();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
            mainFrame.dispose();
            System.exit(0);
        }
    }
}
