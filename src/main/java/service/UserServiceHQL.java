package service;

import DAO.UserServiceHQLDAO;
import model.User;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class UserServiceHQL {

    private static UserServiceHQL userServiceHQL;

    private SessionFactory sessionFactory;

    UserServiceHQL(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserServiceHQL getInstance() {
        if (userServiceHQL == null) {
            userServiceHQL = new UserServiceHQL(DBHelper.getSessionFactory());
        }
        return userServiceHQL;
    }

    public List<User> getAllUsers() {
        return new UserServiceHQLDAO(sessionFactory.openSession()).getAllData();
    }

    public void deleteAllUsers() {
        new UserServiceHQLDAO(sessionFactory.openSession()).deleteAllData();
    }

    public boolean isExistUserName(String userName) {
        User userFromDB = new UserServiceHQLDAO(sessionFactory.openSession()).getDataByUserName(userName);
        if (userFromDB != null) {
            return true;
        }
        return false;
    }

    public User getUserByID(Long id) {
        return new UserServiceHQLDAO(sessionFactory.openSession()).getDataByID(id);
    }

    public User getUserByUserName(String userName) {
        return new UserServiceHQLDAO(sessionFactory.openSession()).getDataByUserName(userName);
    }

    public String addUser(User user) {

        if (isExistUserName(user.getUserName())) {
            return "Error: Username exists!";
        }

        if (user.getAge() < 0) {
            return "Error: Age can not be negative!";
        }

        new UserServiceHQLDAO(sessionFactory.openSession()).addData(user);
        return "User was added!";

    }

    public String deleteUser(User user) {

        User userFromDB = new UserServiceHQLDAO(sessionFactory.openSession()).getDataByUserName(user.getUserName());

        if (userFromDB == null) {
            return "Error: User does not exist!";
        }

        new UserServiceHQLDAO(sessionFactory.openSession()).deleteData(userFromDB);
        return "User was deleted!";

    }

    public String deleteUserById(Long id) {

        User userFromDB = new UserServiceHQLDAO(sessionFactory.openSession()).getDataByID(id);

        if(userFromDB == null) {
            return "Error: User does not exist!";
        }

        deleteUser(userFromDB);
        return "User was deleted!";
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
        User userFromDBByUserName = UserServiceHQL.getInstance().getUserByUserName(newUserName);

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

        new UserServiceHQLDAO(sessionFactory.openSession()).changeFirstName(id, newFirstName);
        new UserServiceHQLDAO(sessionFactory.openSession()).changeSecondName(id, newSecondName);
        new UserServiceHQLDAO(sessionFactory.openSession()).changeUserName(id, newUserName);
        new UserServiceHQLDAO(sessionFactory.openSession()).changePassword(id, newPassword);
        new UserServiceHQLDAO(sessionFactory.openSession()).changeAge(id, newAge);
        new UserServiceHQLDAO(sessionFactory.openSession()).changeGender(id, newGender);
        return "Changes saved!";
    }
}
