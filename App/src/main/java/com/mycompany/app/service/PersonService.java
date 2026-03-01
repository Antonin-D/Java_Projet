package com.mycompany.app.service;

import com.mycompany.app.db.daos.PersonDao;
import com.mycompany.app.db.entities.Person;
import java.util.List;

public class PersonService {
    
    private PersonDao personDao;

    public PersonService() {
        this.personDao = new PersonDao();
    }

    public List<Person> listPersons() {
        return personDao.listPersons();
    }

    public Person addPerson(Person person) {
        return personDao.addPerson(person);
    }

    public void updatePerson(Person person) {
        personDao.updatePerson(person);
    }

    public void deletePerson(Integer id) {
        personDao.deletePerson(id);
    }
}