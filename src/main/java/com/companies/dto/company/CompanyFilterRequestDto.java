package com.companies.dto.company;

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
public class CompanyFilterRequestDto extends GeneralRequestDto {

    private Long id;

    private String name;

    private int employeeCount;

    private int startEmployeeCount;

    private int endEmployeeCount;

    private String addressCountry;

    private String addressCity;

    private String addressStreetName;

    private String departmentName;
    
    private String departmentEmployeeAddress;

    private List<Long> departmentIds;

    private String companyEmployeeName;

    private List<Long> companyEmployeeProfessionIds;

    private String filterType;
}
