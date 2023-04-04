package io.ylab.intensive.lesson05.eventsourcing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.DbUtil;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Component
public class PersonApiImpl implements PersonApi{
    private static final String QUEUE_NAME_SAVE = "save";
    private static final String QUEUE_NAME_DELETE = "delete";
    DataSource dataSource = DbUtil.buildDataSource();
    private final ConnectionFactory factory;

    public PersonApiImpl(ConnectionFactory connectionFactory) throws SQLException {
        this.factory = connectionFactory;
    }

    @Override
    public void deletePerson(Long personId) {
        try (Channel channel = factory.newConnection().createChannel()) {
            channel.queueDeclare(QUEUE_NAME_DELETE, false, false, false, null);
            String message = personId.toString();
            channel.basicPublish("", QUEUE_NAME_DELETE, null, message.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        try (Channel channel = factory.newConnection().createChannel()) {
            channel.queueDeclare(QUEUE_NAME_SAVE, false, false, false, null);
            Person person = new Person(personId, firstName, lastName, middleName);
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(person);
            channel.basicPublish("", QUEUE_NAME_SAVE, null, jsonString.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person findPerson(Long personId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM person WHERE person_id = ?")) {
            stmt.setLong(1, personId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Person person = new Person(rs.getLong("person_id"), rs.getString("first_name"),
                            rs.getString("last_name"), rs.getString("middle_name"));
                    stmt.close();
                    conn.close();
                    return person;
                }
                stmt.close();
                conn.close();
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> findAll() {
        List<Person> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM person")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new Person(rs.getLong("person_id"), rs.getString("first_name"),
                            rs.getString("last_name"), rs.getString("middle_name")));
                }
                stmt.close();
                conn.close();
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
