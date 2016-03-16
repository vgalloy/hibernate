package com.vgalloy.example.factory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by vgalloy on 04/12/15.
 */
public class EntityManagerFactoryFactory {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Person");

    public static final EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }
}
