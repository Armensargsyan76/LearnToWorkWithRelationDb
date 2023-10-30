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
public class EmployeeResponseDto {

    private Long id;

    private String name;

    private String surname;

    private int age;

    private Double salary;

    private String phone;

    private String departmentName;

    private String addressCountry;

    private String addressCity;

    private String addressStreetName;

    private String companyName;

    private String isBusy;

    private List<String> professionNames;
}
