package id.ac.ui.cs.advprog.authenticationandadministration.repository;

import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.Token;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    void addToken(@NonNull String token, @NonNull Integer userId);
    void deleteTokenByExpired();
}
