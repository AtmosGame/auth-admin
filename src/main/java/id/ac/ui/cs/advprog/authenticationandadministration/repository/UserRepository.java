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

    @NonNull
    List<User> findAll();

    @NonNull
    Optional<User> findByUsername(@NonNull String username);

    @Transactional
    @Modifying
    @Query(value = "update _user set active = false where username =  ?1", nativeQuery = true)
    void blockUserByUsername(@NonNull String username);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM _user WHERE (_user.role = 'USER' OR _user.role = 'DEVELOPER') AND username LIKE CONCAT('%', :username, '%') ", nativeQuery = true)
    List<User> findByUsernameContainingIgnoreCase(String username);

    @Transactional
    @Modifying
    @Query(value = "SELECT username FROM _user, user_report WHERE _user.id = user_report._user_id AND _user.active GROUP BY _user_id, username ORDER BY count(*) DESC;", nativeQuery = true)
    List<String> findAllHaveReportedUser();
}
