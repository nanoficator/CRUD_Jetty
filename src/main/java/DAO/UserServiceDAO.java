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

    public User findDataByID(Long id) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User where id = :id");
        query.setParameter("id", id);
        User userFromDB = (User) query.uniqueResult();
        transaction.commit();
        session.close();
        return userFromDB;
    }

    public User findDataByUserName (String userName) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User where user_name = :user_name");
        query.setParameter("user_name", userName);
        User userFromDB = (User) query.uniqueResult();
        transaction.commit();
        session.close();
        return userFromDB;
    }

    public int changeFirstName(Long id, String firstName) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("update User set firstName = :firsName where id = :id");
        query.setParameter("firsName", firstName);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        transaction.commit();
        session.close();
        return result;
    }
}
