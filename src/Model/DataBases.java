package Model;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by hugo on 07/04/2017.
 */
public class DataBases{
    private HashMap<String, MyMoviesDataBase> dataBases = new HashMap<>();

    public MyMoviesDataBase getDataBase(String name) {
        return dataBases.get(name);
    }

    public void addDataBase(MyMoviesDataBase db, String dbName) {
        dataBases.put(dbName, db);
    }

    public int getSize(){
        return dataBases.size();
    }

    public Collection<MyMoviesDataBase> getDatabases(){
        return  dataBases.values();
    }

    public void removeDb(String dbName) {
        dataBases.remove(dbName);
    }

    public boolean dataBaseExists(String dbName) {
        return dataBases.get(dbName) != null;
    }
}
