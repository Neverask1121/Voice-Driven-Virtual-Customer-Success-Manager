package com.vcsm.controller;

import com.vcsm.model.VoiceCommand;
import com.vcsm.service.OmnidimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/voice")
@CrossOrigin(origins = "*")
public class VoiceController {

    @Autowired
    private OmnidimService omnidimService;

    @PostMapping("/command")
    public ResponseEntity<Map<String, Object>> command(@RequestBody Map<String, String> body) {
        String transcript = body.get("transcript");
        if (transcript == null || transcript.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Transcript required", "success", false));
        }
        return ResponseEntity.ok(omnidimService.processVoiceCommand(transcript));
    }

    @GetMapping("/history")
    public ResponseEntity<List<VoiceCommand>> history() {
        return ResponseEntity.ok(omnidimService.getRecentCommands());
    }
}