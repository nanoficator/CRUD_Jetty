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

    public User getUserByID(Long id) {
        return new UserServiceDAO(sessionFactory.openSession()).getDataByID(id);
    }

    public User findUserByUserName(String userName) {
        return new UserServiceDAO(sessionFactory.openSession()).getDataByUserName(userName);
    }

    public String addUser(User user) {

        if (user.getFirstName().equals("") ||
                user.getSecondName().equals("") ||
                user.getUserName().equals("") ||
                user.getPassword().equals("") ||
                user.getAge() == null ||
                user.getGender().equals("")) {
            return "Error: All fields are required!";
        }

        if (isExistUserName(user.getUserName())) {
            return "Error: Username exists!";
        }

        new UserServiceDAO(sessionFactory.openSession()).addData(user);
        return "User was added!";

    }

    public String deleteUser(User user) {
        User userFromDB = new UserServiceDAO(sessionFactory.openSession()).getDataByUserName(user.getUserName());
        if (userFromDB.getId() != 0) {
            new UserServiceDAO(sessionFactory.openSession()).deleteData(userFromDB);
            return "User was deleted!";
        }
        return "Error: User does not exist!";
    }

    public String deleteUserById(Long id) {
        User userFromDB = new UserServiceDAO(sessionFactory.openSession()).getDataByID(id);
        if(userFromDB != null) {
            deleteUser(userFromDB);
            return "User was deleted!";
        }
        return "Error: User does not exist!";
    }

    public String changeUser(User changedUser) {

        Long id = changedUser.getId();
        String newFirstName = changedUser.getFirstName();
        String newSecondName = changedUser.getSecondName();
        String newUserName = changedUser.getUserName();
        String newPassword = changedUser.getPassword();
        Long newAge = changedUser.getAge();
        String newGender = changedUser.getGender();

        User userFromDBById = getUserByID(id);
        User userFromDBByUserName = UserService.getInstance().findUserByUserName(newUserName);

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

        new UserServiceDAO(sessionFactory.openSession()).changeFirstName(id, newFirstName);
        new UserServiceDAO(sessionFactory.openSession()).changeSecondName(id, newSecondName);
        new UserServiceDAO(sessionFactory.openSession()).changeUserName(id, newUserName);
        new UserServiceDAO(sessionFactory.openSession()).changePassword(id, newPassword);
        new UserServiceDAO(sessionFactory.openSession()).changeAge(id, newAge);
        new UserServiceDAO(sessionFactory.openSession()).changeGender(id, newGender);
        return "Changes saved!";
    }
}
