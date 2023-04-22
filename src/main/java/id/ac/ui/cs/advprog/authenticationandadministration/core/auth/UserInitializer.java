package id.ac.ui.cs.advprog.authenticationandadministration.core.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepositoryNonDB;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserInitializer {

    @Autowired
    UserRepositoryNonDB userRepositoryNonDB;

    @PostConstruct
    public void init() {
        // Username | Password | Role
        // ---------------------------------------
        // eugenius   | Pacil2021  | Developer
        // mario      | Bakung2021 | User

        // initialize users
        userRepositoryNonDB.addUser("eugenius", "AJCCCJgCzArCAAAAtJJAA", "developer");
        userRepositoryNonDB.addUser("mario", "JJCACBAALAxJCCrAEJASAA", "user");
    }
}
