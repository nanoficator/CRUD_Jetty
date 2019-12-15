package service;

import DAO.UserServiceSQLDAO;
import exception.DBException;
import model.User;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserServiceSQL {

    public UserServiceSQL() {
    }

    public List<User> getAllUsers() throws DBException {
        UserServiceSQLDAO dao = getUserServiceSQLDAO();
        try {
            return dao.getAllData();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void deleteAllUsers() throws DBException {
        UserServiceSQLDAO dao = getUserServiceSQLDAO();
        try {
            dao.deleteAllData();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean isExistUserName(String userName) throws DBException {
        UserServiceSQLDAO dao = getUserServiceSQLDAO();
        try{
            User userFromDB = dao.getDataByUserName(userName);
            if (userFromDB != null) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public User getUserByID(Long id) throws DBException {
        UserServiceSQLDAO dao = getUserServiceSQLDAO();
        try {
            return dao.getDataById(id);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public User getUserByUserName(String userName) throws DBException {
        UserServiceSQLDAO dao = getUserServiceSQLDAO();
        try {
            return dao.getDataByUserName(userName);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public String addUser(User user) throws DBException {

        if (isExistUserName(user.getUserName())) {
            return "Error: Username exists!";
        }

        if (user.getAge() < 0) {
            return "Error: Age can not be negative!";
        }

        UserServiceSQLDAO dao = getUserServiceSQLDAO();
        try {
            dao.addData(user);
            return "User was added!";
        } catch (SQLException e) {
            throw new DBException(e);
        }
            }

    public String deleteUser(User user) throws DBException {

        User userFromDB = getUserByUserName(user.getUserName());

        if (userFromDB == null) {
            return "Error: User does not exist!";
        }

        UserServiceSQLDAO dao = getUserServiceSQLDAO();
        try {
            dao.deleteData(userFromDB);
            return "User was deleted!";
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public String deleteUserById(Long id) throws DBException {

        User userFromDB = getUserByID(id);

        if (userFromDB == null) {
            return "Error: User does not exist!";
        }

        UserServiceSQLDAO dao = getUserServiceSQLDAO();
        try {
            dao.deleteData(userFromDB);
            return "User was deleted!";
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public String changeUser(User changedUser) throws DBException {

        Long id = changedUser.getId();
        String newFirstName = changedUser.getFirstName();
        String newSecondName = changedUser.getSecondName();
        String newUserName = changedUser.getUserName();
        String newPassword = changedUser.getPassword();
        Long newAge = changedUser.getAge();
        String newGender = changedUser.getGender();

        User userFromDBById = getUserByID(id);
        User userFromDBByUserName = getUserByUserName(newUserName);

        if (userFromDBById == null) {
            return "Error: User does not exist!";
        }

        if (id == null ||
                newFirstName.equals("") ||
                newSecondName.equals("") ||
                newUserName.equals("") ||
                newPassword.equals("") ||
                newAge == null ||
                newGender.equals("")) {
            return "Error: All fields are required!";
        }

        if (userFromDBByUserName != null && !userFromDBByUserName.getId().equals(id)) {
            return "Error: Username exists!";
        }

        if (newAge < 0) {
            return "Error: Age can not be negative!";
        }

        UserServiceSQLDAO dao = getUserServiceSQLDAO();
        try {
            dao.changeFirstName(id, newFirstName);
            dao.changeSecondName(id, newSecondName);
            dao.changeUserName(id, newUserName);
            dao.changePassword(id, newPassword);
            dao.changeAge(id, newAge);
            dao.changeGender(id, newGender);
            return "Changes saved!";
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").          //db type
                    append("localhost:").             //host name
                    append("3306/").                  //port
                    append("user_db?").          //db name
                    append("user=root").              //login
                    append("&password=p@ssw0rd").     //password
                    append("&serverTimezone=UTC");    //setup server time

            System.out.println("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString(), "root", "p@ssw0rd");
            return connection;
        } catch (SQLException | ClassNotFoundException |IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private static UserServiceSQLDAO getUserServiceSQLDAO() {
        return new UserServiceSQLDAO(getMysqlConnection());
    }
}
