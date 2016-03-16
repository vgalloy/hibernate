package com.vgalloy.example.dao;

import javax.persistence.EntityManager;

import com.vgalloy.example.dao.impl.PersonDaoImpl;
import com.vgalloy.example.entity.Person;
import com.vgalloy.example.factory.EntityManagerFactoryFactory;

import org.hibernate.Session;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by vgalloy on 04/12/15.
 */
public class TestDao {

    private static PersonDao personDao = PersonDaoImpl.getInstance();

//    @Test
//    public void tmp() {
//        assertNotNull(personDao.getById(26L));
//    }

    @Test
    public void testCreate() {
        // GIVEN
        Person person = new Person("Vincent");

        // WHEN
        personDao.create(person);

        // THEN
        assertNotNull(person.getId());
        assertEquals(personDao.getById(person.getId()), person);
    }

    @Test
    public void testDelete() {
        // GIVEN
        Person person = new Person("Vincent");
        personDao.create(person);

        // WHEN
        personDao.delete(person.getId());

        // THEN
        assertNull(personDao.getById(person.getId()));
    }

    @Test
    public void testGetAll() {
        //GIVEN
        Person person = new Person("Vincent");
        List<Person> list1 = personDao.getAll();

        //WHEN
        personDao.create(person);

        //THEN
        List<Person> list2 = personDao.getAll();
        list1.add(person);
        assertEquals(list1, list2);
    }

    @Test
    public void testUpdate() {
        //GIVEN
        Person person = new Person("Vincent");
        personDao.create(person);
        String newName = "Galloy";
        person.setName(newName);

        //WHEN
        personDao.update(person);

        //THEN
        assertEquals(personDao.getById(person.getId()).getName(), newName);
    }

    @Test
    public void testAttachDetach() {
        //GIVEN
        Person person = new Person("Vincent");
        personDao.create(person);

        //WHEN
        person.setName("Galloy");
        
        //THEN
        assertNotEquals(personDao.getById(person.getId()), person);
    }

    @Test
    public void testAttachDetach2() {
        //GIVEN
        Person person = new Person("Vincent");
        personDao.create(person);

        //WHEN
        person.setName("Galloy");
        personDao.update(person);
        person.setName("NewName");

        //THEN
        assertNotEquals(personDao.getById(person.getId()), person);
    }

    @Test
    public void fullTest() {
        EntityManager entityManager = EntityManagerFactoryFactory.getEntityManagerFactory().createEntityManager();

        Person person = new Person("Vincent");
        entityManager.persist(person);

        assertNull(person.getId()); // Le context n'ayant pas été ouvert, il n'y a pas de persistance
        entityManager.close();
        assertNull(person.getId()); // il ne s'est rien passé
        try {
            entityManager.persist(person);
            fail("Exception should occured");
        } catch (IllegalStateException expected) {

        }
        entityManager = EntityManagerFactoryFactory.getEntityManagerFactory().createEntityManager();
        EntityManager entityManager2 = EntityManagerFactoryFactory.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        assertNotNull(person.getId()); // On est dans un context du coup le champ est setter mais on est pas passé en base
        assertNotNull(entityManager.find(Person.class, person.getId())); // On peut trouver l'objet dans la session
        assertNull(entityManager2.find(Person.class, person.getId()));
        entityManager.getTransaction().commit();
        assertNull(entityManager2.find(Person.class, person.getId())); // L'entityManager ne trouve rien car il a été crée avant l'insertion

        EntityManager entityManager3 = EntityManagerFactoryFactory.getEntityManagerFactory().createEntityManager();
        assertNotNull(entityManager3.find(Person.class, person.getId()));
    }

}
