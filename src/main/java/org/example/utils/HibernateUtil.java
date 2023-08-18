package org.example.utils;

import org.example.entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// Підрубається до БД
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            // Create the SessionFactory from hibernate.cfg.xml
            configuration.addAnnotatedClass(User.class); // Створить таблицю User, якщо не існує в БД

            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}