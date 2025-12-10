package com.company.miniproject.dto.DtoRequest;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DepartmentRequest {
    @NotBlank(message = "Tên phòng ban không được để trống")
    @Size(max = 100, message = "Tên phòng ban không được quá 100 ký tự")
    private String departmentName;

    @Size(max = 200, message = "Mô tả không quá 200 ký tự")
    private String description;
}
