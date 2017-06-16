package Model.Commands;

import Model.DataBases;
import Model.SQLAccess.AccessDB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hugo on 07/04/2017.
 */
public class ShowDatabase extends Command{
    @Override
    public boolean execute(DataBases dataBases, String dbName, String dirName) throws SQLException {
        ArrayList<HashMap<String, String>> moviesInfo = AccessDB.getAllInfoFromThisDB(dbName);

        if(moviesInfo.isEmpty()) {
            userInteraction.show("Doesn't exist a database with that name!!!!\n");
            return false;
        }

        return true;
    }

    @Override
    public String getTemplate() {
        return "SHOW /database/{name}";
    }

    @Override
    public String getDescription() {
        return "Shows a previously created database, given a name!";
    }
}
