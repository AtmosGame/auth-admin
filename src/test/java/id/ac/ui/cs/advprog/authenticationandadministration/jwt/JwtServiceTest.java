package id.ac.ui.cs.advprog.authenticationandadministration.jwt;

import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class JwtServiceTest {
    private static final String SECRET_KEY = "645367566B59703373367639792F423F4528482B4D6251655468576D5A713474";

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void testExtractUsername() {
        // Arrange
        String username = "john";
        String token = generateTokenWithUsername(username);

        // Act
        String extractedUsername = jwtService.extractUsername(token);

        // Assert
        Assertions.assertEquals(username, extractedUsername);
    }

    @Test
    void testGenerateTokenWithUserDetails() {
        // Arrange
        UserDetails userDetails = User.builder()
                .username("john")
                .password("password")
                .roles("USER")
                .build();

        // Act
        String token = jwtService.generateToken(userDetails);

        // Assert
        Assertions.assertNotNull(token);
    }

    @Test
    void testGenerateTokenWithExtraClaimsAndUserDetails() {
        // Arrange
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("customClaim", "value");

        UserDetails userDetails = User.builder()
                .username("john")
                .password("password")
                .roles("USER")
                .build();

        // Act
        String token = jwtService.generateToken(extraClaims, userDetails);

        // Assert
        Assertions.assertNotNull(token);
    }

    @Test
    void testIsTokenValidValidTokenAndUserDetails() {
        // Arrange
        String username = "john";
        String token = generateTokenWithUsername(username);

        UserDetails userDetails = User.builder()
                .username(username)
                .password("password")
                .roles("USER")
                .build();

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        Assertions.assertTrue(isValid);
    }

    @Test
    void testExtractClaim() {
        // Arrange
        String username = "john";
        String token = generateTokenWithUsername(username);

        // Act
        String extractedUsername = jwtService.extractClaim(token, Claims::getSubject);

        // Assert
        Assertions.assertEquals(username, extractedUsername);
    }

    @Test
    void testExtractAllClaims() {
        // Arrange
        String username = "john";
        String token = generateTokenWithUsername(username);

        // Act
        Claims claims = jwtService.extractAllClaims(token);

        // Assert
        Assertions.assertEquals(username, claims.getSubject());
    }

    private String generateTokenWithUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Token expires in 1 hour
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateTokenWithUsernameAndExpiration(String username, Date expiration) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    void testIsTokenValidDifferentUsername() {
        // Arrange
        String token = generateTokenWithUsername("john");
        UserDetails userDetails = User.builder()
                .username("jane")
                .password("password")
                .roles("USER")
                .build();

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        Assertions.assertFalse(isValid);
    }

    @Test
    void testIsTokenValidTokenExpiredException() {
        // Arrange
        String token = generateTokenWithUsernameAndExpiration("john", new Date(System.currentTimeMillis() - 1000));
        UserDetails userDetails = User.builder()
                .username("john")
                .password("password")
                .roles("USER")
                .build();

        // Act and Assert
        Assertions.assertThrows(ExpiredJwtException.class, () -> jwtService.isTokenValid(token, userDetails));
    }
}

