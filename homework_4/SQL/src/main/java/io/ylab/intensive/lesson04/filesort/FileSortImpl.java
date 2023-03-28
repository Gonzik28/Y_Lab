package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FileSortImpl implements FileSorter {
    private static final int BATCH_SIZE = 1_000;
    private DataSource dataSource;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) throws SQLException {
        addValueToTable(data);
        String insertSQL = "SELECT * FROM numbers ORDER BY val DESC";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertSQL);
        ResultSet resultSet = preparedStatement.executeQuery();
        File file = new File("src/main/resources/result.txt");
        try (PrintWriter out = new PrintWriter(file, StandardCharsets.UTF_8)) {
            while (resultSet.next()) {
                out.print(resultSet.getString(1) + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public void addValueToTable(File data) throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        String insertSQL = "INSERT INTO numbers(val) VALUES (?)";
        PreparedStatement number = dataSource.getConnection().prepareStatement(insertSQL);
        try {
            List<String> numbers = Files.readAllLines(Paths.get(String.valueOf(data)), StandardCharsets.UTF_8);
            for (int i = 0; i < numbers.size(); i++) {
                number.setLong(1, Long.parseLong(numbers.get(i)));
                number.addBatch();
                if ((i % BATCH_SIZE == 0) | (i == numbers.size() - 1)) {
                    number.executeBatch();
                    connection.commit();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
