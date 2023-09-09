package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.entity.User;
import pl.coderslab.entity.UserRole;
import pl.coderslab.exceptions.UserAlreadyExistException;
import pl.coderslab.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void newClient(User user) throws UserAlreadyExistException {
        if(emailExists(user.getEmail())){
            throw new UserAlreadyExistException("Jest już konto o takim emailu: " + user.getEmail());
        }
        user.setUserRole(UserRole.USER);
        userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    public User authenticateUser(String userName, String password) {
        // Tutaj możesz dodać logikę uwierzytelniania użytkownika
        User user = userRepository.getUserByUserNameAndPassword(userName, password);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
