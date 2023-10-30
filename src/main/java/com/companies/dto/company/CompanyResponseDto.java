package com.companies.dto.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDto {

    private Long id;

    private String name;

    private String addressCountry;

    private String addressCity;

    private String addressStreetName;

    private Set<String> departmentNames;

    private int employeeCount;
}
