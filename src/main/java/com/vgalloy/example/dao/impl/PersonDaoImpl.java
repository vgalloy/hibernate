package com.vgalloy.example.dao.impl;

import com.vgalloy.example.dao.PersonDao;
import com.vgalloy.example.entity.Person;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 04/12/15.
 */
@Repository
public class PersonDaoImpl implements PersonDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Person person) {
        sessionFactory.getCurrentSession().save(person);
    }

    @Override
    public List<Person> getAll() {
        return sessionFactory.getCurrentSession().createCriteria(Person.class).list();
    }

    @Override
    public Person getById(Long id) {
        return (Person) sessionFactory.getCurrentSession().get(Person.class, id);
    }

    @Override
    public void update(Person person) {
        sessionFactory.getCurrentSession().merge(person);
    }

    @Override
    public void delete(Long id) {
        Person person = (Person) sessionFactory.getCurrentSession().get(Person.class, id);
        sessionFactory.getCurrentSession().delete(person);
    }

}
