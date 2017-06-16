package Model.SQLAccess;

import Model.Movie;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hugo on 07/04/2017.
 */
public class SQLMovies extends AccessDB{

    public static void insertMovie(Movie movie) throws SQLException {
        //check if it's already in database
        String selectCommand = "select ID from movie where ID = '"+movie.getId()+"'";
        ResultSet rs = executeSelect(selectCommand);
        boolean isInDB = isInDB(rs);

        //if it's not in dataBase, insert!
        if(!isInDB) {
            String command = "insert into movie (ID, title, type, plot, released, imdbRating, runtime, poster) " +
                    "values('"+movie.getId()+"', '"+movie.getTitle()+"', '"+movie.getType()+"', \'"+movie.getPlot()+"\', '"
                    +movie.getReleased()+"', "+movie.getImdbRating()+", "+movie.getRuntime()+", '"+movie.getPosterLink()+"')";

            executeInsertOrDelete(command);
            DriverManager.println("inserted movie/series with title : "+movie.getTitle());
        }
    }

    public static void getMovie(String movieID, ArrayList<HashMap<String, String>> movieInfo, HashMap<String, String> moviesAttributes) throws SQLException {
        String selectMovie = "Select * from movie where ID = '"+movieID+"'";
        ResultSet rs = executeSelect(selectMovie);
        ResultSetMetaData metaData = rs.getMetaData();
        int numCol = metaData.getColumnCount();

        rs.next();

        for (int i = 1; i <= numCol; i++)
            moviesAttributes.put(metaData.getColumnLabel(i), rs.getString(i));

        movieInfo.add(moviesAttributes);
    }

    public static void deleteMovies(String movieID) throws SQLException {
        String deleteMovie = "Delete from movie where ID = '"+movieID+"'";

        executeInsertOrDelete(deleteMovie);
    }
}
