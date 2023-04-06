package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import id.ac.ui.cs.advprog.authenticationandadministration.service.Admin.AdminService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.Auth.AuthService;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User_NonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping(path = "/v1/admin")
public class AdminController {
    @Autowired
    private AuthService authService;
    private AdminService adminService;

    @GetMapping("/users-uname-key")
    public ResponseEntity<Object> getAllUsers() {
        Map<String, User_NonDB> users = authService.getAllUsersUnameKey();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users-uid-key")
    public ResponseEntity<Object> getAllUsersUidKey() {
        Map<Integer, User_NonDB> users = authService.getAllUsersUidKey();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/view-profile/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        User user = adminService.findUserByUsername(username);
        return ResponseEntity.ok(user);
    }
}
