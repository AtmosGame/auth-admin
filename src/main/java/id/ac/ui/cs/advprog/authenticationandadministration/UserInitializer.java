package id.ac.ui.cs.advprog.authenticationandadministration;

@Component
public class UserInitializer {

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void init() {
        // Username | Password
        // ---------------------------------------
        // Alicia   | UlangTahunSayaAdalahHariIni
        // Bob      | BOBisAPalindrome
        // Charlie  | CHARLIEcharlie

        // initialize users
        userRepository.addUser("Alicia", "jrLzrEPyYRlkyrCurECErrrIZxz");
        userRepository.addUser("Bob", "IFuSJRfCEzzrSDgv");
        userRepository.addUser("Charlie", "CRrIvYziZVtyTc");

    }
}
