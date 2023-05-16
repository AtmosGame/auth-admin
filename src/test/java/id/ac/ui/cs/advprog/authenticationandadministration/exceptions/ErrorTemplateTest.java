package id.ac.ui.cs.advprog.authenticationandadministration.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ExtendWith(MockitoExtension.class)
class ErrorTemplateTest {
    ZonedDateTime timestamp;

    @BeforeEach
    void setUp(){
        timestamp = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
    }

    @Test
    void TestErrorTemplate(){
        ErrorTemplate errorTemplate = new ErrorTemplate("exception",
                                        HttpStatus.BAD_REQUEST,
                                        timestamp);

        Assertions.assertEquals("exception", errorTemplate.responseMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorTemplate.responseCode());
        Assertions.assertEquals(timestamp, errorTemplate.timestamp());
    }
}
