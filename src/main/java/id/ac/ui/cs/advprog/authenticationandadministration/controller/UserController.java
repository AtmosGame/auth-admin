package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.CurrentUserResponse;
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
        return ResponseEntity.ok(CurrentUserResponse.builder()
                .id(getCurrentUser().getId())
                .username(getCurrentUser().getUsername())
                .role(getCurrentUser().getRole())
                .profilePicture(getCurrentUser().getProfilePicture())
                .active(getCurrentUser().getActive())
                .build());
    }

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