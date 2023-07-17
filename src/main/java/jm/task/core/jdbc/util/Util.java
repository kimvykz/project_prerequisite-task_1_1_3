package jm.task.core.jdbc.util;
import com.fasterxml.classmate.AnnotationConfiguration;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.imageio.spi.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Util {
    // set up a database connection
    private String userName = "Admin";
    private String userPassword = "welcome1";
    public Connection getConnection() {
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

    public Session getHibSession() {

        Configuration conf = new Configuration();

        conf.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/habsida");
        conf.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        conf.setProperty("hibernate.connection.username", "Admin");
        conf.setProperty("hibernate.connection.password", "welcome1");
        conf.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        conf.setProperty("show_sql", "true");
        conf.addAnnotatedClass(jm.task.core.jdbc.model.User.class);


        SessionFactory sessionFactory = conf.buildSessionFactory();

        Session session = sessionFactory.openSession();


        return session;
    }
}
