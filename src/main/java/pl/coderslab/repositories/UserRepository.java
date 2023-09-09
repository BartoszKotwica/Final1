package pl.coderslab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    static void save() {
    }

    User findUserById (Integer id);
    User findUserByFirstName (String firstName);
    User findUserByLastName (String lastName);
    User findUserByUserName (String userName);
    User findUserByUserNameAndPassword(String userName, String password);
    User findUserByEmail (String email);

    @Query("select u from User u where u.id = :id")
    User getUserById(@Param("id") Integer id);

    @Query ("select u from User u where u.firstName like :first_name%")
    User getUserByFirstName(@Param("fist_name") String firstName);

    @Query("select u from User u where u.lastName like :last_name%")
    User getUserByLastName(@Param("last_name") String lastName);

    @Query("select u from User u where u.email like :email%")
    User getUserByEmail(@Param("email") String email);

    @Query("select u from User u where u.userName like :user_name%")
    User getUserByUserName(@Param("user_name") String userName);

    @Query("select u from User u where u.userName like :user_name% and u.password like :password%")
    User getUserByUserNameAndPassword (@Param("user_name") String userName, @Param("password") String password);
}
