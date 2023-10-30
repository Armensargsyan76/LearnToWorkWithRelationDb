package com.companies.mapper;

import com.companies.dto.company.CompanyRequestDto;
import com.companies.dto.company.CompanyResponseDto;
import com.companies.entity.Address;
import com.companies.entity.Company;
import com.companies.entity.Department;
import com.companies.repository.AddressRepository;
import com.companies.repository.DepartmentRepository;
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
public abstract class CompanyMapper {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "departmentNames", expression = "java(getDepartmentNames(company.getDepartments()))")
    @Mapping(target = "addressCountry", source = "company.address.country")
    @Mapping(target = "addressCity", source = "company.address.city")
    @Mapping(target = "addressStreetName", source = "company.address.streetName")
    public abstract CompanyResponseDto toResponseDto(Company company);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "address", expression = "java(getAddressById(dto.getAddressId()))")
    @Mapping(target = "departments", expression = "java(getDepartmentsByIds(dto.getDepartmentIds()))")
    public abstract Company fromRequestDto(CompanyRequestDto dto);

    protected Set<Department> getDepartmentsByIds(List<Long> ids) {
        List<Department> departments = departmentRepository.findAllById(ids);
        return new HashSet<>(departments);
    }

    protected Set<String> getDepartmentNames(Set<Department> departments) {
        return departments.stream()
                .map(Department::getName)
                .collect(Collectors.toSet());
    }

    protected Address getAddressById(Long id) {
        if (addressRepository.findById(id).isPresent()) {
            return addressRepository.findById(id).get();
        }
        return null;
    }
}
