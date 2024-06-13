package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao{
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users" +
                    "(id bigserial PRIMARY KEY, name VARCHAR(255), lastname VARCHAR(255), age smallint)").executeUpdate();
            tr.commit();
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы пользователей");
        }
    }

    @Override
    public void dropUsersTable(){
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            tr.commit();
        } catch (SQLException e) {
            System.out.println("Ошибка при дропе таблицы");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tr.commit();
            System.out.println("Пользователь " + name + " добавлен в БД");
        } catch (SQLException e) {
            System.out.println("Ошибка добавления пользователя");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null){
                session.delete(user);
            }
            tr.commit();
            System.out.println("Пользователь id" + id + " удален");
        } catch (SQLException e) {
            System.out.println("Ошибка удаления пользователя");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("From users", User.class).list();
        } catch (SQLException e) {
            System.out.println("Ошибка получения пользователей");
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.createQuery("delete from users").executeUpdate();
            tr.commit();
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы пользователей");
        }
    }
}