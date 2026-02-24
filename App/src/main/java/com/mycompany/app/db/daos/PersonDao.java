package com.mycompany.app.db.daos;

import com.mycompany.app.db.entities.Person;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PersonDao {

    public List<Person> listPersons() {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = DataSourceFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet results = stmt.executeQuery("SELECT * FROM person")) {
            
            while (results.next()) {
                Person person = mapResultSetToPerson(results);
                persons.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listing persons", e);
        }
        return persons;
    }

    public Person addPerson(Person person) {
        String sql = "INSERT INTO person(lastname, firstname, nickname, phone_number, address, email_address, birth_date) VALUES(?,?,?,?,?,?,?)";
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setPersonStatementParameters(stmt, person);
            stmt.executeUpdate();
            
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    person.setId(keys.getInt(1));
                }
                return person;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding person", e);
        }
    }

    public void updatePerson(Person person) {
        String sql = "UPDATE person SET lastname=?, firstname=?, nickname=?, phone_number=?, address=?, email_address=?, birth_date=? WHERE idperson=?";
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            setPersonStatementParameters(stmt, person);
            stmt.setInt(8, person.getId()); // Le 8ème paramètre est l'ID pour la clause WHERE
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating person", e);
        }
    }

    public void deletePerson(Integer id) {
        String sql = "DELETE FROM person WHERE idperson=?";
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting person", e);
        }
    }


    private Person mapResultSetToPerson(ResultSet results) throws SQLException {
        Person person = new Person();
        person.setId(results.getInt("idperson"));
        person.setLastname(results.getString("lastname"));
        person.setFirstname(results.getString("firstname"));
        person.setNickname(results.getString("nickname"));
        person.setPhoneNumber(results.getString("phone_number"));
        person.setAddress(results.getString("address"));
        person.setEmailAddress(results.getString("email_address"));
        
        // Gestion des dates qui peuvent être nulles
        Date sqlDate = results.getDate("birth_date");
        if (sqlDate != null) {
            person.setBirthDate(sqlDate.toLocalDate());
        }
        return person;
    }

    private void setPersonStatementParameters(PreparedStatement stmt, Person person) throws SQLException {
        stmt.setString(1, person.getLastname());
        stmt.setString(2, person.getFirstname());
        stmt.setString(3, person.getNickname());
        stmt.setString(4, person.getPhoneNumber());
        stmt.setString(5, person.getAddress());
        stmt.setString(6, person.getEmailAddress());
        
        if (person.getBirthDate() != null) {
            stmt.setDate(7, Date.valueOf(person.getBirthDate()));
        } else {
            stmt.setNull(7, java.sql.Types.DATE);
        }
    }
}