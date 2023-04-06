package id.ac.ui.cs.advprog.authenticationandadministration.service.Admin;

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
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    private boolean isUserDoesNotExist(String username){
        return userRepository.findByUsername(username).isEmpty();
    }
}
