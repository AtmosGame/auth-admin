package id.ac.ui.cs.advprog.authenticationandadministration.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

@Data
@Generated
@Builder
@AllArgsConstructor
public class ViewProfileResponse {
    private String username;
    private String role;
    private String profilePicture;
    private String bio;
    private String applications;
}
