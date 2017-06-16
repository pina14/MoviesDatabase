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
public class SQLMoviesActors extends AccessDB{

    public static void insertMovieActor(String movieID, String[] actorsNames, String movieTitle) throws SQLException {
        String insertCommand, selectCommand;
        boolean isInDB;

        if (actorsNames == null)
            return;

        for (String name : actorsNames) {
            //eliminate first char if it's a space
            if(name.charAt(0) == ' ')
                name = name.replaceFirst(" ", "");

            selectCommand = "select * from movieActor where movieID = '"+movieID+"' and actorName = '"+name+"'";
            ResultSet rs = executeSelect(selectCommand);
            isInDB = isInDB(rs);

            //check if it's already in database, if it isn't, insert!
            if(!isInDB) {
                insertCommand = "insert into movieActor (movieID, actorName, movieTitle) " +
                        "values('" + movieID + "','" + name + "','" + movieTitle + "')";

                executeInsertOrDelete(insertCommand);

                DriverManager.println("inserted movie actor : "+name+" ("+movieTitle+")");
            }
        }
    }

    public static void getMovieActors(String movieID, ArrayList<HashMap<String, String>> movieInfo, HashMap<String, String> moviesAttributes) throws SQLException {
        String selectMovieActors = "Select actorName from movieActor where movieID = '"+movieID+"'";
        ResultSet rs = executeSelect(selectMovieActors);
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

    public static void deleteMovieActors(String[] actors, String movieID) throws SQLException {
        String deleteMovieActor = "Delete from movieActor where movieID = + '"+movieID+"' and actorName = '";

        for (String actor : actors) {
            executeInsertOrDelete(deleteMovieActor + actor + "'");
        }
    }
}
