package com.companies.mapper;

import com.companies.dto.address.AddressRequestDto;
import com.companies.dto.address.AddressResponseDto;
import com.companies.dto.employee.EmployeeResponseForAddressDto;
import com.companies.entity.Address;
import com.companies.entity.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public abstract class AddressMapper {

    @Autowired
    EmployeeMapper employeeMapper;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "employees", expression = "java(mapEmployeesToEmployeesForList(address.getEmployees()))")
    @Mapping(target = "companyName", source = "address.company.name")
    public abstract AddressResponseDto toResponseDto(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    public abstract Address fromRequestDto(AddressRequestDto dto);

    protected List<EmployeeResponseForAddressDto> mapEmployeesToEmployeesForList(List<Employee> employees) {
        if (employees != null && !employees.isEmpty()) {
            return employees.stream()
                    .map(employeeMapper::mapEmployeeToForAddressDto)
                    .toList();
        }
        return null;
    }
}
