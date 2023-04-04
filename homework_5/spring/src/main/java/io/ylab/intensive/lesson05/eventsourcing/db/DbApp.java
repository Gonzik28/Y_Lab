package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class DbApp {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        DataProcessor dataProcessor = new DataProcessor(applicationContext.getBean(DataSource.class),
                applicationContext.getBean(ConnectionFactory.class));
        dataProcessor.receiveMessage();
    }
}
