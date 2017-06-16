package Model;

import java.util.ArrayList;

/**
 * Created by hugo on 07/04/2017.
 */
public class MyMoviesDataBase {
    private ArrayList<Movie> movies;
    private String name;

    public MyMoviesDataBase(String dbName) {
        name = dbName;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public Movie getMovie(String id){
        for (Movie movie : movies) {
            if(movie.getId().equals(id))
                return movie;
        }
        return null;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
