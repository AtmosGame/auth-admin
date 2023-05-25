package id.ac.ui.cs.advprog.authenticationandadministration;

import id.ac.ui.cs.advprog.authenticationandadministration.controller.AuthController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthenticationAndAdministrationApplicationTests {
    @Autowired
    private AuthController authController;

    @Test
    void contextLoads() {
        Assertions.assertThat(authController).isNotNull();
    }
}
