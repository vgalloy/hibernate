package com.vgalloy.example.service.impl;

import com.vgalloy.example.dao.PersonDao;
import com.vgalloy.example.dao.impl.PersonDaoImpl;
import com.vgalloy.example.entity.Person;
import com.vgalloy.example.factory.SessionFactoryFactory;
import com.vgalloy.example.service.PersonService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vgalloy on 04/12/15.
 */
public class PersonServiceImpl implements PersonService {
    private static PersonService INSTANCE;
    private PersonDao personDao;

    private PersonServiceImpl() {
        this.personDao = PersonDaoImpl.getInstance();
    }

    @Override
    public void create(Person person) {
        try {
            SessionFactoryFactory.getSessionFactory().getCurrentSession().beginTransaction();
            personDao.create(person);
            SessionFactoryFactory.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception e){
            SessionFactoryFactory.getSessionFactory().getCurrentSession().getTransaction().rollback();
        }
    }

    @Override
    public List<Person> getAll() {
        List<Person> personList = new ArrayList<>();
        try {
            SessionFactoryFactory.getSessionFactory().getCurrentSession().beginTransaction();
            personList = personDao.getAll();
            SessionFactoryFactory.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception e){
            SessionFactoryFactory.getSessionFactory().getCurrentSession().getTransaction().rollback();
        }
        return personList;
    }

    @Override
    public Person getById(Long id) {
        Person person = null;
        try {
            SessionFactoryFactory.getSessionFactory().getCurrentSession().beginTransaction();
            person = personDao.getById(id);
            SessionFactoryFactory.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception e){
            SessionFactoryFactory.getSessionFactory().getCurrentSession().getTransaction().rollback();
        }
        return person;
    }

    @Override
    public void update(Person person) {
        try {
            SessionFactoryFactory.getSessionFactory().getCurrentSession().beginTransaction();
            personDao.update(person);
            SessionFactoryFactory.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception e){
            SessionFactoryFactory.getSessionFactory().getCurrentSession().getTransaction().rollback();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            SessionFactoryFactory.getSessionFactory().getCurrentSession().beginTransaction();
            personDao.delete(id);
            SessionFactoryFactory.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception e){
            SessionFactoryFactory.getSessionFactory().getCurrentSession().getTransaction().rollback();
        }
    }

    public static final PersonService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new PersonServiceImpl();
        }
        return INSTANCE;
    }

}
