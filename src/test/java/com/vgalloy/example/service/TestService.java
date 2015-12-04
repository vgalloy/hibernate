package com.vgalloy.example.service;

import com.vgalloy.example.entity.Person;
import com.vgalloy.example.service.impl.PersonServiceImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by vgalloy on 04/12/15.
 */
public class TestService {

    private static PersonService personService = PersonServiceImpl.getInstance();

    @Test
    public void testCreate() {
        // GIVEN
        Person person = new Person("vincent");

        // WHEN
        personService.create(person);

        // THEN
        assertNotNull(person.getId());
        assertEquals(personService.getById(person.getId()), person);
    }

    @Test
    public void testDelete() {
        // GIVEN
        Person person = new Person("vincent");
        personService.create(person);

        // WHEN
        personService.delete(person.getId());

        // THEN
        assertNull(personService.getById(person.getId()));
    }

    @Test
    public void testGetAll() {
        //GIVEN
        Person person = new Person("vincent");
        List<Person> list1 = personService.getAll();

        //WHEN
        personService.create(person);

        //THEN
        List<Person> list2 = personService.getAll();
        list1.add(person);
        assertEquals(list1, list2);
    }

    @Test
    public void testUpdate() {
        //GIVEN
        Person person = new Person("vincent");
        personService.create(person);
        String newName = "Galloy";
        person.setName(newName);

        //WHEN
        personService.update(person);

        //THEN
        assertEquals(personService.getById(person.getId()).getName(), newName);
    }

    @Test
    public void testAttachDetach() {
        //GIVEN
        Person person = new Person("vincent");
        personService.create(person);

        //WHEN
        person.setName("Galloy");

        //THEN
        assertNotEquals(personService.getById(person.getId()), person);
    }

    @Test
    public void testAttachDetach2() {
        //GIVEN
        Person person = new Person("vincent");
        personService.create(person);

        //WHEN
        person.setName("Galloy");
        personService.update(person);
        person.setName("NewName");

        //THEN
        assertNotEquals(personService.getById(person.getId()), person);
    }

}
