package com.example.car_service.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//Repository interface for managing feedback entities.
//Provides database operations related to customer feedback and ratings.
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByCustomerId(Long customerId);

    List<Feedback> findByBookingId(Long bookingId);

    List<Feedback> findByRatingGreaterThanEqual(Integer rating);
}
