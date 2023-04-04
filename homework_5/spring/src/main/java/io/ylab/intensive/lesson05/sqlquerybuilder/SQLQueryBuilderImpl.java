package io.ylab.intensive.lesson05.sqlquerybuilder;


import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {
    private final Connection connection;
    private final DatabaseMetaData databaseMetaData;

    public SQLQueryBuilderImpl(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
        this.databaseMetaData = connection.getMetaData();
    }
    @Override
    public String queryForTable(String tableName) throws SQLException {
        ArrayList<String> colum = new ArrayList();
        ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, new String[] {"TABLE"});
        if(resultSet.next()){
            try(ResultSet columns = databaseMetaData.getColumns(null,null, tableName, null)) {
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    colum.add(columnName);
                }
            }
            String fields = String.join(", ", colum);
            return "SELECT '" + fields.replace(", ", "', '") + "' FROM " + "'" + tableName + "'";
        }else{
            return null;
        }
    }

    @Override
    public List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<>();
        try(ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"})){
            while(resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                tables.add(tableName);
            }
        }
        return tables;
    }
}
