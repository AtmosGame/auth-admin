package id.ac.ui.cs.advprog.authenticationandadministration.service.Admin;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.UserHasBeenBlockedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.UserIsAdministratorException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    @Override
    public ViewProfileResponse getUserByUsername(String username) {
        if (userRepository.findByUsername(username).isEmpty()){
            throw new UserDoesNotExistException(username);
        }

        User user = userRepository.findByUsername(username).get();
        userValidation(user);
        return ViewProfileResponse.builder().username(user.getUsername())
                .role(user.getRole()).profilePicture(user.getProfilePicture())
                .bio(user.getBio()).applications(user.getApplications()).build();
    }

    private void userValidation(User user){
        if (user.getRole().equals("administrator")){
            throw new UserIsAdministratorException(user.getUsername());
        }

        if (!user.getActive()){
            throw new UserHasBeenBlockedException(user.getUsername());
        }
    }
}
