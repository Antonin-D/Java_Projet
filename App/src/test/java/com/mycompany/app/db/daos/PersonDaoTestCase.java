package com.mycompany.app.db.daos;

import com.mycompany.app.db.entities.Person;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PersonDaoTestCase {

    private final PersonDao personDao = new PersonDao();

    @BeforeEach
    public void initDb() throws Exception {
        Connection connection = DataSourceFactory.getConnection();
        Statement stmt = connection.createStatement();
        
        stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS person (
                    idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                    lastname VARCHAR(45) NOT NULL,
                    firstname VARCHAR(45) NOT NULL,
                    nickname VARCHAR(45) NOT NULL,
                    phone_number VARCHAR(15) NULL,
                    address VARCHAR(200) NULL,
                    email_address VARCHAR(150) NULL,
                    birth_date DATE NULL
                );""");
        
        stmt.executeUpdate("DELETE FROM person");
        stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='person'");
        
        stmt.executeUpdate("INSERT INTO person(idperson, lastname, firstname, nickname, phone_number, address, email_address, birth_date) "
        + "VALUES (1, 'Doe', 'John', 'Johnny', '0102030405', '123 Fake St', 'john@doe.com', '1990-01-01 12:00:00.000')");
        stmt.executeUpdate("INSERT INTO person(idperson, lastname, firstname, nickname, phone_number, address, email_address, birth_date) "
                + "VALUES (2, 'Smith', 'Jane', 'Janey', NULL, NULL, NULL, NULL)");
        
        stmt.close();
        connection.close();
    }

    @Test
    public void shouldListPersons() {
        List<Person> persons = personDao.listPersons();
        
        assertThat(persons).hasSize(2);
        assertThat(persons).extracting("id", "lastname", "firstname", "nickname")
                .containsOnly(
                        tuple(1, "Doe", "John", "Johnny"),
                        tuple(2, "Smith", "Jane", "Janey")
                );
    }

    @Test
    public void shouldAddPerson() throws Exception {
        Person newPerson = new Person("Wayne", "Bruce", "Batman", "555-0000", "Batcave", "bruce@wayne.com", LocalDate.of(1985, 5, 27));
        Person savedPerson = personDao.addPerson(newPerson);
        
        assertThat(savedPerson.getId()).isNotNull();
       
        try (Connection connection = DataSourceFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE lastname='Wayne'")) {
            
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getString("nickname")).isEqualTo("Batman");
            assertThat(resultSet.next()).isFalse();
        }
    }

    @Test
    public void shouldUpdatePerson() throws Exception {
        Person personToUpdate = personDao.listPersons().get(0);
        personToUpdate.setNickname("NewNick");
        personToUpdate.setPhoneNumber("0908070605");
        
        personDao.updatePerson(personToUpdate);
        
        try (Connection connection = DataSourceFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE idperson=1")) {
            
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getString("nickname")).isEqualTo("NewNick");
            assertThat(resultSet.getString("phone_number")).isEqualTo("0908070605");
        }
    }

    @Test
    public void shouldDeletePerson() throws Exception {
        personDao.deletePerson(1);
        
        List<Person> persons = personDao.listPersons();
        assertThat(persons).hasSize(1);
        assertThat(persons.get(0).getId()).isEqualTo(2);
    }
}