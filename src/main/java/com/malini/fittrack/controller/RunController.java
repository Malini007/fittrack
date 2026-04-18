package com.malini.fittrack.controller;

import com.malini.fittrack.model.Run;
import com.malini.fittrack.model.User;
import com.malini.fittrack.service.RunService;
import com.malini.fittrack.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final RunService runService;
    private final UserService userService;

    public RunController(RunService runService, UserService userService) {
        this.runService = runService;
        this.userService = userService;
    }

    private User getCurrentUser(Authentication auth) {
        return userService.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping("/log")
    public ResponseEntity<?> logRun(@RequestBody Map<String, String> request,
                                    Authentication auth) {
        try {
            User user = getCurrentUser(auth);
            Run run = runService.logRun(
                    user,
                    request.get("routeName"),
                    Double.parseDouble(request.get("distanceKm")),
                    Integer.parseInt(request.get("durationMinutes")),
                    LocalDate.parse(request.get("runDate")));
            return ResponseEntity.ok(Map.of(
                    "message", "Run logged successfully!",
                    "runId", run.getId(),
                    "pace", String.format("%.2f min/km", run.getPace())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(Authentication auth) {
        User user = getCurrentUser(auth);
        List<Run> runs = runService.getUserRuns(user);
        return ResponseEntity.ok(runs);
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(Authentication auth) {
        User user = getCurrentUser(auth);
        return ResponseEntity.ok(Map.of("stats", runService.getStats(user)));
    }

    @DeleteMapping("/{runId}")
    public ResponseEntity<?> deleteRun(@PathVariable Long runId, Authentication auth) {
        runService.deleteRun(runId);
        return ResponseEntity.ok(Map.of("message", "Run deleted successfully"));
    }
}