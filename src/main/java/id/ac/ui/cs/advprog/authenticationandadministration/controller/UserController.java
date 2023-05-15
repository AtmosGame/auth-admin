package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.CurrentUserResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.SearchUserRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.JwtService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
        CurrentUserResponse response = null;
        response = userService.getCurrentUser(getCurrentUser().getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search-user")
    public ResponseEntity<List<User>> searchUsers(@RequestBody SearchUserRequest request) {
        return ResponseEntity.ok(userService.searchUsers(request));
    }

    @GetMapping("/get-user-non-admin/{username}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<User> getUserNonAdmin(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserNonAdminByUsername(username));
    }

    @GetMapping("/get-user/{username}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
}
