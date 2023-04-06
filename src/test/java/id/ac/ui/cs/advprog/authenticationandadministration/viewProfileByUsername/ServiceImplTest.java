package id.ac.ui.cs.advprog.authenticationandadministration.viewProfileByUsername;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.Admin.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ServiceImplTest {

    @InjectMocks
    private AdminService service;

    @Mock
    private UserRepository repository;

    ViewProfileResponse responseUserValid;


    @BeforeEach
    void setUp(){

    }
}
