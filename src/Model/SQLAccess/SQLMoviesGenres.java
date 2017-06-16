package Model.SQLAccess;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hugo on 07/04/2017.
 */
public class SQLMoviesGenres extends AccessDB{

    public static void insertMovieGenre(String movieID, String[] genres, String movieTitle) throws SQLException {
        String insertCommand, selectCommand;
        boolean isInDB;

        if(genres == null)
            return;

        for (String genre : genres) {
            //eliminate first char if it's a space
            if(genre.charAt(0) == ' ')
                genre = genre.replaceFirst(" ", "");

            selectCommand = "select * from movieGenre where movieID = '"+movieID+"' and genre = '"+genre+"'";
            ResultSet rs = executeSelect(selectCommand);
            isInDB = isInDB(rs);

            //check if it's already in database, if it isn't, insert!
            if(!isInDB) {
                insertCommand = "insert into movieGenre (movieID, genre, movieTitle) " +
                        "values('" + movieID + "','" + genre + "','" + movieTitle + "')";

                executeInsertOrDelete(insertCommand);
                DriverManager.println("inserted movie genre : "+genre+" ("+movieTitle+")");
            }
        }
    }

    public static void getMovieGenres(String movieID, ArrayList<HashMap<String, String>> movieInfo, HashMap<String, String> moviesAttributes) throws SQLException {
        String selectMovieGenres = "Select genre from movieGenre where movieID = '"+movieID+"'";
        ResultSet rs = executeSelect(selectMovieGenres);
        ResultSetMetaData metaData = rs.getMetaData();
        StringBuilder res = new StringBuilder();

        rs.next();
        while(rs.getRow() != 0) {
            res.append(rs.getString(1));
            if(rs.next()) {
                res.append(", ");
            }
        }

        int index = movieInfo.size() > 0 ? movieInfo.size()-1 : 0;

        movieInfo.get(index).put(metaData.getColumnLabel(1), res.toString());
    }

    public static void deleteMovieGenres(String[] genres, String movieID) throws SQLException {
        String deleteMovieGenre = "Delete from movieGenre where  movieID = + '"+movieID+"' and genre = '";

        for (String genre : genres) {
            executeInsertOrDelete(deleteMovieGenre + genre + "'");
        }
    }
}
