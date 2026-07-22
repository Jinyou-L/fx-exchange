package com.fx.api.web;

import com.fx.api.feed.AcceptingState;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AcceptingState accepting;

    public AdminController(AcceptingState accepting) { this.accepting = accepting; }

    @GetMapping("/accepting")
    public Map<String, Boolean> getAccepting() {
        return Map.of("accepting", accepting.isAccepting());
    }

    @PostMapping("/accepting")
    public Map<String, Boolean> setAccepting(@RequestBody Map<String, Boolean> request) {
        Boolean value = request.get("accepting");
        if (value == null) throw new IllegalArgumentException("accepting is required");
        return Map.of("accepting", accepting.set(value));
    }
}
