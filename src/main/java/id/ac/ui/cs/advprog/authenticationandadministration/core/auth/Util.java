package id.ac.ui.cs.advprog.authenticationandadministration.core.auth;

import java.security.SecureRandom;
import java.util.ArrayList;

public class Util {

    private Util() {

    }

    private static final char[] allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public static String generateToken() {
        var random = new SecureRandom();
        var token = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            var randomCharIndex = random.nextInt(allowedCharacters.length);
            var randomChar = allowedCharacters[randomCharIndex];
            token.add(String.valueOf(randomChar));
        }
        return String.join("", token);
    }

}

