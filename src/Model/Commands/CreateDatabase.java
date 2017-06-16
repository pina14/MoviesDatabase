package Model.Commands;

import Model.*;
import Model.SQLAccess.AccessDB;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by hugo on 07/04/2017.
 */
public class CreateDatabase extends Command{
    @Override
    public boolean execute(DataBases dataBases, String dbName, String dirName) {
        ArrayList<Movie> movies;

        if(dataBases.dataBaseExists(dbName))
            return false;

        //create new instance of database with given dbName
        MyMoviesDataBase db = new MyMoviesDataBase(dbName);

        //keeping user up to date
        userInteraction.show("Creating database "+dbName+":");

        //Get movie info from SQL
        movies = OMDBQuery.moviesInformationFromDirectory(dirName);

        if(movies.size() == 0) {
            userInteraction.show("Empty or not a valid directory!!!!\n");
            return false;
        }

        //Adding database
        db.setMovies(movies);               //create new database in java
        dataBases.addDataBase(db, dbName);  //add database to java databases structure
        insertInDatabase(movies, dbName);   //save database in SQL

        userInteraction.show("Created and saved database from \"" + dirName + "\" with name \"" + dbName + "\"!\n");

        return true;
    }

    private void insertInDatabase(ArrayList<Movie> movies, String dbName) {
        try {
            //set auto commit false
            AccessDB.setAutoCommit(false);

            for (Movie movie : movies)
                AccessDB.insertAll(movie, dbName);      //insert all in SQL

            //commit database alterations
            AccessDB.setAutoCommit(true);

        } catch (SQLException e) {
            try {
                //Rollback changes and get back to previous state
                AccessDB.execRollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public String getTemplate() {
        return "CREATE /database";
    }

    @Override
    public String getDescription() {
        return "Creates a new movie/series database given a directory to read from, and saves it!";
    }
}
