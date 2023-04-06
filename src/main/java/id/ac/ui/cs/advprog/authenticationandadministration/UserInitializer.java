package id.ac.ui.cs.advprog.authenticationandadministration;

import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
        userRepository.addUser("eugenius", "AJCCCJgCzArCAAAAtJJAA", "Developer");
        userRepository.addUser("mario", "JJCACBAALAxJCCrAEJASAA", "User");
    }
}
