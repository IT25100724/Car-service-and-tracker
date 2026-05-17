package com.example.car_service.feedback;

import java.util.List;

//Service interface for managing customer feedback operations.
// Defines methods for creating, retrieving, updating, deleting, and analyzing feedback data.
public interface FeedbackService {

    //Save a new feedback record.
    Feedback submitFeedback(Feedback feedback);

    //Retrieve feedback by its unique ID.
    Feedback getFeedbackById(Long id);

    //Retrieve all feedback records.
    List<Feedback> getAllFeedback();

    //Retrieve feedback submitted by a specific customer.
    List<Feedback> getFeedbackByCustomer(Long customerId);

    //Retrieve feedback related to a specific booking.
    List<Feedback> getFeedbackByBooking(Long bookingId);

    //Retrieve feedback with ratings greater than or equal to the specified minimum value.
    List<Feedback> getFeedbackByMinRating(Integer minRating);

    //Update an existing feedback record.
    Feedback updateFeedback(Long id, Feedback feedback);

    //Delete feedback by its unique ID.
    void deleteFeedback(Long id);

    //Calculate and return the average feedback rating.
    Double getAverageRating();
}
