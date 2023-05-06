package id.ac.ui.cs.advprog.authenticationandadministration.service.user;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.CurrentUserResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.SearchUserRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface UserService {
    CurrentUserResponse getCurrentUser(String username);

    List<User> searchUsers(SearchUserRequest request);
}