package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.UsernameAlreadyExistsException;
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

    @GetMapping("/users-uname-key")
    public ResponseEntity<Object> getAllUsers() {
        Map<String, User> users = authService.getAllUsersUnameKey();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users-uid-key")
    public ResponseEntity<Object> getAllUsersUidKey() {
        Map<Integer, User> users = authService.getAllUsersUidKey();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Object> register(Model model,
                           @RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "role") String role) {
        if (authService.getAllUsersUnameKey().containsKey(username)) {
            Map<String, String> message = new HashMap<>();
            message.put("message", "Username already exists");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        authService.register(username, password, role);
        Map<String, User> data = new HashMap<>();
        data.put("data", authService.getAllUsersUnameKey().get(username));
        return new ResponseEntity<>(data, HttpStatus.OK);
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
}
