package id.ac.ui.cs.advprog.authenticationandadministration.core.auth;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;

@Component
public class UserInitializer {

    @Autowired
    public
    UserRepository userRepository;

    @PostConstruct
    public void init() {
        // Username     | Password | Role       | Status
        // ---------------------------------------------
        // atmos        | pass1234 | ADMIN      | Active
        // moontod      | tencent0 | DEVELOPER  | Active
        // eugeniusms   | adadeh13 | USER       | Active
        // mario        | tidurges | USER       | Inactive


        // initialize users
        userRepository.addUser("atmos", "$2a$10$qlqaBQHs1Si7s3NJpMwTxu9p0mnBuCRbX/3rZh3f4FQfrRYs4Yi5e", "ADMIN", true);
        userRepository.addUser("moontod", "$2a$10$vna.ETappaBal/Ibk7u2I.Ixqx8oF2qquUVOZJ8AcRWxxTIoadKVW", "DEVELOPER", true);
        userRepository.addUser("eugeniusms", "$2a$10$OlSFCGx/hRb9LNAfPQuCsu/yq7n8bbEsusXk01vpZKWEZifSsKKie", "USER", true);
        userRepository.addUser("mario", "$2a$10$clLk5YJ28ROYVtNpXWdUW.5.gfp7s4OtDGOg8rYx89OkawiFxl7xq", "USER", false);
    }
}
