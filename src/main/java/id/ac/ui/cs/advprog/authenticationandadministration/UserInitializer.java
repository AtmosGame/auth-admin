package id.ac.ui.cs.advprog.authenticationandadministration;

import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
