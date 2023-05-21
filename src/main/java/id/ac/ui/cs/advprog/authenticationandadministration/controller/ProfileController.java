package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.EditProfileRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/view-profile/{username}")
    public ResponseEntity<ViewProfileResponse> viewProfileByUsername(@PathVariable String username){
        return new ResponseEntity<>(profileService.getProfileByUsername(username), HttpStatus.OK);
    }

    @PostMapping("/update-profile/{username}")
    public ResponseEntity<String> updateProfile(@PathVariable String username, @RequestBody EditProfileRequest request) {
        profileService.updateProfile(username, request);
        return new ResponseEntity<>("Profile updated", HttpStatus.OK);
    }
}
