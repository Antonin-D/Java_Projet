package com.mycompany.app.service;

import com.mycompany.app.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {
    
    private PersonService personService;

    // @BeforeEach s'exécute automatiquement AVANT chaque test (@Test)
    @BeforeEach
    public void setUp() {
        personService = new PersonService();
    }

    @Test
    public void testAddAndListPersons() {
        // 1. GIVEN : On prépare une personne de test
        Person newPerson = new Person(
            "TestNom", 
            "TestPrenom", 
            "TestSurnom", 
            "0102030405", 
            "10 rue Test", 
            "test@test.com", 
            LocalDate.of(2000, 1, 1)
        );
        
        // 2. WHEN : On exécute la méthode du service (qui appelle le DAO)
        Person addedPerson = personService.addPerson(newPerson);
        
        // 3. THEN : On fait des "Assertions" pour vérifier le résultat
        
        // Vérifie que la personne n'est pas nulle
        assertNotNull(addedPerson, "La personne ajoutée ne devrait pas être null");
        
        // Vérifie que la base de données a bien généré un ID
        assertNotNull(addedPerson.getId(), "L'ID de la personne ne doit pas être null après insertion en BDD");
        
        // Vérifie que les données sont restées correctes
        assertEquals("TestNom", addedPerson.getLastname(), "Le nom doit correspondre à celui inséré");

        // 4. On teste la récupération de la liste
        List<Person> persons = personService.listPersons();
        
        // Vérifie que la liste contient au moins notre personne
        assertTrue(persons.size() > 0, "La liste des personnes ne doit pas être vide");
        
        // On cherche notre personne spécifique dans la liste pour vérifier qu'elle y est bien
        boolean isPersonInDatabase = false;
        for (Person p : persons) {
            if (p.getId().equals(addedPerson.getId())) {
                isPersonInDatabase = true;
                assertEquals("TestPrenom", p.getFirstname(), "Le prénom doit correspondre");
                break;
            }
        }
        
        assertTrue(isPersonInDatabase, "La personne que l'on vient d'ajouter doit se trouver dans la liste de la base de données");
    }
}