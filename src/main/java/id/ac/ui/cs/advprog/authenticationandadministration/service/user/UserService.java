package id.ac.ui.cs.advprog.authenticationandadministration.service.user;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.CurrentUserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    CurrentUserResponse getCurrentUser(String username);
}