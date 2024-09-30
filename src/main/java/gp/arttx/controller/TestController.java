package gp.arttx.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/test")
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @CrossOrigin(origins = "https://www.artpings.com")
    @RequestMapping(value = "/1", method = RequestMethod.OPTIONS)
    public void options() {
        // Preflight 요청 처리
    }

    @CrossOrigin(origins = "https://www.artpings.com")
    @GetMapping("/1")
    public String testReturn() {
        logger.debug("DEBUG: /api/test endpoint hit");
        logger.info("INFO: /api/test endpoint hit");
        return "TestSuccess";
    }
}
