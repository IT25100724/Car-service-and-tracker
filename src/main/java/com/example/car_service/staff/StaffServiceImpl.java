package com.example.car_service.staff;

import com.example.car_service.exception.DuplicateResourceException;
import com.example.car_service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * STAFF SERVICE IMPLEMENTATION
 * =============================
 * Handles business rules for employees.
 */
@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Override
    public Staff addStaff(Staff staff) {
        if (staffRepository.existsByEmail(staff.getEmail())) {
            throw new DuplicateResourceException("Staff with email '" + staff.getEmail() + "' already exists");
        }
        return staffRepository.save(staff);
    }

    @Override
    public Staff getStaffById(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + id));
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public List<Staff> getActiveStaff() {
        return staffRepository.findByActive(true);
    }

    @Override
    public List<Staff> getStaffByRole(StaffRole role) {
        return staffRepository.findByRole(role);
    }

    @Override
    public Staff updateStaff(Long id, Staff staff) {
        Staff existing = getStaffById(id);
        existing.setFirstName(staff.getFirstName());
        existing.setLastName(staff.getLastName());
        existing.setEmail(staff.getEmail());
        existing.setPhone(staff.getPhone());
        existing.setRole(staff.getRole());
        existing.setSalary(staff.getSalary());
        existing.setHireDate(staff.getHireDate());
        existing.setActive(staff.isActive());
        return staffRepository.save(existing);
    }

    @Override
    public void deleteStaff(Long id) {
        getStaffById(id);
        staffRepository.deleteById(id);
    }

    /** Helper method to quickly activate/deactivate an employee */
    @Override
    public Staff toggleActive(Long id) {
        Staff staff = getStaffById(id);
        staff.setActive(!staff.isActive());
        return staffRepository.save(staff);
    }
}