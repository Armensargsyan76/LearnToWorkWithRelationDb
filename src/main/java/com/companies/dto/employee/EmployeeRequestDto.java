package com.companies.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDto {

    private String name;

    private String surname;

    private int age;

    private Double salary;

    private String phone;

    private Long departmentId;

    private Long addressId;

    private Long companyId;

    private List<String> professionNames;
}
