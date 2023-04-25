package id.ac.ui.cs.advprog.authenticationandadministration.service.profile;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserHasBeenBlockedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserIsAdministratorException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;

    @Override
    public ViewProfileResponse getProfileByUsername(String username) {
        User user = getUserByUsername(username);
        userValidationNonAdmin(user);

        return ViewProfileResponse.builder()
                .username(user.getUsername())
                .role(user.getRole().name())
                .profilePicture(user.getProfilePicture())
                .bio(user.getBio())
                .applications(user.getApplications())
                .build();
    }

    @Override
    public void userValidationNonAdmin(User user){
        if (user.getRole().name().equals("ADMIN"))
            throw new UserIsAdministratorException(user.getUsername());

        if (!user.getActive())
            throw new UserHasBeenBlockedException(user.getUsername());
    }

    @Override
    public User getUserByUsername(String username){
        if (userRepository.findByUsername(username).isEmpty())
            throw new UserDoesNotExistException(username);

        return userRepository.findByUsername(username).get();
    }
    @Override
    public User updateProfile(String username, String bio, String profilePicture){
        User user = getUserByUsername(username);
        userValidationNonAdmin(user);

        user.setBio(bio);
        user.setProfilePicture(profilePicture);
        return userRepository.save(user);
    }
}
