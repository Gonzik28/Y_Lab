package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import java.util.List;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = initMQ();
        PersonApi personApi = new PersonApiImpl(connectionFactory);
        personApi.savePerson(1L, "John", "Doe", "Smith");
        personApi.savePerson(2L, "Jane", "Doe", "Smith");
        personApi.savePerson(2L, "Anton", "Doe", "Smith");
        personApi.savePerson(3L, "Don", "Jaerar", "Wood");
        personApi.deletePerson(1L);
        Thread.sleep(1_000);
        personApi.deletePerson(1L);
        Person person = personApi.findPerson(2L);
        System.out.println(person.toString());
        List<Person> allPersons = personApi.findAll();
        allPersons.forEach(person1 -> {
            System.out.print(person1.toString() + " ");
        });
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}
