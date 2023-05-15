package id.ac.ui.cs.advprog.authenticationandadministration.jwt;

import id.ac.ui.cs.advprog.authenticationandadministration.core.config.JwtAuthenticationFilter;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import jakarta.servlet.*;
import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);
    }

    @Test
    public void testDoFilterInternal_WithValidJwtToken() throws ServletException, IOException {
        // Arrange
        String jwtToken = "valid_token";
        String username = "john";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.extractUsername(jwtToken)).thenReturn(username);

        UserDetails userDetails = User.builder()
                .username(username)
                .password("password")
                .roles("USER")
                .build();
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.isTokenValid(jwtToken, userDetails)).thenReturn(true);

        // Mock the SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(request, times(1)).getHeader("Authorization");
        verify(jwtService, times(1)).extractUsername(jwtToken);
        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtService, times(1)).isTokenValid(jwtToken, userDetails);
        verify(filterChain, times(1)).doFilter(request, response);
        verifyNoMoreInteractions(request, response, filterChain, jwtService, userDetailsService, securityContext);
        verifyNoInteractions(SecurityContextHolder.getContext());
    }

    @Test
    public void testDoFilterInternal_WithInvalidJwtToken() throws ServletException, IOException {
        // Arrange
        String jwtToken = "invalid_token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.extractUsername(jwtToken)).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(request, times(1)).getHeader("Authorization");
        verify(jwtService, times(1)).extractUsername(jwtToken);
        verify(filterChain, times(1)).doFilter(request, response);
        verifyNoMoreInteractions(request, response, filterChain, jwtService, userDetailsService);
        verifyNoInteractions(SecurityContextHolder.getContext());
    }

    @Test
    public void testDoFilterInternal_WithAlreadyAuthenticatedUser() throws ServletException, IOException {
        // Arrange
        String jwtToken = "valid_token";
        String username = "john";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.extractUsername(jwtToken)).thenReturn(username);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken
                        (username, null));
        UserDetails userDetails = User.builder()
                .username(username)
                .password("password")
                .roles("USER")
                .build();
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.isTokenValid(jwtToken, userDetails)).thenReturn(true);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(request, times(1)).getHeader("Authorization");
        verify(jwtService, times(1)).extractUsername(jwtToken);
        verify(userDetailsService, never()).loadUserByUsername(username);
        verify(jwtService, never()).isTokenValid(jwtToken, userDetails);
        verify(filterChain, times(1)).doFilter(request, response);
        verifyNoMoreInteractions(request, response, filterChain, jwtService, userDetailsService);
        verifyNoInteractions(SecurityContextHolder.getContext());
    }

    @Test
    public void testDoFilterInternal_WithoutJwtToken() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(request, times(1)).getHeader("Authorization");
        verify(jwtService, never()).extractUsername(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtService, never()).isTokenValid(anyString(), any());
        verify(filterChain, times(1)).doFilter(request, response);
        verifyNoMoreInteractions(request, response, filterChain, jwtService, userDetailsService);
        verifyNoInteractions(SecurityContextHolder.getContext());
    }

    @Test
    public void testDoFilterInternal_WithInvalidAuthHeader() throws ServletException, IOException {
        // Arrange
        String authHeader = "Invalid Header";

        when(request.getHeader("Authorization")).thenReturn(authHeader);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(request, times(1)).getHeader("Authorization");
        verify(jwtService, never()).extractUsername(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtService, never()).isTokenValid(anyString(), any());
        verify(filterChain, times(1)).doFilter(request, response);
        verifyNoMoreInteractions(request, response, filterChain, jwtService, userDetailsService);
        verifyNoInteractions(SecurityContextHolder.getContext());
    }

//    Dalam pengujian ini, kita menguji tiga skenario:
//
//    1. Ketika terdapat JWT token yang valid, maka autentikasi akan berhasil.
//    2. Ketika terdapat JWT token yang tidak valid, maka autentikasi akan gagal.
//    3. Ketika pengguna sudah terotentikasi sebelumnya, maka autentikasi tidak akan dilakukan ulang.

}