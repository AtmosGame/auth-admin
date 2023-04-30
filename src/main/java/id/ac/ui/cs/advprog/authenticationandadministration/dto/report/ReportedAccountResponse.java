package id.ac.ui.cs.advprog.authenticationandadministration.dto.report;

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
