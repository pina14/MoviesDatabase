package Control;

import Model.AccessDB;
import Model.MovieSerie;
import Model.OMDBQuery;
import View.ViewList;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyMoviesDataBase {

    private static ArrayList<MovieSerie>  movies;

    public static void main(String[] args) throws IOException {
//        movies = OMDBQuery.moviesInformationFromDirectory("F:\\Filmes");
        movies = OMDBQuery.moviesInformationFromDirectory("C:\\Users\\hugo\\Documents\\Vuze Downloads");
//        movies = OMDBQuery.moviesInformationFromName("Passengers");

        //show result in UI
        new ViewList(movies);

        try {
            //config resources to access database
            AccessDB.configDataSource();
            AccessDB.configLogFile();
            AccessDB.configConnection();

            //set auto commit false
            AccessDB.setAutoCommit(false);

            for (MovieSerie movie : movies) {
                //save in database
                AccessDB.insertMovie(movie);                                                      //insert in table movie
                AccessDB.insertActor(movie.getActors());                                          //insert in table actor
                AccessDB.insertGenre(movie.getGenres());                                          //insert in table genre
                AccessDB.insertMovieActor(movie.getID(),movie.getActors(), movie.getTitle());     //insert in table movieActor
                AccessDB.insertMovieGenre(movie.getID(), movie.getGenres(), movie.getTitle());    //insert in table movieGenre
            }

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
        }finally {
            try {
                //close connection
                AccessDB.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
