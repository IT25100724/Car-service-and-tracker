package com.example.car_service.customer;

import com.example.car_service.user.BaseEntity;
import jakarta.persistence.*;

// Represents a customer profile in the system
@Entity
@Table(name = "customers")

// Inheritance form BaseEntity
public class Customer extends BaseEntity {


    // Encapsulation (Attribute as Private )
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;


    // Default constructor
    public Customer() {
    }


    // Parameterize Constructor (DTO)
    public Customer(String firstName, String lastName, String email, String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }


    // Encapsulation (Below using getter/setter)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
