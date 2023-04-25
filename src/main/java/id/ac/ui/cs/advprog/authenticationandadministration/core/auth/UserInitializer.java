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
        // eugenius   | Pacil2021  | Developer
        // mario      | Bakung2021 | User

        // initialize users
        userRepository.addUser("eugenius", "AJCCCJgCzArCAAAAtJJAA", "developer");
        userRepository.addUser("mario", "JJCACBAALAxJCCrAEJASAA", "user");
    }
}
