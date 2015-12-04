package com.vgalloy.example.factory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by vgalloy on 04/12/15.
 */
public class SessionFactoryFactory {
    private static SessionFactory sessionFactory;

    public static final SessionFactory getSessionFactory(){
        if(sessionFactory == null) {
            sessionFactory = new Configuration().configure("hib.cfg.xml").buildSessionFactory();
        }
        return sessionFactory;
    }
}
