package com.companies.dto.address;

import com.companies.dto.employee.EmployeeResponseForAddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {

    private Long id;

    private String country;

    private String city;

    private String streetName;

    private String companyName;

    private List<EmployeeResponseForAddressDto> employees;
}
