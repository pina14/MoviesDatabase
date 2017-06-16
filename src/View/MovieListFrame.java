package View;

import Model.Movie;
import Model.MyMoviesDataBase;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hugo on 05/05/2017.
 */
public class MovieListFrame extends Frame{
    private JPanel rootPanel;
    private JToolBar toolbar;
    private JButton backBtn;
    private JScrollPane scrollPane;
    private JList listPanel;
    private final Map<String, ImageIcon> imageMap;
    private final Map<Integer, String> idMap;

    public MovieListFrame(MyMoviesDataBase db){
        String[] namesList = getAllNames(db.getMovies());
        ImageIcon[] imageslist = getAllImages(db.getMovies());
        String[] ids = getAllIds(db.getMovies());
        imageMap = createImageMap(namesList, imageslist);
        idMap = createIdMap(ids);
        listPanel.setListData(namesList);
        listPanel.setCellRenderer(new ViewListRenderer());
        setContentPane(rootPanel);
    }

    private Map<Integer, String> createIdMap(String[] ids) {
        Map<Integer, String> map = new HashMap<>();
        try {
            for (int i = 0; i < ids.length; i++)
                map.put(i, ids[i]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    private Map<String, ImageIcon> createImageMap(String[] namesList, ImageIcon[] imageslist) {
        Map<String, ImageIcon> map = new HashMap<>();
        try {
            for (int i = 0; i < namesList.length; i++)
                map.put(namesList[i], imageslist[i]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    public String getMovieId(Integer index){
        return idMap.get(index);
    }

    private String[] getAllIds(ArrayList<Movie> movies) {
        String[] res = new String[movies.size()];

        for (int i = 0; i < movies.size(); i++)
            res[i] = movies.get(i).getId();

        return res;
    }

    private String[] getAllNames(ArrayList<Movie> movies) {
        String[] res = new String[movies.size()];

        for (int i = 0; i < movies.size(); i++)
            res[i] = movies.get(i).getTitle();

        return res;
    }

    private ImageIcon[] getAllImages(ArrayList<Movie> movies) {
        ImageIcon[] icons = new ImageIcon[movies.size()];

        for (int i = 0; i < movies.size(); i++)
            icons[i] = movies.get(i).getPoster();

        return icons;
    }

    public class ViewListRenderer extends DefaultListCellRenderer {

        Font font = new Font("Comic Sans MS", Font.BOLD, 24);
        Border blackLine = BorderFactory.createLineBorder(Color.black);

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            //set label size
            label.setPreferredSize(new Dimension(200, 200));
            //get resized image
            Image image = getScaledImage(imageMap.get(value).getImage(), 200, 200);
            //set resized image icon
            label.setIcon(new ImageIcon(image));
            //set label location
            label.setHorizontalTextPosition(JLabel.RIGHT);
            //set label font
            label.setFont(font);
            //set label border type
            label.setBorder(blackLine);

            return label;
        }

    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public JList getListPanel() {
        return listPanel;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JToolBar getToolbar() {
        return toolbar;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
