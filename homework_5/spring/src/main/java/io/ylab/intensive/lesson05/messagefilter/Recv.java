package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class Recv {
    private static final String PATH = "src/main/resources/data.txt";
    private final static String QUEUE_NAME_INPUT = "input";
    private final static String QUEUE_NAME_OUTPUT = "output";

    public static void main(String[] argv) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        receiveMessage(connection);
    }

    public static void receiveMessage(Connection connection) {
        Message message = new Message();
        try {
            message.fillObsceneLanguage(new File(PATH));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Channel channelSave = connection.createChannel();
            channelSave.queueDeclare(QUEUE_NAME_INPUT, false, false, false, null);
            DeliverCallback deliverCallbackSave = (consumerTag, delivery) -> {
                String text = new String(delivery.getBody());
                sentMessage(connection, message.pullMessage(text));
            };
            channelSave.basicConsume(QUEUE_NAME_INPUT, true, deliverCallbackSave, consumerTag -> {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sentMessage(Connection connection, String result) {
        try (Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME_OUTPUT, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME_OUTPUT, null, result.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
