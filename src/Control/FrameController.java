package Control;

import Model.DataBases;
import Model.Movie;
import Model.MyMoviesDataBase;
import Model.SQLAccess.AccessDB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hugo on 04/05/2017.
 */
public class FrameController {
    protected static DataBases dbs = new DataBases();

    public static void main(String[] args){
        MainFrameController mainFrameController = new MainFrameController();
        mainFrameController.show();
    }

    static {
        //config resources to access database
        AccessDB.configDataSource();
        AccessDB.configLogFile();
        try {
            AccessDB.configConnection();
            initDatabaseInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initDatabaseInfo() throws SQLException {
        initDatabaseNames();
        initDatabases();
    }

    private static void initDatabases() throws SQLException {
        for (MyMoviesDataBase database : dbs.getDatabases()) {
            initMoviesFromDb(database);
        }
    }

    private static void initMoviesFromDb(MyMoviesDataBase database) throws SQLException {
        ArrayList<HashMap<String, String>> moviesInfo = AccessDB.getAllInfoFromThisDB(database.getName());
        ArrayList<Movie> res = new ArrayList<>();
        for (HashMap<String, String> movieInfo : moviesInfo) {
            res.add(initMovieInfo(movieInfo));
        }

        database.setMovies(res);
    }

    private static Movie initMovieInfo(HashMap<String, String> movieInfo) {
        String genre = movieInfo.get("genre"), actor = movieInfo.get("actorName");
        String[] genres = null, actors = null;
        if(genre != null)
            genres = genre.split(", ");
        if(actor != null)
            actors = actor.split(", ");

        return new Movie(movieInfo.get("ID"), movieInfo.get("type"), movieInfo.get("title"), movieInfo.get("released"), genres,
                actors, movieInfo.get("plot"), movieInfo.get("imdbRating"),
                movieInfo.get("runTime"), movieInfo.get("poster"));
    }

    private static void initDatabaseNames() {
        ArrayList<String> dbNames = AccessDB.getAllDbNames();

        for (String dbName : dbNames)
            dbs.addDataBase(new MyMoviesDataBase(dbName), dbName);
    }
}

