package Model.SQLAccess;

import Model.Movie;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by hugo on 07/04/2017.
 */
public class SQLMoviesDataBases extends AccessDB{

    public static void insertMovieDataBase(String movieId, String dbName) throws SQLException {
        //check if it's already in database
        String selectCommand = "select * from movieDataBase where movieID = '"+movieId+"' and dbName = '"+dbName+"'";
        ResultSet rs = executeSelect(selectCommand);
        boolean isInDB = isInDB(rs);

        //if it's not in dataBase, insert!
        if(!isInDB) {
            String command = "insert into movieDataBase (movieID, dbName) " +
                    "values('"+movieId+"', '"+dbName+"')";

            executeInsertOrDelete(command);
            DriverManager.println("inserted movie "+movieId+" in database "+dbName);
        }
    }

    public static ArrayList<String> getMoviesFromDataBase(String dbName) throws SQLException {
        String selectMovie = "Select movieID from movieDataBase where dbName = '"+dbName+"'";
        ResultSet rs = executeSelect(selectMovie);
        ResultSetMetaData metaData = rs.getMetaData();
        int numCol = metaData.getColumnCount();
        ArrayList<String> moviesID = new ArrayList<>();

        while (rs.next()){
            for (int i = 1; i <= numCol; i++)
                moviesID.add(rs.getString(i));
//                System.out.println(metaData.getColumnLabel(i)+": "+rs.getString(i)+"\n");
        }
        return moviesID;
    }

    public static void getMovieDataBase(Movie movie) throws SQLException {
        String selectMovie = "Select dbName from movieDataBase where movieID = '"+movie.getId()+"'";
        ResultSet rs = executeSelect(selectMovie);
        ResultSetMetaData metaData = rs.getMetaData();
        int numCol = metaData.getColumnCount();

        while (rs.next()){
            for (int i = 1; i <= numCol; i++)
                System.out.println(metaData.getColumnLabel(i)+": "+rs.getString(i)+"\n");
        }
    }

    public static void deleteMoviesDataBase(String dbName, String movieId) throws SQLException {
        String deleteDataBase = "Delete from movieDataBase where movieID = + '"+movieId+"' and dbName = '"+dbName+"'";

        executeInsertOrDelete(deleteDataBase);
    }
}
