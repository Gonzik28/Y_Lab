package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class MessageFilterApp {
    private final static String QUEUE_NAME_INPUT = "input";
    private final static String QUEUE_NAME_OUTPUT = "output";
    private static final String PATH = "src/main/resources/data.txt";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        try {
            Connection connection = applicationContext.getBean(ConnectionFactory.class).newConnection();
            applicationContext.start();
            Message message = new Message();
            message.fillObsceneLanguage(new File(PATH));
            sentMessage(connection);
            try {
                Thread.sleep(3_000);
                receiveMessage(connection);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            channelSave.queueDeclare(QUEUE_NAME_OUTPUT, false, false, false, null);
            DeliverCallback deliverCallbackSave = (consumerTag, delivery) -> {
                System.out.println(new String(delivery.getBody()));
            };
            channelSave.basicConsume(QUEUE_NAME_OUTPUT, true, deliverCallbackSave, consumerTag -> {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sentMessage(Connection connection) {
        try (Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME_INPUT, false, false, false, null);
            Scanner scanner = new Scanner(System.in);
            StringBuffer text = new StringBuffer();
            int textSize;
            do {
                String a = scanner.nextLine();
                text.append(a);
                text.append('\n');
                textSize = a.length();
            } while (textSize > 0);
            channel.basicPublish("", QUEUE_NAME_INPUT, null, text.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}

