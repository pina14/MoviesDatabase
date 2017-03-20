package Control;

import Model.AccessDB;
import Model.MovieSerie;
import Model.OMDBQuery;
import View.PanelTowerFrame;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyMoviesDataBase {

    private static ArrayList<MovieSerie>  movies;

    public static void main(String[] args) throws IOException {
        movies = OMDBQuery.moviesInformationFromDirectory("G:\\Filmes");
//        OMDBQuery.moviesInformationFromName("Birdman or (The Unexpected Virtue of Ignorance) 2014 1080p BRRip x264 DTS-JYK")
//        movies.addAll(OMDBQuery.moviesInformationFromName("Birdman or (The Unexpected Virtue of Ignorance) 2014 1080p BRRip x264 DTS-JYK"));

        PanelTowerFrame panel = new PanelTowerFrame(movies.size(), movies);

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

            //close connection
            AccessDB.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
