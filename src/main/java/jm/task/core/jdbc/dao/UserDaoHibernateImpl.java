package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.math.BigInteger;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;
    public UserDaoHibernateImpl() {
        session = Util.getHibSession();
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;

        try {
                SQLQuery query = session.createSQLQuery("select count(1)\n" +
                    "from information_schema.columns\n" +
                    "where TABLE_NAME = 'USERS'" +
                        "and TABLE_SCHEMA = 'habsida'");
            List<BigInteger> isExists = query.list();

            if ( isExists.get(0).toString().equals("0")) {
                transaction = session.beginTransaction();

                String sqlCre = "CREATE TABLE `USERS` (\n" +
                        "  `ID` int NOT NULL AUTO_INCREMENT,\n" +
                        "  `NAME` varchar(45) DEFAULT NULL,\n" +
                        "  `LASTNAME` varchar(45) DEFAULT NULL,\n" +
                        "  `AGE` int DEFAULT 0,\n" +
                        "  PRIMARY KEY (`ID`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf32;\n";
                session.createSQLQuery(sqlCre).executeUpdate();
                transaction.commit();
                System.out.println("Table USERS is created");
            }
            else {
                System.out.println("Table USERS already exists");
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try {
            SQLQuery query = session.createSQLQuery("select count(1)\n" +
                    "from information_schema.columns\n" +
                    "where TABLE_NAME = 'USERS'\n" +
                    "and TABLE_SCHEMA = 'habsida'");
            List<BigInteger> isExists = query.list();
            if ( !isExists.get(0).toString().equals("0")) {
                transaction = session.beginTransaction();
                String sqlDrop = "DROP TABLE `habsida`.`USERS`;" ;
                session.createSQLQuery(sqlDrop).executeUpdate();
                transaction.commit();
                System.out.println("Table has dropped");
            }
            else {
                System.out.println("Table does not exist");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session.beginTransaction();
        User user = new User(name, lastName, age);
        //long id = (long) session.save(user);
        session.persist(user);
        session.evict(user);
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        User user = session.find(User.class, id);
        if (user != null) {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> res = session.createQuery("from jm.task.core.jdbc.model.User",
                User.class).getResultList();
        return res;
    }

    @Override
    public void cleanUsersTable() {
        session.beginTransaction();
        Query query = session.createQuery("delete from jm.task.core.jdbc.model.User");
        int rowCount = query.executeUpdate();
        session.getTransaction().commit();
    }

}
