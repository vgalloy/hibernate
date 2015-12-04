package com.vgalloy.example.dao;

import com.vgalloy.example.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by vgalloy on 04/12/15.
 *
 * /!\ Attention ici un context transactionnel est ouvert par Test !
 * cf : les deux derniers test
 */

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring-context.xml" })
public class TestDao {
    @Autowired
    private PersonDao personDao;

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

    /**
     * Dans le cas de figure ou le context n'est pas fermé, les objets restent attachés à la session et sont donc
     * modifiés directement en base.
     */

    @Test
    public void testAttachDetach() {
        //GIVEN
        Person person = new Person("Vincent");
        personDao.create(person);

        //WHEN
        person.setName("Galloy");
        
        //THEN
        assertEquals(personDao.getById(person.getId()), person);
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
        assertEquals(personDao.getById(person.getId()), person);
    }

}
