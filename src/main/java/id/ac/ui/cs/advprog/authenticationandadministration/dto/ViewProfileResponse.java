package id.ac.ui.cs.advprog.authenticationandadministration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ViewProfileResponse {
    private String username;
    private String role;
    private String profilePicture;
    private String bio;
    private String applications;
}
