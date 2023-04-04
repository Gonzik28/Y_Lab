package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);

        personApi.savePerson(1L, "John", "Doe", "Smith");
        personApi.savePerson(2L, "Jane", "Doe", "Smith");
        personApi.savePerson(2L, "Anton", "Doe", "Smith");
        personApi.savePerson(3L, "Don", "Jerad", "Wood");
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
}
