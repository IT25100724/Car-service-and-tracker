package com.example.car_service.staff;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface StaffRepository extends JpaRepository<Staff, Long> {

    /** Find staff by their employment status (active or inactive) */
    List<Staff> findByActive(boolean active);

    /** Find staff by their job role */
    List<Staff> findByRole(StaffRole role);

    /** Check if an email is already assigned to a staff member */
    boolean existsByEmail(String email);
}
