package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection conn;
    public UserDaoJDBCImpl() {
        Util util = new Util();
        conn = util.getConnection();
    }

    public void createUsersTable() {
        try(Statement stm = conn.createStatement()){

            DatabaseMetaData dbMetaData = conn.getMetaData();
            ResultSet resultSet = dbMetaData.getTables(null, null, null,
                    new String[] {"TABLE"});
            boolean userExists = false;
            while (resultSet.next()){
                String name = resultSet.getString("TABLE_NAME");
                if (name.equals("USERS")){
                    userExists = true;
                }
            }

            if (!userExists) {
                stm.executeUpdate("CREATE TABLE `USERS` (\n" +
                        "  `ID` int NOT NULL AUTO_INCREMENT,\n" +
                        "  `NAME` varchar(45) DEFAULT NULL,\n" +
                        "  `LASTNAME` varchar(45) DEFAULT NULL,\n" +
                        "  `AGE` int DEFAULT 0,\n" +
                        "  PRIMARY KEY (`ID`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf32;\n");
                System.out.println("Table USERS is created");
            }
            else{
                System.out.println("Table USERS is already existed in DB");
            }
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try (Statement stm = conn.createStatement()) {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            ResultSet resultSet = dbMetaData.getTables(null, null, null,
                    new String[] {"TABLE"});
            boolean userExists = false;
            while (resultSet.next()){
                String name = resultSet.getString("TABLE_NAME");
                if (name.equals("USERS")){
                    userExists = true;
                }
            }
            if (userExists) {
                stm.executeUpdate("DROP TABLE `habsida`.`USERS`");
                System.out.println("Table USERS is dropped");
            }
            else {
                System.out.println("Table USERS does not exist");
            }
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        try (Statement stm = conn.createStatement()) {
            stm.executeUpdate("INSERT INTO `habsida`.`USERS`\n" +
                    "(`NAME`,\n" +
                    "`LASTNAME`,\n" +
                    "`AGE`)\n" +
                    "VALUES\n" +
                    "('" + name + "',\n" +
                    "'" + lastName + "',\n" +
                     age + ");\n");
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement stm = conn.createStatement()){
            stm.executeUpdate("DELETE FROM `habsida`.`USERS`\n" +
                    "WHERE id = " + id + ";\n");
        }
        catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(Statement stm = conn.createStatement()){
            ResultSet rs = stm.executeQuery("select * from `habsida`.`USERS`");
            while (rs.next()){
                User user = new User(rs.getString("NAME"),
                        rs.getString("LASTNAME"),
                        rs.getByte("AGE"));
                user.setId(rs.getLong("ID"));
                userList.add(user);
            }

        }
        catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE TABLE `habsida`.`USERS`");
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
    }
}
