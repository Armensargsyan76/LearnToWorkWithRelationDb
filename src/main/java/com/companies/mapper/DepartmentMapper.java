package com.companies.mapper;

import com.companies.dto.company.CompanyResponseDto;
import com.companies.dto.department.DepartmentRequestDto;
import com.companies.dto.department.DepartmentResponseDto;
import com.companies.dto.employee.EmployeeResponseDto;
import com.companies.entity.Company;
import com.companies.entity.Department;
import com.companies.entity.Employee;
import com.companies.repository.CompanyRepository;
import com.companies.repository.EmployeeRepository;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public abstract class DepartmentMapper {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "companies", expression = "java(mapCompanyToDto(department.getCompanies()))")
    @Mapping(target = "employees", expression = "java(mapEmployeeToDto(department.getEmployees()))")
    public abstract DepartmentResponseDto toResponseDto(Department department);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "employees", expression = "java(getEmployeesByIds(dto.getEmployeeIds()))")
    @Mapping(target = "companies", expression = "java(getCompaniesByIds(dto.getCompanyIds()))")
    public abstract Department fromRequestDto(DepartmentRequestDto dto);

    protected List<Employee> getEmployeesByIds(List<Long> ids) {
        if (ids == null) return null;
        return employeeRepository.findAllById(ids);
    }

    protected Set<Company> getCompaniesByIds(List<Long> ids) {
        if (ids == null) return null;
        List<Company> companies = companyRepository.findAllById(ids);
        return new HashSet<>(companies);
    }

    protected Set<CompanyResponseDto> mapCompanyToDto(Set<Company> companies) {
        if (companies == null) return null;
        return companies.stream()
                .map(companyMapper::toResponseDto)
                .collect(Collectors.toSet());
    }

    protected List<EmployeeResponseDto> mapEmployeeToDto(List<Employee> employees) {
        if (employees == null) return null;
        return employees.stream()
                .map(employeeMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
