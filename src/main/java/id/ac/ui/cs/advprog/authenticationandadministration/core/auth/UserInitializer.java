package id.ac.ui.cs.advprog.authenticationandadministration.core.auth;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;

@Component
public class UserInitializer {

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void init() {
        // Username | Password | Role
        // ---------------------------------------
        // eugeniusms   | adadeh13 | ADMIN

        // initialize users
        userRepository.addUser("eugeniusms", "$2a$10$IoLq5nh3EsDI1.E9WJqWC.NydY7F6g6drXrHGHHcfahveHd86WHhS", "ADMIN", true);
    }
}
