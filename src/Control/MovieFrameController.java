package Control;

import Model.Movie;
import View.MovieFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by hugo on 06/05/2017.
 */
public class MovieFrameController extends FrameController{
    MovieFrame movieFrame;
    private JPanel rootPanel;
    private JButton backBtn;
    private final String dbName;

    public MovieFrameController(String dbName, Movie movie) {
        this.dbName = dbName;
        initComponents(movie);
        setListeners();
    }

    private void setListeners() {
        backBtn.addActionListener(new MenuListener());
        rootPanel.addKeyListener(new KeysListener());
        rootPanel.setFocusable(true);
    }

    private void initComponents(Movie movie) {
        movieFrame = new MovieFrame(movie);
        rootPanel = movieFrame.getRootPanel();
        backBtn = movieFrame.getBackBtn();
    }

    public void show() {
        movieFrame.setVisible(true);
    }

    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MoviesListFrameController moviesListFrameController = new MoviesListFrameController(dbName);
            moviesListFrameController.show();
            movieFrame.dispose();
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
                MoviesListFrameController moviesListFrameController = new MoviesListFrameController(dbName);
                moviesListFrameController.show();
                movieFrame.dispose();
            }
        }
    }
}
