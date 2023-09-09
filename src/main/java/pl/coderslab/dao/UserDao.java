package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import pl.coderslab.DbUtil;
import pl.coderslab.entity.User;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class UserDao {

    private static final String CREATE_USER_QUERY = "INSERT INTO user(first_name, last_name, user_name, email, password) VALUES (?, ?, ?, ?, ?);";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE id = ?;";
    private static final String FIND_ALL_USERS_QUERY = "SELECT * FROM user;";
    private static final String READ_USER_QUERY = "SELECT * FROM user WHERE id = ?;";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET first_name = ?, last_name = ?, user_name = ?, email = ?, password = ? WHERE id = ?;";
    private static final String FIND_BY_USERNAME_AND_PASSWORD_QUERY = "SELECT * FROM user WHERE user_name = ? AND password = ?;";
    private static final String FIND_BY_USERNAME_QUERY = "SELECT * FROM user WHERE user_name = ?;";


    public User read (Integer userId) {
        User user = new User();
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(READ_USER_QUERY)) {
            statement.setInt(1, userId);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setUserName(resultSet.getString("user_name"));
                    user.setEmail(resultSet.getString("email"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User addUser(User user) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_USER_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, user.getFirstName());
            insertStm.setString(2, user.getLastName());
            insertStm.setString(3, user.getUserName());
            insertStm.setString(4, user.getEmail());
            insertStm.setString(5, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    user.setId(generatedKeys.getInt(1));
                    return user;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setUserName(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void updateUser(User user) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUserName());
            statement.setString(3, user.getEmail());
            statement.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            statement.setInt(6, user.getId());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(Integer userId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User findByUser_nameAndPassword(String user_name, String password) {
        User user = null;
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_AND_PASSWORD_QUERY)) {
            statement.setString(1, user_name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("password");
                    if (BCrypt.checkpw(password, hashedPassword)) {
                        user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setFirstName(resultSet.getString("first_name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setUserName(resultSet.getString("user_name"));
                        user.setEmail(resultSet.getString("email"));

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByUsername(String user_name) {
        User user = null;
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_QUERY)) {
            statement.setString(1, user_name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setUserName(resultSet.getString("user_name"));
                    user.setEmail(resultSet.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
