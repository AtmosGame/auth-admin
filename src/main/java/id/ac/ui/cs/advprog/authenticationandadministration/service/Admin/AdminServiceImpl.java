package id.ac.ui.cs.advprog.authenticationandadministration.service.Admin;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.UserIsAdministratorException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    @Autowired
    private final UserRepository userRepository;

    @Override
    public ViewProfileResponse getUserByUsername(String username) {
        if (isUserDoesNotExist(username)){
            throw new UserDoesNotExistException(username);
        }

        User user = userRepository.findByUsername(username).get();

        if (user.getRole().equals("Administrator")){
            throw new UserIsAdministratorException(username);
        }

        return ViewProfileResponse.builder().username(user.getUsername())
                .role(user.getRole()).profilePicture(user.getProfilePicture())
                .bio(user.getBio()).applications(user.getApplications()).build();
    }

    private boolean isUserDoesNotExist(String username){
        return userRepository.findByUsername(username).isEmpty();
    }
}
