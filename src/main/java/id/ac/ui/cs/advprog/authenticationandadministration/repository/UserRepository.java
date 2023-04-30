package id.ac.ui.cs.advprog.authenticationandadministration.repository;

import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO _user (username, password, role, active) VALUES (:username, :password, :role, :active)", nativeQuery = true)
    void addUser(@NonNull String username, @NonNull String password, @NonNull String role, @NonNull Boolean active);
    @Query(value = "SELECT * FROM _user WHERE username = :username", nativeQuery = true)
    User getUser(@NonNull String username);
    @Query(value = "SELECT * FROM _user", nativeQuery = true)
    List<User> getAllUsers();

    @NonNull
    List<User> findAll();
    @NonNull
    Optional<User> findByUsername(@NonNull String username);
    @Modifying
    @Query("update User u set u.active = false where u.username = :username")
    void updateActiveUserByUsername(@NonNull String username);

    List<User> findByUsernameContainingIgnoreCase(String username);
}
