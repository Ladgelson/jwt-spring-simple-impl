package learnjwt.coursejwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Welcome {
    @GetMapping("welcome")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome migles!");
    }

}
