package id.ac.ui.cs.advprog.authenticationandadministration.repository;

import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.Token;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO _token (token, user_id) VALUES (:token, :userId)", nativeQuery = true)
    void addToken(@NonNull String token, @NonNull Integer userId);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM _token WHERE user_id = :userId", nativeQuery = true)
    void deleteAllByUserId(@NonNull Integer userId);
    @Transactional
    List<Token> getAllByUserId(@NonNull Integer userId);
}
