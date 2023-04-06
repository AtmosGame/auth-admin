package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dummy")
public class DummyController {
    @GetMapping("/hello-world")
    public ResponseEntity<String> sayHelloWorld(){
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping("/test")
    public ResponseEntity<String> sayTest(){
        return ResponseEntity.ok("Test");
    }
}
