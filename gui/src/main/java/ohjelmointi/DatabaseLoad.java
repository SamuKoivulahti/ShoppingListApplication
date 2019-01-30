package ohjelmointi;
import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.query.NativeQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Allows downloading/loading files to and from database.
 *
 * @author  Samu Koivulahti
 * @version 2018.1210
 * @since   1.8
 */
public class DatabaseLoad {
    private static SessionFactory sessionFactory;

    /**
    * Creates initial memory database configurations.
    */
    public static void create() {
        Configuration config = new Configuration().configure();
        sessionFactory = config.buildSessionFactory();
    }

    /**
    * Adds items to database.
    */
    public static void addItems(ObservableList<Item> items) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        transaction.begin();
        session.createSQLQuery("DELETE products").executeUpdate();

        for (Item item : items) {
            session.save(item);
        }

        transaction.commit();

        session.close();
    }

    /**
    * Gets items from database.
    *
    * @return list of items.
    */
    @SuppressWarnings("unchecked")
    public static ObservableList<Item> getItems() {
        Session session = sessionFactory.openSession();
        ObservableList<Item> items = FXCollections.observableArrayList();

        NativeQuery<Object[]> rows = session.createSQLQuery("SELECT productAmount, productName FROM products");

        for(Object[] row : rows.list()) {
            items.add(new Item((String) row[1], (Integer) row[0]));
        }

        session.close();
        return items;
    }

    /**
    * Closes session factory.
    */
    public static void delete() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}