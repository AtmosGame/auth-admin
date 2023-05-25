package id.ac.ui.cs.advprog.authenticationandadministration.repository;

import id.ac.ui.cs.advprog.authenticationandadministration.models.Report;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    @NonNull
    Optional<Report> findById(@NonNull Integer id);

    void deleteById(@NonNull Integer id);

    @Async
    void deleteAll(@NonNull Iterable<? extends Report> entities);
}
