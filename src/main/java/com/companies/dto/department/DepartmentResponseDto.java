package com.companies.dto.department;

import com.companies.dto.company.CompanyResponseDto;
import com.companies.dto.employee.EmployeeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponseDto {

    private Long id;

    private String name;

    private List<EmployeeResponseDto> employees;

    private Set<CompanyResponseDto> companies;
}
