package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.entity.User;
import pl.coderslab.service.UserService;


@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
           model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(User user, Model model) {

        User authenticatedUser = userService.authenticateUser(user.getUserName(), user.getPassword());

        if(authenticatedUser != null) {
            return "redirect:/welcome";
        }else {
            model.addAttribute("error", "Błąd logowania. Sróbuj ponownie.");
            return "redirect:/login";
        }
    }
}
