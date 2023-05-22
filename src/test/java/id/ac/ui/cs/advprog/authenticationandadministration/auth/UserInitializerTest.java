package id.ac.ui.cs.advprog.authenticationandadministration.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.core.seed.UserInitializer;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class UserInitializerTest {

    @Mock
    private UserRepository userRepository;

    private UserInitializer userInitializer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userInitializer = new UserInitializer();
        userInitializer.userRepository = userRepository;
    }

    @Test
    void testInit() {
        userInitializer.init();

        verify(userRepository).addUser("atmos", "$2a$10$qlqaBQHs1Si7s3NJpMwTxu9p0mnBuCRbX/3rZh3f4FQfrRYs4Yi5e", "ADMIN", true);
        verify(userRepository).addUser("moontod", "$2a$10$vna.ETappaBal/Ibk7u2I.Ixqx8oF2qquUVOZJ8AcRWxxTIoadKVW", "DEVELOPER", true);
        verify(userRepository).addUser("eugeniusms", "$2a$10$OlSFCGx/hRb9LNAfPQuCsu/yq7n8bbEsusXk01vpZKWEZifSsKKie", "USER", true);
        verify(userRepository).addUser("mario", "$2a$10$clLk5YJ28ROYVtNpXWdUW.5.gfp7s4OtDGOg8rYx89OkawiFxl7xq", "USER", false);
        verifyNoMoreInteractions(userRepository);
    }
}
