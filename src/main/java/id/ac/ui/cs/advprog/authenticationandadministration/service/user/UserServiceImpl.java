package id.ac.ui.cs.advprog.authenticationandadministration.service.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.CurrentUserResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.SearchUserRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.Report;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthService;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public CurrentUserResponse getCurrentUser(String username) {
//        if (!userRepository.getUser(username))
//            throw new UserDoesNotExistException(username);

        User user = userRepository.getUser(username);
        return CurrentUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .profilePicture(user.getProfilePicture())
                .bio(user.getBio())
                .applications(user.getApplications())
                .active(user.getActive())
                .reportList(user.getReportList())
                .build();

    }

    @Override
    public List<User> searchUsers(SearchUserRequest request) {
        List<User> users = new ArrayList<>();
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return users;
        } else {
            return userRepository.findByUsernameContainingIgnoreCase(request.getUsername());
        }
    };
}

