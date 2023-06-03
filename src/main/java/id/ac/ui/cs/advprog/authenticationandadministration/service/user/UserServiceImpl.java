package id.ac.ui.cs.advprog.authenticationandadministration.service.user;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.CurrentUserResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.SearchUserRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.InvalidTokenException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserHasBeenBlockedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserIsAdministratorException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.TokenRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Override
    public CurrentUserResponse getCurrentUser(String username) {
        User user = userRepository.getUser(username);

        var userTokens = tokenRepository.getAllByUserId(user.getId());
        if (userTokens.isEmpty()) {
            throw new InvalidTokenException();
        }

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
    }

    @Override
    public User getUserNonAdminByUsername(String username) {
        User user = getUserByUsername(username);
        String userRole = user.getRole().name();

        if (userRole.equals(UserRole.ADMIN.name()))
            throw new UserIsAdministratorException(user.getUsername());

        boolean userBlocked = !user.getActive();
        if (userBlocked)
            throw new UserHasBeenBlockedException(user.getUsername());

        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent())
            return user.get();
        else
            throw new UserDoesNotExistException(username);
    }
}

