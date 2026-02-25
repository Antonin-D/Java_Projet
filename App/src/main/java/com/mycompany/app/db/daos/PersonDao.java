package com.mycompany.app.db.daos;

import com.mycompany.app.model.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {

    public List<Person> listPersons() {
        List<Person> listPersons = new ArrayList<>();
        try (Connection connection = DataSourceFactory.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet results = statement.executeQuery("SELECT * FROM person")) {
                    while (results.next()) {
                        Person person = new Person(
                            results.getInt("idperson"),
                            results.getString("lastname"),
                            results.getString("firstname"),
                            results.getString("nickname"),
                            results.getString("phone_number"),
                            results.getString("address"),
                            results.getString("email_address"),
                            results.getDate("birth_date") != null ? results.getDate("birth_date").toLocalDate() : null
                        );
                        listPersons.add(person);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des personnes", e);
        }
        return listPersons;
    }

    public Person addPerson(Person person) {
        try (Connection connection = DataSourceFactory.getConnection()) {
            String sqlQuery = "INSERT INTO person(lastname, firstname, nickname, phone_number, address, email_address, birth_date) VALUES(?,?,?,?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, person.getLastname());
                statement.setString(2, person.getFirstname());
                statement.setString(3, person.getNickname());
                statement.setString(4, person.getPhoneNumber());
                statement.setString(5, person.getAddress());
                statement.setString(6, person.getEmailAddress());
                
                if (person.getBirthDate() != null) {
                    statement.setDate(7, Date.valueOf(person.getBirthDate()));
                } else {
                    statement.setNull(7, java.sql.Types.DATE);
                }

                statement.executeUpdate();
                try (ResultSet ids = statement.getGeneratedKeys()) {
                    if (ids.next()) {
                        person.setId(ids.getInt(1));
                        return person;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de la personne", e);
        }
        return null;
    }
}