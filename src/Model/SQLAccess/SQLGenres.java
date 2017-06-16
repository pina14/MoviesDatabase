package Model.SQLAccess;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hugo on 07/04/2017.
 */
public class SQLGenres extends AccessDB{

    public static void insertGenre(String[] genres) throws SQLException {
        String insertCommand, selectCommand;
        boolean isInDB;

        if(genres == null)
            return;

        for (String genre : genres) {
            //eliminate first char if it's a space
            if(genre.charAt(0) == ' ')
                genre = genre.replaceFirst(" ", "");

            selectCommand = "select genre from genre where genre = '"+genre+"'";
            ResultSet rs = executeSelect(selectCommand);
            isInDB = isInDB(rs);

            //check if it's already in database, if it isn't, insert!
            if(!isInDB) {
                insertCommand = "insert into genre (genre) " +
                        "values('" + genre + "')";

                executeInsertOrDelete(insertCommand);
                DriverManager.println("inserted genre : "+genre);
            }
        }
    }

    public static void deleteGenres(String[] genres) throws SQLException {
        String selectGenre = "Select count(genre) from movieGenre where genre = '";
        String deleteGenre = "Delete from genre where genre = '";

        ResultSet rs = null;
        for (String genre : genres) {
            rs = executeSelect(selectGenre + genre + "'");

            rs.next();
            int count = rs.getInt(1);
            if (count == 1) {
                executeInsertOrDelete(deleteGenre + genre + "'");
            }
        }
    }

}
