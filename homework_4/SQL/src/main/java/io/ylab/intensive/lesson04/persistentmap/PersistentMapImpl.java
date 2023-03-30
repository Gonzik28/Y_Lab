package io.ylab.intensive.lesson04.persistentmap;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentMapImpl implements PersistentMap {
    private DataSource dataSource;
    private String name;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) throws SQLException {
        this.name = name;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        String sqlInsert = "SELECT value FROM persistent_map WHERE key=?";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlInsert);
        preparedStatement.setString(1, key);
        ResultSet rs = preparedStatement.executeQuery();
        boolean result = false;
        if (rs.next()) {
            result = true;
        }
        preparedStatement.close();
        return result;
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> keys = new ArrayList<>();
        String sqlInsert = "SELECT key FROM persistent_map";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlInsert);
        ResultSet resultSet = preparedStatement.executeQuery();
        String result = "";
        while (resultSet.next()) {
            result = resultSet.getString(1);
            keys.add(result);
        }
        preparedStatement.close();
        return keys;
    }

    @Override
    public String get(String key) throws SQLException {
        String sqlInsert = "SELECT value FROM persistent_map WHERE key=?";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlInsert);
        preparedStatement.setString(1, key);
        ResultSet rs = preparedStatement.executeQuery();
        String result = "";
        if (rs.next()) {
            result = rs.getString(1);
        }
        preparedStatement.close();
        return result;
    }

    @Override
    public void remove(String key) throws SQLException {
        String sqlInsert = "DELETE FROM persistent_map WHERE key=?";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlInsert);
        preparedStatement.setString(1, key);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void put(String key, String value) throws SQLException {
        String sqlInsert = "INSERT INTO persistent_map (map_name, key, value) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlInsert);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, key);
        preparedStatement.setString(3, value);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void clear() throws SQLException {
        String sqlInsert = "DELETE FROM persistent_map";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlInsert);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
