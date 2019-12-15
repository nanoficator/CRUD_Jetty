package DAO;

import com.google.protobuf.DescriptorProtos;
import freemarker.core.UnexpectedTypeException;
import model.User;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserServiceSQLDAO {

    private Connection connection;

    public UserServiceSQLDAO(Connection connection) {
        this.connection = connection;
    }

    public List<User> getAllData() throws SQLException {
        List<User> allData = new LinkedList<>();
        Statement statement = connection.createStatement();
        statement.execute("select * from users");
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            Long id = resultSet.getLong(1);
            String firstName = resultSet.getString(3);
            String secindName = resultSet.getString(6);
            String userName = resultSet.getString(7);
            String password = resultSet.getString(5);
            Long age = resultSet.getLong(2);
            String gender = resultSet.getString(4);
            allData.add(new User(id, firstName, secindName, userName, password, age, gender));
        }
        resultSet.close();
        statement.close();
        return allData;
    }

    public void deleteAllData() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("delete from users");
        statement.close();
    }

    public void addData(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into users (first_name, second_name, user_name, password, age, gender) values (?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getSecondName());
        preparedStatement.setString(3, user.getUserName());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setLong(5, user.getAge());
        preparedStatement.setString(6, user.getGender());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void deleteData(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from users where id = ?");
        preparedStatement.setLong(1, user.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public User getDataById(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            User userById = new User();
            userById.setId(resultSet.getLong(1));
            userById.setFirstName(resultSet.getString(3));
            userById.setSecondName(resultSet.getString(6));
            userById.setUserName(resultSet.getString(7));
            userById.setPassword(resultSet.getString(5));
            userById.setAge(resultSet.getLong(2));
            userById.setGender(resultSet.getString(4));
            return userById;
        }
        return null;
    }

    public User getDataByUserName (String userName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where user_name = ?");
        preparedStatement.setString(1,userName);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            User userByUserName = new User();
            userByUserName.setId(resultSet.getLong(1));
            userByUserName.setFirstName(resultSet.getString(3));
            userByUserName.setSecondName(resultSet.getString(6));
            userByUserName.setUserName(resultSet.getString(7));
            userByUserName.setPassword(resultSet.getString(5));
            userByUserName.setAge(resultSet.getLong(2));
            userByUserName.setGender(resultSet.getString(4));
            return userByUserName;
        }
        return null;
    }

    public void changeFirstName(Long id, String newFirstName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update users set first_name = ? where id = ?");
        preparedStatement.setString(1, newFirstName);
        preparedStatement.setLong(2, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void changeSecondName(Long id, String newSecondName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update users set second_name = ? where id = ?");
        preparedStatement.setString(1, newSecondName);
        preparedStatement.setLong(2, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void changeUserName(Long id, String newUserName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update users set user_name = ? where id = ?");
        preparedStatement.setString(1, newUserName);
        preparedStatement.setLong(2, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void changePassword(Long id, String newPassword) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update users set password = ? where id = ?");
        preparedStatement.setString(1, newPassword);
        preparedStatement.setLong(2, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void changeAge(Long id, Long newAge) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update users set age = ? where id = ?");
        preparedStatement.setLong(1, newAge);
        preparedStatement.setLong(2, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void changeGender(Long id, String newGender) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update users set gender = ? where id = ?");
        preparedStatement.setString(1, newGender);
        preparedStatement.setLong(2, id);
        preparedStatement.execute();
        preparedStatement.close();
    }
}