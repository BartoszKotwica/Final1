package pl.coderslab.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.dao.UserDao;
import pl.coderslab.entity.User;
import pl.coderslab.exceptions.UserAlreadyExistException;
import pl.coderslab.repositories.UserRepository;
import pl.coderslab.service.UserService;

@Controller
@RequestMapping("/")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController (UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/")
    public String mainSite(Model model) {
        return "/html/index";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String ProcessRegister (User user) throws UserAlreadyExistException {
        try {
            userService.newClient(user);


        return "redirect:/registrationSuccess";
    } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/registrationError";
        }
    }
}
