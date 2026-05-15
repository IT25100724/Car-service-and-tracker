package com.example.car_service.staff;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * STAFF ENTITY — MODULE 3: STAFF MANAGEMENT
 * ==========================================
 * Represents an employee working at the car service center.
 * Maps to the "staff" table in the database.
 */
@Entity
@Table(name = "staff")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {

    /** Auto-generated primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    /** Email must be unique for each employee */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    /** The job position (e.g., MECHANIC, MANAGER) */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private StaffRole role;

    /** Employee's salary */
    @Column(name = "salary")
    private Double salary;

    /** Date the employee was hired */
    @Column(name = "hire_date")
    private LocalDate hireDate;

    /** Is the employee currently active (working)? */
    @Column(name = "active")
    @Builder.Default
    private boolean active = true;
}
