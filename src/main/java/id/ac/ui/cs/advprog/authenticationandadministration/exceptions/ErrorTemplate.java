package id.ac.ui.cs.advprog.authenticationandadministration.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ErrorTemplate(String responseMessage, HttpStatus responseCode, ZonedDateTime timestamp) {
}