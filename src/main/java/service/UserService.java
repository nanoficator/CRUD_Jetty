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
        User userFromDB = new UserServiceDAO(sessionFactory.openSession()).getDataByUserName(userName);
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
        return "Username is already exist!";
    }

    public void deleteUser(User user) {
        User userFromDB = new UserServiceDAO(sessionFactory.openSession()).getDataByUserName(user.getUserName());
        if (userFromDB.getId() != 0) {
            new UserServiceDAO(sessionFactory.openSession()).deleteData(userFromDB);
        }
    }

    public boolean deleteUserById(Long id) {
        User userFromDB = new UserServiceDAO(sessionFactory.openSession()).getDataByID(id);
        if(userFromDB != null) {
            deleteUser(userFromDB);
            return true;
        }
        return false;
    }

    public User findUserByID(Long id) {
        return new UserServiceDAO(sessionFactory.openSession()).getDataByID(id);
    }

    public User findUserByUserName(String userName) {
        return new UserServiceDAO(sessionFactory.openSession()).getDataByUserName(userName);
    }

    public String changeUser(User userForChange) {

        Long id = userForChange.getId();
        String newFirstName = userForChange.getFirstName();
        String newSecondName = userForChange.getSecondName();
        String newUserName = userForChange.getUserName();
        String newPassword = userForChange.getPassword();
        Long newAge = userForChange.getAge();
        String newGender = userForChange.getGender();

        User userFromDBById = findUserByID(id);
        User userFromDBByUserName = UserService.getInstance().findUserByUserName(newUserName);

        if (userFromDBById == null) {
            return "Error: User does not exist!";
        }

        int count = 0;
        if (!newFirstName.equals("")) {
            count += new UserServiceDAO(sessionFactory.openSession()).changeFirstName(id, newFirstName);
        }
        if (!newSecondName.equals("")) {
            count += new UserServiceDAO(sessionFactory.openSession()).changeSecondName(id, newSecondName);
        }
        if (!newUserName.equals("") && userFromDBByUserName == null) {
            count += new UserServiceDAO(sessionFactory.openSession()).changeUserName(id, newUserName);
        } else if (!userFromDBByUserName.getId().equals(id)) {
            return "Error: Username already exist!";
        }
        if (!newPassword.equals("")) {
            count += new UserServiceDAO(sessionFactory.openSession()).changePassword(id, newPassword);
        }
        if (newAge != 0) {
            count += new UserServiceDAO(sessionFactory.openSession()).changeAge(id, newAge);
        }
        if (!newGender.equals("")) {
            count += new UserServiceDAO(sessionFactory.openSession()).changeGender(id, newGender);
        }
        if (count > 0) {
            return "Fields successfully changed";
        } else {
            return "User not changes";
        }
    }

}
