package id.ac.ui.cs.advprog.authenticationandadministration.service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean login(String username, String password) {
        User supposedUser = userRepository.getUser(username);
        return supposedUser != null &&
                toCipher(password).equals(supposedUser.getPassword());
    }

    @Override
    public void register(String username, String password) {
        userRepository.addUser(username, toCipher(password));
    }

    private String toCipher(String password) {
        Encryptor encryptor = new Encryptor();
        String encryptPassword = encryptor.encrypt(password);
        return encryptPassword;
    }
}
