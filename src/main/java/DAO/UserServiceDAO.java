package DAO;

import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserServiceDAO {

    Session session;

    public UserServiceDAO(Session session) {
        this.session = session;
    }

    public List<User> getAllData() {
        Transaction transaction = session.beginTransaction();
        List<User> allUsers = session.createQuery("from User").list();
        transaction.commit();
        session.close();
        return allUsers;
    }

    public void deleteAllData() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        transaction.commit();
        session.close();
    }

    public void addData(User user) {
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public void deleteData(User user) {
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    public User getDataByID(Long id) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User where id = :id");
        query.setParameter("id", id);
        User userFromDB = (User) query.uniqueResult();
        transaction.commit();
        session.close();
        return userFromDB;
    }

    public User getDataByUserName(String userName) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User where user_name = :user_name");
        query.setParameter("user_name", userName);
        User userFromDB = (User) query.uniqueResult();
        transaction.commit();
        session.close();
        return userFromDB;
    }

    public void changeFirstName(Long id, String firstName) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("update User set firstName = :firstName where id = :id");
        query.setParameter("firstName", firstName);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void changeSecondName(Long id, String secondName) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("update User set secondName = :secondName where id = :id");
        query.setParameter("secondName", secondName);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void changeUserName(Long id, String newUserName) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("update User set userName = :newUserName where id = :id");
        query.setParameter("newUserName", newUserName);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void changePassword(Long id, String password) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("update User set password = :password where id = :id");
        query.setParameter("password", password);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void changeAge(Long id, Long age) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("update User set age = :age where id = :id");
        query.setParameter("age", age);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void changeGender(Long id, String gender) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("update User set gender = :gender where id = :id");
        query.setParameter("gender", gender);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        transaction.commit();
        session.close();
    }
}