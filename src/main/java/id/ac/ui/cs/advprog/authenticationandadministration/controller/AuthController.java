package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthService;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User_NonDB;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(path = "/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping(path = "/register")
    public String registerPage(Model model) {
        return "auth/register";
    }

    @GetMapping(path = "/login")
    public String loginPage(Model model) {
        return "auth/login";
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
        Map<String, User_NonDB> data = new HashMap<>();
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
