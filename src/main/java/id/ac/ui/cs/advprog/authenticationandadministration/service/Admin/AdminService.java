package id.ac.ui.cs.advprog.authenticationandadministration.service.Admin;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.ViewProfileResponse;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    ViewProfileResponse getUserByUsername(String username);
}
