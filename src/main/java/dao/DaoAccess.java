package dao;

import entities.Component;
import lombok.Data;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.NativeQuery;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 26.05.2019
 */


@Data
@ApplicationScoped
public class DaoAccess {
    private StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
    ;
    private SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();


    @PostConstruct
    public void init() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            NativeQuery sqlQuery = session.createSQLQuery("CREATE TABLE IF NOT EXISTS components ( " +
                    "id INT(11) NOT NULL AUTO_INCREMENT, " +
                    "description VARCHAR(255) NOT NULL, " +
                    "count INT(2)," +
                    "needForAssembly BOOLEAN," +
                    "countForAssembly INT(2)," +
                    "PRIMARY KEY (id));"
            );
            sqlQuery.executeUpdate();
            List<Component> list = getAll();
            if (list.size() == 0) {
                session.save(new Component("звуковая карта", 7, true, 1));
                session.save(new Component("материнская плата", 10, true, 1));
                session.save(new Component("подсветка корпуса", 5, false, 1));
                session.save(new Component("корпус", 3, true, 1));
                session.save(new Component("SSD диск", 5, true, 1));
                session.save(new Component("оперативная память", 10, true, 2));
                session.save(new Component("видеокарта", 15, false, 1));
                session.save(new Component("Ввентилятор задний", 30, false, 1));
                session.save(new Component("Вентилятор передний", 4, false, 1));
                session.save(new Component("СD привод", 13, false, 1));
                session.save(new Component("DVD привод", 4, false, 1));
                session.save(new Component("HHD диск", 6, false, 1));
                session.save(new Component("блок питания", 4, true, 1));
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public void add(Component component) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(component);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public List<Component> getAll() {
        List<Component> components = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            components =  session.createQuery("from Component").list();
//            for (Component component : components) {
//                session.persist(component);
//            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return components;
    }

    public void update(Component component) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(component);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }


    public void delete(Component component) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(component);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

}
