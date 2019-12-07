package service;

import DAO.UserDAO;
import model.User;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class UserService {

    private static UserService userService;

    private SessionFactory sessionFactory;

    UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService(DBHelper.getSessionFactory());
        }
        return userService;
    }

    public List<User> getAllUsers() {
        return new UserDAO(sessionFactory.openSession()).getAllData();
    }

    public void deleteAllUsers() {
        new UserDAO(sessionFactory.openSession()).deleteAllData();
    }

    public boolean isExistUserName(String userName) {
        User userFromDB = new UserDAO(sessionFactory.openSession()).findDataByUserName(userName);
        if (userFromDB.getId() != 0) {
            return true;
        }
        return false;
    }

    public void addUser(User user) {
        if (!isExistUserName(user.getUserName())) {
            new UserDAO(sessionFactory.openSession()).addData(user);
        }
    }

    public void deleteUser(User user) {
        User userFromDB = new UserDAO(sessionFactory.openSession()).findDataByUserName(user.getUserName());
        if (userFromDB.getId() != 0) {
            new UserDAO(sessionFactory.openSession()).deleteData(userFromDB);
        }
    }

}
