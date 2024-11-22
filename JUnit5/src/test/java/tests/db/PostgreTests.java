package tests.db;

import entities.Person;
import helpers.CustomAssertions;
import helpers.db.classic.DbClient;
import helpers.db.classic.ResponseFormat;
import org.junit.jupiter.api.Test;

import java.util.List;

import static config.PropsJUnit.propsDb;

public class PostgreTests extends BaseDbTest {

    @Test
    void createDbTest() {
        String createDb = " drop table if exists person;" +
                "create table person (" +
                "person_id bigint primary key," +
                "first_name varchar," +
                "last_name varchar," +
                "middle_name varchar" +
                ");";

        String addPerson = "INSERT INTO person (person_id, first_name, last_name, middle_name) VALUES (1, 'Ivan', 'Ivanov', 'Ivanich');";
        String getPerson = "SELECT * FROM person WHERE person_id=1;";
        String getPersons = "SELECT * FROM person;";
        String deletePerson = "DELETE FROM person WHERE person_id=1;";

        String deleteDb = "drop table person;";

        dbClient.executeUpdate(createDb);

        Person person = dbClient.executeQuery(getPerson, ResponseFormat.POJO, Person.class);
        List<Person> persons = dbClient.executeQuery(getPersons, ResponseFormat.LIST_OF_POJO, Person.class);

        System.out.println(person.toString());
        CustomAssertions.assertTrue(person.equals(persons.get(0)),
                "Полученный из таблицы пользователь с id=1 отличается от первого в списке");

        dbClient.executeUpdate(deleteDb);
    }
}