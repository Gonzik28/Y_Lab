package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

@Component
public class DataProcessor {
    private static final String QUEUE_NAME_SAVE = "save";
    private static final String QUEUE_NAME_DELETE = "delete";
    DataSource dataSource;
    ConnectionFactory connectionFactory;

    public DataProcessor(DataSource dataSource, ConnectionFactory connectionFactory) {
        this.dataSource = dataSource;
        this.connectionFactory = connectionFactory;
    }

    private static boolean isId(String s) throws NumberFormatException {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void receiveMessage() {
        try {
            com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
            Channel channelSave = connection.createChannel();
            channelSave.queueDeclare(QUEUE_NAME_SAVE, false, false, false, null);
            DeliverCallback deliverCallbackSave = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                Person person = new ObjectMapper().readValue(message, Person.class);
                savePerson(person);
            };
            channelSave.basicConsume(QUEUE_NAME_SAVE, true, deliverCallbackSave, consumerTag -> {
            });

            Channel channelDelete = connection.createChannel();
            channelDelete.queueDeclare(QUEUE_NAME_DELETE, false, false, false, null);
            DeliverCallback deliverCallbackDelete = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                if (isId(message)) {
                    deletePerson(Long.parseLong(message));
                }
            };
            channelDelete.basicConsume(QUEUE_NAME_DELETE, true, deliverCallbackDelete, consumerTag -> {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePerson(Long personId) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT * FROM person WHERE person_id = ?";
            PreparedStatement preparedStatementAll = connection.prepareStatement(sql);
            preparedStatementAll.setLong(1, personId);
            if (preparedStatementAll.executeQuery().next()) {
                String sqlDelete = "DELETE FROM person WHERE person_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
                preparedStatement.setLong(1, personId);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } else {
                System.out.println("An attempt was made to delete, but no data were found");
            }
            preparedStatementAll.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePerson(Person person) {
        try {
            Connection connection = dataSource.getConnection();
            String sqlAll = "SELECT * FROM person WHERE person_id = ?";
            PreparedStatement preparedStatementAll = connection.prepareStatement(sqlAll);
            preparedStatementAll.setLong(1, person.getId());
            String sqlInsert;
            if (preparedStatementAll.executeQuery().next()) {
                sqlInsert = "UPDATE person SET first_name = ?, last_name = ?, middle_name =? WHERE person_id = ?;";
            } else {
                sqlInsert = "INSERT INTO person (first_name, last_name, middle_name, person_id) " +
                        "VALUES (?, ?, ?, ?)";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getMiddleName());
            preparedStatement.setLong(4, person.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            preparedStatementAll.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
