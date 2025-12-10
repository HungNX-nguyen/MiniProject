package com.company.miniproject.service;

import com.company.miniproject.dto.DtoRequest.DepartmentRequest;
import com.company.miniproject.dto.DtoRespone.DepartmentRespone;

import java.util.List;

public interface DepartmentService {
    List<DepartmentRespone> getAllDepartments();
    DepartmentRespone getDepartmentById(int departmentId);
    DepartmentRespone createDepartment(DepartmentRequest departmentRequest);
    DepartmentRespone updateDepartment(int id, DepartmentRequest departmentRequest);
    void deleteDepartment(int id);
}
