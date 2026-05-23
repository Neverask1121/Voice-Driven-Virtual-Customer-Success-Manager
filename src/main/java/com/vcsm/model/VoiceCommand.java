package com.vcsm.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "voice_commands")
public class VoiceCommand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String transcript;

    private String intent;

    @Column(length = 2000)
    private String response;

    private boolean processed = false;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // ---- Getters ----
    public Long getId() { return id; }
    public String getTranscript() { return transcript; }
    public String getIntent() { return intent; }
    public String getResponse() { return response; }
    public boolean isProcessed() { return processed; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // ---- Setters ----
    public void setId(Long id) { this.id = id; }
    public void setTranscript(String transcript) { this.transcript = transcript; }
    public void setIntent(String intent) { this.intent = intent; }
    public void setResponse(String response) { this.response = response; }
    public void setProcessed(boolean processed) { this.processed = processed; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}