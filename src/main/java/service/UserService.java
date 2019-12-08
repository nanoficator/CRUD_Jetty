package service;

import DAO.UserServiceDAO;
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
        return new UserServiceDAO(sessionFactory.openSession()).getAllData();
    }

    public void deleteAllUsers() {
        new UserServiceDAO(sessionFactory.openSession()).deleteAllData();
    }

    public boolean isExistUserName(String userName) {
        User userFromDB = new UserServiceDAO(sessionFactory.openSession()).findDataByUserName(userName);
        if (userFromDB != null) {
            return true;
        }
        return false;
    }

    public String addUser(User user) {
        if (!isExistUserName(user.getUserName())) {
            new UserServiceDAO(sessionFactory.openSession()).addData(user);
            return "User was added!";
        }
        return "Username is alredy exist!";
    }

    public void deleteUser(User user) {
        User userFromDB = new UserServiceDAO(sessionFactory.openSession()).findDataByUserName(user.getUserName());
        if (userFromDB.getId() != 0) {
            new UserServiceDAO(sessionFactory.openSession()).deleteData(userFromDB);
        }
    }

    public boolean deleteUserById(Long id) {
        User userFromDB = new UserServiceDAO(sessionFactory.openSession()).findDataByID(id);
        if(userFromDB != null) {
            deleteUser(userFromDB);
            return true;
        }
        return false;
    }

    public User findUserByID(Long id) {
        return new UserServiceDAO(sessionFactory.openSession()).findDataByID(id);
    }

}
