package id.ac.ui.cs.advprog.authenticationandadministration.dto.ViewProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewProfileRequest {
    private String username;
}
