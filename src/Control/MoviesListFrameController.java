package Control;

import Model.MyMoviesDataBase;
import View.MovieListFrame;
import javax.swing.*;
import java.awt.event.*;

/**
 * Created by hugo on 06/05/2017.
 */
public class MoviesListFrameController extends FrameController{
    private MovieListFrame movieListFrame;
    private JPanel rootPanel;
    private JButton backBtn;
    private JList listPanel;
    //TODO implement from and to frame
/*    private FrameController from;
    private FrameController to;*/
    MyMoviesDataBase curDb;

    public MoviesListFrameController(String dbName){
        curDb = dbs.getDataBase(dbName);
        initComponents(curDb);
        setListeners();
    }

    private void setListeners() {
        backBtn.addActionListener(new MenuListener());
        listPanel.addMouseListener(new DoubleClickListener());
        listPanel.addKeyListener(new KeysListener());
        rootPanel.addKeyListener(new KeysListener());
        rootPanel.setFocusable(true);
    }

    private void initComponents(MyMoviesDataBase curDb) {
        movieListFrame = new MovieListFrame(curDb);
        rootPanel = movieListFrame.getRootPanel();
        backBtn = movieListFrame.getBackBtn();
        listPanel = movieListFrame.getListPanel();
    }

    public void show() {
        movieListFrame.setVisible(true);
    }

    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ShowAllDbFrameController showAllDbFrameController = new ShowAllDbFrameController();
            showAllDbFrameController.show();
            movieListFrame.dispose();
        }
    }

    private class DoubleClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && !e.isConsumed()) {
                e.consume();

                //handle double click event.
                String selectedItem = movieListFrame.getMovieId(listPanel.getSelectedIndex());
                MovieFrameController movieFrameController = new MovieFrameController(curDb.getName(), curDb.getMovie(selectedItem));
                movieFrameController.show();
                movieListFrame.dispose();
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

                //If double clicked in a empty space
                if(listPanel.getSelectedValue() == null)
                    return;

                //handle double click event.
                String selectedItem = movieListFrame.getMovieId(listPanel.getSelectedIndex());
                MovieFrameController movieFrameController = new MovieFrameController(curDb.getName(), curDb.getMovie(selectedItem));
                movieFrameController.show();
                movieListFrame.dispose();
            }
            else if(e.getKeyChar() == (KeyEvent.VK_BACK_SPACE)){
                e.consume();

                //handle backSpace event.
                ShowAllDbFrameController showAllDbFrameController = new ShowAllDbFrameController();
                showAllDbFrameController.show();
                movieListFrame.dispose();
            }
        }
    }
}
