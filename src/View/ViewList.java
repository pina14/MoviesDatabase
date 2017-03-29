package View;

import Model.MovieSerie;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hugo on 27/03/2017.
 */
public class ViewList {
    private final Map<String, ImageIcon> imageMap;

    public ViewList(ArrayList<MovieSerie> movies) {
//        String[] nameList = {"Mario", "Luigi", "Bowser", "Koopa", "Princess"};
        String[] nameList = getNameList(movies);
        ImageIcon[] imageURL = getImageIcon(movies);
        imageMap = createImageMap(nameList, imageURL);
        JList list = new JList(nameList);
        list.setCellRenderer(new ViewList.ViewListRenderer());

        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(300, 400));

        JFrame frame = new JFrame();
        frame.add(scroll);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private String[] getNameList(ArrayList<MovieSerie> movies) {
        String[] res = new String[movies.size()];
        for (int i = 0; i < movies.size(); i++) {
            res[i] = movies.get(i).getTitle();
        }
        return res;
    }

    private ImageIcon[] getImageIcon(ArrayList<MovieSerie> movies) {
        ImageIcon[] icons = new ImageIcon[movies.size()];
        for (int i = 0; i < movies.size(); i++) {
            icons[i] = movies.get(i).getPoster();
        }
        return icons;
    }

    public class ViewListRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvitica", Font.BOLD, 24);

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(imageMap.get((String) value));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }
    }

    private Map<String, ImageIcon> createImageMap(String[] names, ImageIcon[] imageURL) {
        Map<String, ImageIcon> map = new HashMap<>();
        try {
            for (int i = 0; i < names.length; i++) {
                map.put(names[i], imageURL[i]);
            }
            /*map.put("Mario", new ImageIcon(new URL("http://i.stack.imgur.com/NCsHu.png")));
            map.put("Luigi", new ImageIcon(new URL("http://i.stack.imgur.com/UvHN4.png")));
            map.put("Bowser", new ImageIcon(new URL("http://i.stack.imgur.com/s89ON.png")));
            map.put("Koopa", new ImageIcon(new URL("http://i.stack.imgur.com/QEK2o.png")));
            map.put("Princess", new ImageIcon(new URL("http://i.stack.imgur.com/f4T4l.png")));*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }
}
