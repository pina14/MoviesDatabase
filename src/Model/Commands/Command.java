package Model.Commands;

import Model.DataBases;
import Model.UserInteraction;
import java.sql.SQLException;

public abstract class Command {
    protected UserInteraction userInteraction = new UserInteraction();

    //return if executed with success
    public abstract boolean execute(DataBases dataBases, String dbName, String dirName) throws SQLException;

    public abstract String getTemplate();

    public abstract String getDescription();
}
