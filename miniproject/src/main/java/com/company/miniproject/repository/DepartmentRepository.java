package com.company.miniproject.repository;

import com.company.miniproject.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    boolean existsByDepartmentName(String departmentName);
}
