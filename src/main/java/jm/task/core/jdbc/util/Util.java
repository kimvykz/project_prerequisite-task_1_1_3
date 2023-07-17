package jm.task.core.jdbc.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;

public class Util {
    // set up a database connection
    private static final String userName = "Admin";
    private static final String userPassword = "welcome1";

    private Util(){

    }
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName ("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/habsida",
                    userName,
                    userPassword);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return conn;
    }

    public static Session getHibSession() {

        Configuration conf = new Configuration();

        conf.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/habsida");
        conf.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        conf.setProperty("hibernate.connection.username", userName);
        conf.setProperty("hibernate.connection.password", userPassword);
        conf.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        conf.setProperty("show_sql", "true");
        conf.addAnnotatedClass(jm.task.core.jdbc.model.User.class);


        SessionFactory sessionFactory = conf.buildSessionFactory();

        Session session = sessionFactory.openSession();


        return session;
    }
}
