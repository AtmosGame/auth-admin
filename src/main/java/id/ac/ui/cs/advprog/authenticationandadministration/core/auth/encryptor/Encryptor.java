package id.ac.ui.cs.advprog.authenticationandadministration.core.auth.encryptor;

import java.util.ArrayList;
import java.util.List;

public class Encryptor {
    private final List<ITransformer> steps;
    public Encryptor() {
        // TODO: DONE
        // Mendefinisikan steps list
        this.steps = new ArrayList<ITransformer>();

        // Mendeklarasikan encryptor dan menambahkan ke daftar step
        this.steps.add(new Shuffler(11));
        this.steps.add(new Substituter(2));
        this.steps.add(new Shifter(3));
        this.steps.add(new Reverser());
        this.steps.add(new Substituter(15));
        this.steps.add(new Shifter(-5));
        this.steps.add(new Shuffler(37));
    }

    public String encrypt(String password) {
        // TODO: DONE
        // Melakukan enkripsi berdasarkan step yang telah disusun
        for (ITransformer i : steps) {
            password = i.transform(password);
        }
        return password;
    }

}

