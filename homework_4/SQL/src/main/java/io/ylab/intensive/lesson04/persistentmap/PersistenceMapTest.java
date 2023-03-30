package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class PersistenceMapTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        PersistentMap persistentMap = new PersistentMapImpl(dataSource);
        persistentMap.init("new number");
        persistentMap.put("One", "Tor");
        persistentMap.put("Two", "Max");
        persistentMap.put("Tree", "Logon");
        persistentMap.put("Seven", "Alex");
        persistentMap.put("Four", "Spider");
        System.out.println(persistentMap.get("Seven"));
        System.out.println(persistentMap.containsKey("Tree"));
        System.out.println(persistentMap.containsKey("Five"));
        System.out.println(persistentMap.getKeys().size());
        persistentMap.remove("Seven");
        System.out.println(persistentMap.getKeys().size());
        persistentMap.clear();
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar,\n"
                + "   value varchar\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
