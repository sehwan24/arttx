package gp.arttx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test")
@RestController
public class TestController {

    @GetMapping("/1")
    public String testReturn() {
        return "TestSuccess";
    }
}
