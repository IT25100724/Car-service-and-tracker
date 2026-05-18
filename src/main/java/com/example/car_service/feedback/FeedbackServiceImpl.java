package com.example.car_service.feedback;

import com.example.car_service.booking.Booking;
import com.example.car_service.booking.BookingRepository;
import com.example.car_service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final BookingRepository bookingRepository;

    //Submit new feedback after validating rating and booking completion status.
    @Override
    public Feedback submitFeedback(Feedback feedback) {

        if (feedback.getRating() < 1 || feedback.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        Booking booking = bookingRepository.findById(feedback.getBookingId())
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + feedback.getBookingId()));

        if (!"COMPLETED".equalsIgnoreCase(booking.getStatus().name())) {
            throw new IllegalStateException("Feedback can only be submitted for completed bookings");
        }
        
        return feedbackRepository.save(feedback);
    }

    //Retrieve feedback by its unique ID.
    @Override
    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id: " + id));
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    @Override
    public List<Feedback> getFeedbackByCustomer(Long customerId) {
        return feedbackRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Feedback> getFeedbackByBooking(Long bookingId) {
        return feedbackRepository.findByBookingId(bookingId);
    }

    @Override
    public List<Feedback> getFeedbackByMinRating(Integer minRating) {
        return feedbackRepository.findByRatingGreaterThanEqual(minRating);
    }

    //Update an existing feedback record.
    @Override
    public Feedback updateFeedback(Long id, Feedback feedback) {
        Feedback existing = getFeedbackById(id);
        existing.setRating(feedback.getRating());
        existing.setComment(feedback.getComment());
        return feedbackRepository.save(existing);
    }

    //Delete feedback by its unique ID.
    @Override
    public void deleteFeedback(Long id) {
        getFeedbackById(id);
        feedbackRepository.deleteById(id);
    }

   //Calculate and return the average rating from all feedback records.
    @Override
    public Double getAverageRating() {
        List<Feedback> all = feedbackRepository.findAll();
        if (all.isEmpty()) return 0.0;
        
        return all.stream()
                  .mapToInt(Feedback::getRating)
                  .average()
                  .orElse(0.0);
    }
}