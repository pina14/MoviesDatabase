package Model.SQLAccess;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hugo on 07/04/2017.
 */
public class SQLDataBases extends AccessDB{

    public static void insertDataBase(String dbName) throws SQLException {
        //check if it's already in database
        String selectCommand = "select * from dBase where name = '"+dbName+"'";
        ResultSet rs = executeSelect(selectCommand);
        boolean isInDB = isInDB(rs);

        //if it's not in dataBase, insert!
        if(!isInDB) {
            String command = "insert into dBase (name) " +
                    "values('"+dbName+"')";

            executeInsertOrDelete(command);
            DriverManager.println("inserted Database with name : "+dbName);
        }
    }

    public static void deleteDataBase(String dbName) throws SQLException {
        String deleteDataBase = "Delete from dBase where name = '"+dbName+"'";

        executeInsertOrDelete(deleteDataBase);
    }
}
