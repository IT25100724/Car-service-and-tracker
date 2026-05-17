package com.example.car_service.feedback;

import java.util.List;

//Service interface for managing customer feedback operations.
public interface FeedbackService {

    Feedback submitFeedback(Feedback feedback);

    Feedback getFeedbackById(Long id);

    List<Feedback> getAllFeedback();

    List<Feedback> getFeedbackByCustomer(Long customerId);

    List<Feedback> getFeedbackByBooking(Long bookingId);

    List<Feedback> getFeedbackByMinRating(Integer minRating);

    Feedback updateFeedback(Long id, Feedback feedback);

    void deleteFeedback(Long id);

    Double getAverageRating();
}
