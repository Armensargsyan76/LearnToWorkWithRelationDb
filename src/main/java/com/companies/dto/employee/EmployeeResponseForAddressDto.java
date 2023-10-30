package com.companies.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseForAddressDto {

    private String name;

    private String surname;

    private int age;

    private Double salary;

    private String phone;

    private String departmentName;

    private String companyName;
}
