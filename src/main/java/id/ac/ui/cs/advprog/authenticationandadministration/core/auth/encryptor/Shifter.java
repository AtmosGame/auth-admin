package id.ac.ui.cs.advprog.authenticationandadministration.core.auth.encryptor;

public class Shifter implements ITransformer {
    private int disposition;

    public Shifter(int disposition) {
        this.disposition = disposition;
    }

    public String transform(String str) {
        // TODO: DONE
        // Melakukan shift sesuai nilai
        // Jika disposisi positif maka ke kanan
        // Jika disposisi negatif maka ke kiri
        String content = str;
        if (this.disposition > 0) {
            for (int i = 0; i < this.disposition; i++) {
                content = shiftRightByOne(content);
            }
        } else if (this.disposition < 0) {
            this.disposition = Math.abs(this.disposition);
            for (int i = 0; i < this.disposition; i++) {
                content = shiftLeftByOne(content);
            }
        } else {
            ; // saat disposisi == 0 di pass
        }
        return content;
    }

    private String shiftRightByOne(String s) {
        return s.charAt(s.length() - 1) + s.substring(0, s.length() - 1);
    }

    private String shiftLeftByOne(String s) {
        return s.substring(1) + s.charAt(0);
    }
}
