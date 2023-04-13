package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.service.Admin.AdminService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.Auth.AuthService;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User_NonDB;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AuthService authService;
    private final AdminService adminService;

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
    public ResponseEntity<ViewProfileResponse> viewProfileByUsername(@PathVariable String username){
        return new ResponseEntity<>(adminService.getUserByUsername(username), HttpStatus.OK);
    }
}
