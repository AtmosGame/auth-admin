package id.ac.ui.cs.advprog.authenticationandadministration.models.auth;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Set;
import java.util.stream.Collectors;

import static id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserPermission.*;

public enum UserRole {
    ADMIN(Sets.newHashSet(AUTH, USER_READ, REPORT_READ, REPORT_DELETE)),
    DEVELOPER(Sets.newHashSet(AUTH, USER_READ)),
    USER(Sets.newHashSet(AUTH, USER_READ));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
        return authorities;
    }
}
