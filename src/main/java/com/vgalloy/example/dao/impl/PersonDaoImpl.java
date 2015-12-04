package com.vgalloy.example.dao.impl;

import com.vgalloy.example.dao.PersonDao;
import com.vgalloy.example.entity.Person;
import com.vgalloy.example.factory.SessionFactoryFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 04/12/15.
 */
public class PersonDaoImpl implements PersonDao {
    private static PersonDao INSTANCE = new PersonDaoImpl();

    private SessionFactory sessionFactory;

    private PersonDaoImpl() {
        this.sessionFactory = SessionFactoryFactory.getSessionFactory();
    }

    @Override
    public void create(Person person) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(person);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Person> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Person> list = session.createCriteria(Person.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public Person getById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Person person = (Person) session.get(Person.class, id);
        session.getTransaction().commit();
        session.close();
        return person;
    }

    @Override
    public void update(Person person) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(person);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Person person = (Person) session.get(Person.class, id);
        session.delete(person);
        session.getTransaction().commit();
        session.close();
    }

    public static PersonDao getInstance() {
        return INSTANCE;
    }

}
