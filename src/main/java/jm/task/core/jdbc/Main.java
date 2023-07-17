package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {


        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Ivan", "Semyonov", (byte) 25);
        userDao.saveUser("Oleg", "Petrov", (byte) 20);
        userDao.saveUser("Elena", "Sokolove", (byte) 19);
        userDao.saveUser("Darya", "Krylova", (byte) 27);

        List<User> l = userDao.getAllUsers();

        l.stream().forEach(t -> System.out.println(t.toString()));

        userDao.cleanUsersTable();
        userDao.dropUsersTable();

    }
}
