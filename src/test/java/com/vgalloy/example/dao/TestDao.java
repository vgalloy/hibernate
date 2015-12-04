package com.vgalloy.example.dao;

import com.vgalloy.example.dao.impl.PersonDaoImpl;
import com.vgalloy.example.entity.Person;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by vgalloy on 04/12/15.
 */
public class TestDao {

    private static PersonDao personDao = PersonDaoImpl.getInstance();

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

}
