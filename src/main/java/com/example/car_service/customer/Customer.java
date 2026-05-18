package com.example.car_service.customer;

import com.example.car_service.user.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Inheritance
public class Customer extends BaseEntity {

    // Encapsulation :- private field, access controlled by getter/setter
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

}
