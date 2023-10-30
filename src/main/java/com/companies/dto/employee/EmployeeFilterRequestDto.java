package com.companies.dto.employee;

import com.companies.dto.GeneralRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeFilterRequestDto extends GeneralRequestDto {

    private List<Long> ids;

    private String country;

    private String city;

    private String streetName;

    private String CompanyName;

    private String name;

    private String surname;

    private Integer age;

    private Double salary;

    private Double startSalary;

    private Double endSalary;

    private String phone;

    private String departmentName;

    private Boolean isBusy;

    private String professionName;

    private List<Long> professionIds;
}
