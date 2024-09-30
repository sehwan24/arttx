package gp.arttx.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HealthCheckController {

    @GetMapping("/health")
    public ResponseEntity<Void> checkHealthStatus() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
