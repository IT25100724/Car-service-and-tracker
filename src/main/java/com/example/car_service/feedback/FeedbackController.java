package com.example.car_service.feedback;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//REST controller for managing customer feedback operations.
//Provides endpoints for creating, retrieving, updating,and deleting feedback records.
@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Feedback> submitFeedback(@RequestBody Feedback feedback) {
        return new ResponseEntity<>(feedbackService.submitFeedback(feedback), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Feedback>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByCustomer(customerId));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Feedback>> getByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByBooking(bookingId));
    }

    @GetMapping("/rating/{min}")
    public ResponseEntity<List<Feedback>> getByMinRating(@PathVariable Integer min) {
        return ResponseEntity.ok(feedbackService.getFeedbackByMinRating(min));
    }

    @GetMapping("/average-rating")
    public ResponseEntity<Map<String, Double>> getAverageRating() {
        return ResponseEntity.ok(Map.of("averageRating", feedbackService.getAverageRating()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @RequestBody Feedback feedback) {
        return ResponseEntity.ok(feedbackService.updateFeedback(id, feedback));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok("Feedback deleted successfully");
    }
}
