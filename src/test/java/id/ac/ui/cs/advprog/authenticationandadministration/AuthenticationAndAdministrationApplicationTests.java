package id.ac.ui.cs.advprog.authenticationandadministration;

import id.ac.ui.cs.advprog.authenticationandadministration.controller.DummyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class AuthenticationAndAdministrationApplicationTests {

    @Autowired
    private DummyController myController;

    @Test
    void contextLoads() {
        assertThat(myController).isNotNull();
    }

}
