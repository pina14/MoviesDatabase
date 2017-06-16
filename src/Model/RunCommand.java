package Model;

import Model.Commands.Command;
import Model.SQLAccess.AccessDB;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.SQLException;

/**
 * Created by hugo on 05/05/2017.
 */
public class RunCommand {
    private UserInteraction userInteraction = new UserInteraction();
    private boolean succeed = false;

    public RunCommand(String dbName, String pathDir, Command command, DataBases db){


        exec(command, db, dbName, pathDir);
    }

    public void exec(Command command, DataBases db, String dbName, String pathDir){

        //Get and execute command
        try {
            if(command == null)
                throw new UnsupportedOperationException();

            succeed = command.execute(db, dbName, pathDir);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasSucceed() {
        return succeed;
    }
}
