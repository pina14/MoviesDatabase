package Model.Commands;

import Model.DataBases;
import Model.SQLAccess.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hugo on 07/04/2017.
 */
public class DeleteDatabase extends Command{
    @Override
    public boolean execute(DataBases dataBases, String dbName, String dirName) throws SQLException {
        ArrayList<String> movies = SQLMoviesDataBases.getMoviesFromDataBase(dbName);
        if(movies.isEmpty()) {
            userInteraction.show("Doesn't exist a database with that name!!!!\n");
            return false;
        }

        ArrayList<HashMap<String, String>> moviesInfo = new ArrayList<>();
        HashMap<String, String> genresAttr = new HashMap<>();
        HashMap<String, String> actorsAttr = new HashMap<>();

        for (String movieID : movies) {
            moviesInfo.add(actorsAttr);
            SQLMoviesActors.getMovieActors(movieID, moviesInfo, actorsAttr);
            String[] actors = actorsAttr.get("actorName").split(", ");

            moviesInfo.add(genresAttr);
            SQLMoviesGenres.getMovieGenres(movieID, moviesInfo, genresAttr);
            String[] genres = genresAttr.get("genre").split(", ");

            AccessDB.deleteAllFromThisDB(actors, genres, movieID, dbName);
        }

        //delete database from sql
        SQLDataBases.deleteDataBase(dbName);

        //delete database in java structure
        dataBases.removeDb(dbName);

        userInteraction.show("Database "+dbName+" was deleted\n");
        return true;
    }

    @Override
    public String getTemplate() {
        return "DELETE /database ";
    }

    @Override
    public String getDescription() {
        return "Deletes a database previously created, given a name!";
    }
}
