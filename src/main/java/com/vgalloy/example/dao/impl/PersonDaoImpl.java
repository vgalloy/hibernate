package com.vgalloy.example.dao.impl;

import com.vgalloy.example.dao.PersonDao;
import com.vgalloy.example.entity.Person;
import com.vgalloy.example.factory.SessionFactoryFactory;
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
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(person);
        sessionFactory.getCurrentSession().getTransaction().commit();
    }

    @Override
    public List<Person> getAll() {
        sessionFactory.getCurrentSession().beginTransaction();
        List<Person> list =  sessionFactory.getCurrentSession().createCriteria(Person.class).list();
        sessionFactory.getCurrentSession().getTransaction().commit();
        return list;
    }

    @Override
    public Person getById(Long id) {
        sessionFactory.getCurrentSession().beginTransaction();
        Person person = (Person) sessionFactory.getCurrentSession().get(Person.class, id);
        sessionFactory.getCurrentSession().getTransaction().commit();
        return person;
    }

    @Override
    public void update(Person person) {
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().merge(person);
        sessionFactory.getCurrentSession().getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        sessionFactory.getCurrentSession().beginTransaction();
        Person person = (Person) sessionFactory.getCurrentSession().get(Person.class, id);
        sessionFactory.getCurrentSession().delete(person);
        sessionFactory.getCurrentSession().getTransaction().commit();
    }

    public static PersonDao getInstance() {
        return INSTANCE;
    }

}
