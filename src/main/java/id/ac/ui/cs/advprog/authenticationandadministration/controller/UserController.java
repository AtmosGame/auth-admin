package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

//    @GetMapping("/users")
//    public List<User> searchUsers(@RequestParam("username") String username) {
//        if(username == null || username.isEmpty()) {
//            return userRepository.findAll();
//        }
//        return userRepository.findByUsernameContainingIgnoreCase(username);
//    }

    @GetMapping("/users")
    public CompletableFuture<ResponseEntity<List<User>>> searchUsers(@RequestParam(name = "name", required = false) String username) {
        return CompletableFuture.supplyAsync(() -> {
            List<User> users;
            if (username == null || username.trim().isEmpty()) {
                users = userRepository.findAll();
            } else {
                users = userRepository.findByUsernameContainingIgnoreCase(username);
            }
            if (users.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(users);
        });
    }





}