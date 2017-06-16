package Control;

import Model.Commands.Command;
import Model.Commands.CreateDatabase;
import Model.RunCommand;
import View.CreateDbFrame;
import View.MessageDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by hugo on 04/05/2017.
 */
public class CreateDbFrameController extends FrameController {
    private CreateDbFrame createDbFrame;
    private JPanel rootPanel;
    private JTextField pathText;
    private JTextField dbNametext;
    private JLabel dbPathBtn;
    private JLabel dbNameBtn;
    private JButton createDbBtn;
    private JButton backMenuBtn;
    private JToolBar toolBar;
    Command command = new CreateDatabase();

    public CreateDbFrameController(){
        initComponents();
        setListeners();
    }

    private void setListeners() {
        backMenuBtn.addActionListener(new MenuListener());
        createDbBtn.addActionListener(new CreateDbListener());
        rootPanel.addKeyListener(new KeysListener());
        rootPanel.setFocusable(true);
    }

    private void initComponents() {
        createDbFrame = new CreateDbFrame();
        rootPanel = createDbFrame.getRootPanel();
        backMenuBtn = createDbFrame.getBackMenuBtn();
        createDbBtn = createDbFrame.getCreateDbBtn();
        pathText = createDbFrame.getPathText();
        dbNametext = createDbFrame.getDbNametext();
    }

    public void show(){
        createDbFrame.setVisible(true);
    }

    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MainFrameController mainFrameCtr = new MainFrameController();
            mainFrameCtr.show();
            createDbFrame.dispose();
        }
    }

    private class CreateDbListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String dbName = dbNametext.getText(), message;
            RunCommand commandRes = new RunCommand(dbName, pathText.getText(), command, dbs);
            MessageDialog dialog = new MessageDialog();

            message = commandRes.hasSucceed() ? "Created database \""+dbName+"\"!" : "Already exists database with name: "+dbName ;

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
                createDbFrame.dispose();
            }
        }
    }
}
