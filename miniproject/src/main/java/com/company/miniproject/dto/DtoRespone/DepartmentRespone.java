package com.company.miniproject.dto.DtoRespone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DepartmentRespone {
    private int departmentId;
    private String departmentName;
    private String description;
    private int employeeCount;
}
