package id.ac.ui.cs.advprog.authenticationandadministration.models;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Set;
import java.util.stream.Collectors;

import static id.ac.ui.cs.advprog.authenticationandadministration.models.UserPermission.DUMMY;

public enum UserRole {
    ADMIN(Sets.newHashSet(DUMMY)),
    DEVELOPER(Sets.newHashSet(DUMMY)),
    USER(Sets.newHashSet(DUMMY));

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
