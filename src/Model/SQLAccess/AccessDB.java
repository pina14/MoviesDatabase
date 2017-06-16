package Model.SQLAccess;

import Model.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AccessDB {
    private static File logFile = new File("Logs.txt");
    private static SQLServerDataSource dataSource = new SQLServerDataSource();
    private static Connection conn;

    public static void configDataSource(){
        dataSource.setUser("pina14");
        dataSource.setPassword("moviesPass");
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("myMovies");
    }

    public static void configLogFile(){
        try {
            DriverManager.setLogWriter(new PrintWriter(logFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<HashMap<String, String>> getAllInfoFromThisDB(String dbName) throws SQLException {
        ArrayList<HashMap<String, String>> movieInfo = new ArrayList<>();
        HashMap<String, String> moviesAttributes;
        ArrayList<String> moviesID = SQLMoviesDataBases.getMoviesFromDataBase(dbName);

        for (String movieID : moviesID) {
            moviesAttributes = new HashMap<>();
            SQLMovies.getMovie(movieID, movieInfo, moviesAttributes);
            SQLMoviesGenres.getMovieGenres(movieID, movieInfo, moviesAttributes);
            SQLMoviesActors.getMovieActors(movieID, movieInfo, moviesAttributes);
        }

        return movieInfo;
    }

    public static void insertAll(Movie movie, String dbName) throws SQLException {
        SQLMovies.insertMovie(movie);                                                           //insert in table movie
        SQLActors.insertActor(movie.getActors());                                               //insert in table actor
        SQLGenres.insertGenre(movie.getGenres());                                               //insert in table genre
        SQLMoviesActors.insertMovieActor(movie.getId(),movie.getActors(), movie.getTitle());    //insert in table movieActor
        SQLMoviesGenres.insertMovieGenre(movie.getId(), movie.getGenres(), movie.getTitle());   //insert in table movieGenre
        SQLDataBases.insertDataBase(dbName);                                                    //insert in table dBase
        SQLMoviesDataBases.insertMovieDataBase(movie.getId(), dbName);                          //insert in table movieDataBase
    }

    public static void deleteAllFromThisDB(String[] actors, String[] genres, String movieId, String dbName) throws SQLException {
        boolean movieIsDuplicate = checkIfMovieIsDuplicate(movieId);

        if(!movieIsDuplicate) {
            SQLActors.deleteActors(actors);
            SQLGenres.deleteGenres(genres);
            SQLMoviesActors.deleteMovieActors(actors, movieId);
            SQLMoviesGenres.deleteMovieGenres(genres, movieId);
            SQLMovies.deleteMovies(movieId);
        }

        SQLMoviesDataBases.deleteMoviesDataBase(dbName, movieId);
    }

    public static ArrayList<String> getAllDbNames(){
        ArrayList<String> res = new ArrayList<>();
        String getDbNames = "SELECT * FROM dBase";
        try {
            ResultSet rs = executeSelect(getDbNames);

            while (rs.next())
                res.add(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    private static boolean checkIfMovieIsDuplicate(String movieId) throws SQLException {
        String selectMovieCount = "Select count(movieID) from movieDataBase where movieID = '"+movieId+"'";

        ResultSet rs = executeSelect(selectMovieCount);

        rs.next();
        if(rs.getInt(1) <= 1)
            return false;

        return true;
    }

    protected static ResultSet executeSelect(String command) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(command);
        ResultSet rs =  ps.executeQuery();

        return rs;
    }

    protected static boolean isInDB(ResultSet rs) throws SQLException {
        return rs.next();
    }


    protected static void executeInsertOrDelete(String command) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(command);
        ps.executeUpdate();

        //close resources
        ps.close();
    }

    public static void configConnection() throws SQLServerException {
        conn = dataSource.getConnection();
    }

    public static void closeConnection() throws SQLException {
        conn.close();
    }

    public static void setAutoCommit(boolean commitState) throws SQLException {
        conn.setAutoCommit(commitState);
    }

    public static void execRollback() throws SQLException {
        conn.rollback();
    }
}