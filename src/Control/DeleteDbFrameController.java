package Control;

import Model.Commands.Command;
import Model.Commands.DeleteDatabase;
import Model.RunCommand;
import View.MessageDialog;
import View.DeleteDbFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by hugo on 04/05/2017.
 */
public class DeleteDbFrameController extends FrameController {
    private DeleteDbFrame deleteDbFrame;
    private JPanel rootPanel;
    private JLabel dbPathBtn;
    private JTextField pathText;
    private JLabel dbNameBtn;
    private JTextField dbNametext;
    private JButton deleteDbBtn;
    private JToolBar toolBar;
    private JButton backMenuBtn;
    Command command = new DeleteDatabase();

    public DeleteDbFrameController(){
        initComponents();
        setListeners();
    }

    private void setListeners() {
        backMenuBtn.addActionListener(new MenuListener());
        deleteDbBtn.addActionListener(new DeleteDbListener());
        rootPanel.addKeyListener(new KeysListener());
        rootPanel.setFocusable(true);
    }

    private void initComponents() {
        deleteDbFrame = new DeleteDbFrame();
        rootPanel = deleteDbFrame.getRootPanel();
        backMenuBtn = deleteDbFrame.getBackBtn();
        deleteDbBtn = deleteDbFrame.getDeleteDbBtn();
        dbNametext = deleteDbFrame.getDbNametext();
    }

    public void show(){
        deleteDbFrame.setVisible(true);
    }

    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MainFrameController mainFrameCtr = new MainFrameController();
            mainFrameCtr.show();
            deleteDbFrame.dispose();
        }
    }

    private class DeleteDbListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String dbName = dbNametext.getText(), message;
            RunCommand commandRes = new RunCommand(dbName, null, command, dbs);
            MessageDialog dialog = new MessageDialog();

            message = commandRes.hasSucceed() ? "Database \""+dbName+"\" was deleted!" : "There's no database with name: "+dbName ;

            dialog.setMessage(message);
            dialog.setTimer(2000);
            dialog.showDialog();
        }
    }

    private class KeysListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyChar() == (KeyEvent.VK_BACK_SPACE)){
                e.consume();

                //handle backSpace event.
                MainFrameController mainFrameController = new MainFrameController();
                mainFrameController.show();
                deleteDbFrame.dispose();
            }
        }
    }
}
