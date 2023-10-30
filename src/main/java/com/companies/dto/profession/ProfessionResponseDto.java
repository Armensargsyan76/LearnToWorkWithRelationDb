package com.companies.dto.profession;

import com.companies.dto.employee.EmployeeResponseForProfessionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionResponseDto {

    private String name;

    private List<EmployeeResponseForProfessionDto> employees;
}
