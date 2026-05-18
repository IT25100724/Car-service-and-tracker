
package com.example.car_service.staff;

import java.util.List;


public interface StaffService {
    Staff addStaff(Staff staff);
    Staff getStaffById(Long id);
    List<Staff> getAllStaff();
    List<Staff> getActiveStaff();
    List<Staff> getStaffByRole(StaffRole role);
    Staff updateStaff(Long id, Staff staff);
    void deleteStaff(Long id);
    Staff toggleActive(Long id);
}
