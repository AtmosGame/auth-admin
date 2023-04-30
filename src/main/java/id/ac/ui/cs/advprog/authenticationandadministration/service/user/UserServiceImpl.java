package id.ac.ui.cs.advprog.authenticationandadministration.service.user;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.CurrentUserRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.CurrentUserResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private AuthService authService;
    private UserRepository userRepository;
    @Override
    public CurrentUserResponse getCurrentUser(CurrentUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isEmpty())
            throw new UserDoesNotExistException(request.getUsername());

        User user = authService.getUserByUsername(request.getUsername());
        return CurrentUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .profilePicture(user.getProfilePicture())
                .active(user.getActive())
                .build();

    }
}
