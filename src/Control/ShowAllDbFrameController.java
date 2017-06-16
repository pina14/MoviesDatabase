package Control;

import View.ShowAllDbFrame;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by hugo on 05/05/2017.
 */
public class ShowAllDbFrameController extends FrameController {
    private ShowAllDbFrame showAllDbFrame;
    private JPanel rootPanel;
    private JButton backBtn;
    private JToolBar toolbar;
    private JList listPanel;

    public ShowAllDbFrameController(){
        initComponents();
        setListeners();
    }

    private void setListeners() {
        backBtn.addActionListener(new MenuListener());
        listPanel.addMouseListener(new DoubleClickListener());
        listPanel.addKeyListener(new KeysListener());
        rootPanel.addKeyListener(new KeysListener());
        rootPanel.setFocusable(true);
    }

    private void initComponents() {
        showAllDbFrame = new ShowAllDbFrame(dbs);
        rootPanel = showAllDbFrame.getRootPanel();
        backBtn = showAllDbFrame.getBackBtn();
        toolbar = showAllDbFrame.getToolbar();
        listPanel = showAllDbFrame.getPanelList();
    }

    public void show() {
        showAllDbFrame.setVisible(true);
    }

    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MainFrameController mainFrameCtr = new MainFrameController();
            mainFrameCtr.show();
            showAllDbFrame.dispose();
        }
    }

    private class DoubleClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && !e.isConsumed()) {
                e.consume();

                //If double clicked in a empty space
                if(listPanel.getSelectedValue() == null)
                    return;

                //handle double click event.
                String selectedItem = listPanel.getSelectedValue().toString();
                MoviesListFrameController moviesListFrameController = new MoviesListFrameController(selectedItem);
                moviesListFrameController.show();
                showAllDbFrame.dispose();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

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
            if(e.getKeyChar() == '\n'){
                e.consume();

                //handle double click event.
                String selectedItem = listPanel.getSelectedValue().toString();
                MoviesListFrameController moviesListFrameController = new MoviesListFrameController(selectedItem);
                moviesListFrameController.show();
                showAllDbFrame.dispose();
            }
            else if(e.getKeyChar() == (KeyEvent.VK_BACK_SPACE)){
                e.consume();

                //handle backSpace event.
                MainFrameController mainFrameController = new MainFrameController();
                mainFrameController.show();
                showAllDbFrame.dispose();
            }
        }
    }
}
