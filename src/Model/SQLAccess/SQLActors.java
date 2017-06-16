package Model.SQLAccess;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hugo on 07/04/2017.
 */
public class SQLActors extends AccessDB{

    public static void insertActor(String[] names) throws SQLException {
        String insertCommand, selectCommand;
        boolean isInDB;

        if(names == null)
            return;

        for (String name : names) {
            //eliminate first char if it's a space
            if(name.charAt(0) == ' ')
                name = name.replaceFirst(" ", "");

            selectCommand = "select actorName from actor where actorName = '"+name+"'";
            ResultSet rs = executeSelect(selectCommand);
            isInDB = isInDB(rs);

            //check if it's already in database, if it isn't, insert!
            if(!isInDB) {
                insertCommand = "insert into actor (actorName) " +
                        "values('" + name + "')";

                executeInsertOrDelete(insertCommand);
                DriverManager.println("inserted actor : "+name);
            }
        }
    }

    public static void deleteActors(String[] actors) throws SQLException {
        String selectActor = "Select count(actorName) from movieActor where actorName = '";
        String deleteActor = "Delete from actor where actorName = '";

        ResultSet rs = null;
        for (String actor : actors) {
            rs = executeSelect(selectActor + actor + "'");
            rs.next();
            int count = rs.getInt(1);
            if (count == 1) {
                executeInsertOrDelete(deleteActor + actor + "'");
            }

        }
    }
}
