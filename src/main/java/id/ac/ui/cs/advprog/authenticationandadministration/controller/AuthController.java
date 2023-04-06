package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.service.AuthService;
import id.ac.ui.cs.advprog.authenticationandadministration.core.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping(path = "/register")
    public String registerPage(Model model) {
        return "auth/register";
    }

    @GetMapping(path = "/login")
    public String loginPage(Model model) {
        return "auth/login";
    }

    @PostMapping(path = "/register")
    public String register(Model model,
                           @RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "role") String role) {
        authService.register(username, password, role);
        return "redirect:/auth/login";
    }

    @PostMapping(path = "/login")
    public String login(Model model,
                        @RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password) {
        if (authService.login(username, password)) {
            model.addAttribute("successful", true);
            return "auth/login";
        }
        model.addAttribute("successful", false);
        return "auth/login";
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        Map<String, User> users = authService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
