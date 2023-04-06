package id.ac.ui.cs.advprog.authenticationandadministration.service.Admin;

import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    User findUserByUsername(String username);
}
