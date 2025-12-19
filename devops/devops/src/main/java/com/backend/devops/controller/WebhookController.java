package com.backend.devops.controller;

import com.backend.devops.service.WebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
@CrossOrigin(origins = "http://localhost:3000")
public class WebhookController {

    private final WebhookService webhookService;

    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> payload) {
        try {
            webhookService.processWebhook(payload);
            return ResponseEntity.ok("Webhook processed");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing webhook");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllWebhooks() {
        try {
            List<Map<String, Object>> webhooks = webhookService.getAllWebhooks();
            return ResponseEntity.ok(webhooks);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

}
