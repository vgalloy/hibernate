package com.vgalloy.example.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import com.vgalloy.example.dao.PersonDao;
import com.vgalloy.example.entity.Person;
import com.vgalloy.example.factory.EntityManagerFactoryFactory;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 04/12/15.
 */
public class PersonDaoImpl implements PersonDao {
    private static PersonDao INSTANCE = new PersonDaoImpl();
    /**
     * Un context transactionel est lié à 1 entityManager
     */
    private EntityManagerFactory entityManagerFactory;

    private PersonDaoImpl() {
        entityManagerFactory = EntityManagerFactoryFactory.getEntityManagerFactory();
    }

    @Override
    public void create(Person person) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Person> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("Select t from " + Person.class.getSimpleName() + " t").getResultList();
    }

    @Override
    public Person getById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Person.class, id);
    }

    @Override
    public void update(Person person) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(person);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Person person = entityManager.find(Person.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(person);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static PersonDao getInstance() {
        return INSTANCE;
    }
}
