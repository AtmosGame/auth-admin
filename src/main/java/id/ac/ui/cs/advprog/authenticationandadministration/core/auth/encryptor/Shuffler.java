package id.ac.ui.cs.advprog.authenticationandadministration.core.auth.encryptor;

import java.util.*;

public class Shuffler implements ITransformer {
    private final Random random;

    public Shuffler(int seed) {
        this.random = new Random(seed);
    }

    public String transform(String str) {
        List<Character> chars = new java.util.ArrayList<>(str.chars().mapToObj(i -> (char) i).toList());
        Collections.shuffle(chars, random);

        StringBuilder result = new StringBuilder();
        for (char c : chars) {
            result.append(c);
        }
        return result.toString();
    }
}
