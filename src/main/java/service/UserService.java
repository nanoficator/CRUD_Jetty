package service;

import DAO.UserServiceDAO;
import model.User;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.Iterator;
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
        if (userFromDB.getId() != 0) {
            return true;
        }
        return false;
    }

    public boolean addUser(User user) {
        if (!isExistUserName(user.getUserName())) {
            new UserServiceDAO(sessionFactory.openSession()).addData(user);
            return true;
        }
        return false;
    }

    public void deleteUser(User user) {
        User userFromDB = new UserServiceDAO(sessionFactory.openSession()).findDataByUserName(user.getUserName());
        if (userFromDB.getId() != 0) {
            new UserServiceDAO(sessionFactory.openSession()).deleteData(userFromDB);
        }
    }
     public String [][] createArrayFromDB() {
        List<User> allUsers = getAllUsers();
         Iterator<User> iterator = allUsers.iterator();
        String[][] usersArray = new String[allUsers.size() + 1][6];
        usersArray[0][0] = "ID";
        usersArray[0][1] = "First Name";
        usersArray[0][2] = "Second Name";
        usersArray[0][3] = "Userame";
        usersArray[0][4] = "Age";
        usersArray[0][5] = "Gender";
        while (iterator.hasNext()) {
            for (int i = 1; i < allUsers.size() + 1; i++) {
                User user = iterator.next();
                usersArray[i][0] = user.getId().toString();
                usersArray[i][1] = user.getFirstName();
                usersArray[i][2] = user.getSecondName();
                usersArray[i][3] = user.getUserName();
                usersArray[i][4] = user.getAge().toString();
                usersArray[i][5] = user.getGender();
            }
        }
        return usersArray;
     }
}
