package Model;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;

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

    public static void insertMovie(MovieSerie movie) throws SQLException {
        //check if it's already in database
        String selectCommand = "select ID from movie where ID = '"+movie.getId()+"'";
        boolean isInDB = executeSelect(selectCommand);

        //if it's not in dataBase, insert!
        if(!isInDB) {
            String command = "insert into movie (ID, title, type, plot, released, imdbRating, runtime, poster) " +
                    "values('"+movie.getID()+"', '"+movie.getTitle()+"', '"+movie.getType()+"', \'"+movie.getPlot()+"\', '"
                    +movie.getReleased()+"', "+movie.getImdbRating()+", "+movie.getRuntime()+", '"+movie.getPosterLink()+"')";

            executeInsert(command);
            DriverManager.println("inserted movie/series with title : "+movie.getTitle());
        }
    }

    public static void insertActor(String[] names) throws SQLException {
        String insertCommand, selectCommand;
        boolean isInDB;

        for (String name : names) {
            //eliminate first char if it's a space
            if(name.charAt(0) == ' ')
                name = name.replaceFirst(" ", "");

            selectCommand = "select actorName from actor where actorName = '"+name+"'";
            isInDB =  executeSelect(selectCommand);

            //check if it's already in database, if it isn't, insert!
            if(!isInDB) {
                 insertCommand = "insert into actor (actorName) " +
                        "values('" + name + "')";

                executeInsert(insertCommand);
                DriverManager.println("inserted actor : "+name);
            }
        }
    }

    public static void insertGenre(String[] genres) throws SQLException {
        String insertCommand, selectCommand;
        boolean isInDB;

        for (String genre : genres) {
            //eliminate first char if it's a space
            if(genre.charAt(0) == ' ')
                genre = genre.replaceFirst(" ", "");

            selectCommand = "select genre from genre where genre = '"+genre+"'";
            isInDB = executeSelect(selectCommand);

            //check if it's already in database, if it isn't, insert!
            if(!isInDB) {
                insertCommand = "insert into genre (genre) " +
                        "values('" + genre + "')";

                executeInsert(insertCommand);
                DriverManager.println("inserted genre : "+genre);
            }
        }
    }

    public static void insertMovieActor(String movieID, String[] actorsNames, String movieTitle) throws SQLException {
        String insertCommand, selectCommand;
        boolean isInDB;

        for (String name : actorsNames) {
            //eliminate first char if it's a space
            if(name.charAt(0) == ' ')
                name = name.replaceFirst(" ", "");

            selectCommand = "select * from movieActor where movieID = '"+movieID+"' and actorName = '"+name+"'";
            isInDB = executeSelect(selectCommand);

            //check if it's already in database, if it isn't, insert!
            if(!isInDB) {
                insertCommand = "insert into movieActor (movieID, actorName, movieTitle) " +
                        "values('" + movieID + "','" + name + "','" + movieTitle + "')";

                executeInsert(insertCommand);

                DriverManager.println("inserted movie actor : "+name+" ("+movieTitle+")");
            }
        }
    }

    public static void insertMovieGenre(String movieID, String[] genres, String movieTitle) throws SQLException {
        String insertCommand, selectCommand;
        boolean isInDB;

        for (String genre : genres) {
            //eliminate first char if it's a space
            if(genre.charAt(0) == ' ')
                genre = genre.replaceFirst(" ", "");

            selectCommand = "select * from movieGenre where movieID = '"+movieID+"' and genre = '"+genre+"'";
            isInDB = executeSelect(selectCommand);

            //check if it's already in database, if it isn't, insert!
            if(!isInDB) {
                insertCommand = "insert into movieGenre (movieID, genre, movieTitle) " +
                        "values('" + movieID + "','" + genre + "','" + movieTitle + "')";

                executeInsert(insertCommand);
                DriverManager.println("inserted movie genre : "+genre+" ("+movieTitle+")");
            }
        }
    }

    private static boolean executeSelect(String command) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(command);
        ResultSet rs =  ps.executeQuery();
        boolean isInDB = rs.next();

        //close resources
        ps.close();

        return isInDB;
    }


    private static void executeInsert(String command) throws SQLException {
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
}