package id.ac.ui.cs.advprog.authenticationandadministration.user;

import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserModelTest {
    @Test
    void testGetAdminAuthorities() {
        User user = User.builder()
                .role(UserRole.ADMIN)
                .build();

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(5, authorities.size());
    }

    @Test
    void testGetDeveloperAuthorities() {
        User user = User.builder()
                .role(UserRole.DEVELOPER)
                .build();

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(3, authorities.size());
    }

    @Test
    void testGetUserAuthorities() {
        User user = User.builder()
                .role(UserRole.USER)
                .build();

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(3, authorities.size());
    }
}
