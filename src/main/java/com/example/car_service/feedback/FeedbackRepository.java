package com.example.car_service.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//Repository interface for managing feedback entities.
//Provides database operations related to customer feedback and ratings.
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    //Retrieve all feedback records submitted by a specific customer.
    List<Feedback> findByCustomerId(Long customerId);

    //Retrieve all feedback records associated with a specific booking.
    List<Feedback> findByBookingId(Long bookingId);

    //Retrieve feedback records with ratings greater than or equal to the specified value.
    List<Feedback> findByRatingGreaterThanEqual(Integer rating);
}
