package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {


        UserDao userDaoHibernate = new UserDaoHibernateImpl();

        userDaoHibernate.createUsersTable();

        userDaoHibernate.saveUser("Ivan", "Semyonov", (byte) 25);
        userDaoHibernate.saveUser("Oleg", "Petrov", (byte) 20);
        userDaoHibernate.saveUser("Elena", "Sokolove", (byte) 19);
        userDaoHibernate.saveUser("Darya", "Krylova", (byte) 27);

        List<User> l = userDaoHibernate.getAllUsers();

        l.stream().forEach(t -> System.out.println(t.toString()));

        //userDaoHibernate.cleanUsersTable();
        //userDaoHibernate.dropUsersTable();

    }
}
