package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.ConnectionFactory;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@ComponentScan("io.ylab.intensive.lesson05.messagefilter")
public class Config {
    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
//        dataSource.setPassword("Indiwid753!");
        dataSource.setDatabaseName("postgres");
        dataSource.setPortNumber(5432);
        return dataSource;
    }

    @Bean
    public DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists obscene_language;"
                + "create table if not exists obscene_language (\n"
                + "word varchar\n"
                + ")";
        DataSource dataSource = dataSource();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(ddl);
        }
        return dataSource;
    }
}
