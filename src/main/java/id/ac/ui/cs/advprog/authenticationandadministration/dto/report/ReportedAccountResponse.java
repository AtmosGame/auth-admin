package id.ac.ui.cs.advprog.authenticationandadministration.dto.report;

import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import lombok.*;

import java.util.Collection;
import java.util.Set;

@Data
@Generated
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportedAccountResponse {
    private Collection<String> listUser;
}
