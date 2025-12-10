package com.company.miniproject.service.impl;

import com.company.miniproject.dto.DtoRequest.DepartmentRequest;
import com.company.miniproject.dto.DtoRespone.DepartmentRespone;
import com.company.miniproject.entity.Department;
import com.company.miniproject.repository.DepartmentRepository;
import com.company.miniproject.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentRespone> getAllDepartments() {
        List<Department>  departments = departmentRepository.findAll();
        List<DepartmentRespone> departmentRespones = new ArrayList<>();
        for (Department department : departments) {
            DepartmentRespone departmentResponeFor = convertDepartmentToDepartmentRespone(department);
            departmentRespones.add(departmentResponeFor);
        }
        return departmentRespones;
    }

    @Override
    public DepartmentRespone getDepartmentById(int departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if(department.isEmpty()){
            throw new RuntimeException("Không tìm thấy phòng ban");
        }else {
            return convertDepartmentToDepartmentRespone(department.get());
        }
    }

    @Override
    public DepartmentRespone createDepartment(DepartmentRequest departmentRequest) {
        if (departmentRepository.existsByDepartmentName(departmentRequest.getDepartmentName())){
            throw new RuntimeException("Tên phòng ban đã tồn tại: " +  departmentRequest.getDepartmentName());
        }
        Department department = Department.builder()
                .departmentName(departmentRequest.getDepartmentName())
                .description(departmentRequest.getDescription())
                .build();

        Department saved = departmentRepository.save(department);
        log.info("Đã tạo phòng ban mới");
        return convertDepartmentToDepartmentRespone(saved);
    }

    @Override
    public DepartmentRespone updateDepartment(int id,DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));
        if(department.getDepartmentName().equals(departmentRequest.getDepartmentName())
                && departmentRepository.existsByDepartmentName(departmentRequest.getDepartmentName())
                && department.getDescription().equals(departmentRequest.getDescription())){
            throw new RuntimeException("Vui lòng thay đổi thông tin" + departmentRequest.getDepartmentName());
        }
        department.setDepartmentName(departmentRequest.getDepartmentName());
        department.setDescription(departmentRequest.getDescription());
        Department departmentUpdated = departmentRepository.save(department);

        return convertDepartmentToDepartmentRespone(departmentUpdated);
    }

    @Override
    public void deleteDepartment(int id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isEmpty()) {
            throw new RuntimeException("Phòng ban không tồn tại");
        }
        int employeeCount = department.get().getEmployees().size();
        if(employeeCount > 0){
            throw new RuntimeException("Không thể xóa phòng ban vì còn nhân viên " + employeeCount + " nhân viên");
        }
        departmentRepository.deleteById(id);

    }


    private DepartmentRespone convertDepartmentToDepartmentRespone(Department department) {
        int employeesCount = department.getEmployees() == null ? 0 : department.getEmployees().size();
        return DepartmentRespone.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .description(department.getDescription())
                .employeeCount(employeesCount)
                .build();
    }
}
