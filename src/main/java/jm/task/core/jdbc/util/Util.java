package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.PostgreSQLDialect; // Используйте соответствующий диалект для вашей СУБД

import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:postgresql://localhost:5432/newDB";
    private static final String user = "postgres";
    private static final String password = "2425733";
    // реализуйте настройку соеденения с БД
    public static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() throws SQLException {
        if (sessionFactory == null){
            Configuration config = new Configuration();
            config.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            config.setProperty("hibernate.connection.url", url);
            config.setProperty("hibernate.connection.username", user);
            config.setProperty("hibernate.connection.password", password);
            config.addAnnotatedClass(User.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
            sessionFactory = config.buildSessionFactory(builder.build());
        }
        return sessionFactory;
    }
}