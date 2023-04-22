package id.ac.ui.cs.advprog.authenticationandadministration.core.auth.encryptor;

public class Reverser implements ITransformer {
    public String transform(String str) {
        char[] chars = str.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = chars.length-1; i >= 0; i--) {
            result.append(chars[i]);
        }
        return result.toString();
    }

}
