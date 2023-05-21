package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.*;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.JwtService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    private static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
    }

    @GetMapping("/current")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<CurrentUserResponse> getCurrentUserProfile() {
        CurrentUserResponse response = userService.getCurrentUser(getCurrentUser().getUsername());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/search-user")
    public ResponseEntity<List<User>> searchUsers(@RequestBody SearchUserRequest request) {
        List<User> listUsers = userService.searchUsers(request);
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }

    @GetMapping("/get-user-non-admin/{username}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<User> getUserNonAdmin(@PathVariable String username) {
        User userNonAdmin = userService.getUserNonAdminByUsername(username);
        return new ResponseEntity<>(userNonAdmin, HttpStatus.OK);
    }

    @GetMapping("/get-user/{username}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
