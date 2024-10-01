package gp.arttx.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/test")
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/1")
    public String testReturn() {
        logger.debug("DEBUG: /api/test endpoint hit");
        logger.info("INFO: /api/test endpoint hit");
        return "종합설계 파이팅!";
    }

    @GetMapping("/health")
    public ResponseEntity<Void> healthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/1", method = RequestMethod.OPTIONS)
    public void handleOptions() {
        logger.info("OPTIONS request received");
    }


}
