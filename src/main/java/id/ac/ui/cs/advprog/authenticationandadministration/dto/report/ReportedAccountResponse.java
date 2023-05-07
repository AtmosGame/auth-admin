package id.ac.ui.cs.advprog.authenticationandadministration.dto.report;

import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class ReportedAccountResponse {
    private Collection<String> listUser;
}
