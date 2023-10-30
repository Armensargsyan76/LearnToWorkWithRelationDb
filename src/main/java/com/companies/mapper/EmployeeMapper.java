package com.companies.mapper;

import com.companies.dto.employee.EmployeeRequestDto;
import com.companies.dto.employee.EmployeeResponseDto;
import com.companies.dto.employee.EmployeeResponseForAddressDto;
import com.companies.entity.Address;
import com.companies.entity.Company;
import com.companies.entity.Department;
import com.companies.entity.Employee;
import com.companies.entity.Profession;
import com.companies.repository.AddressRepository;
import com.companies.repository.CompanyRepository;
import com.companies.repository.DepartmentRepository;
import com.companies.repository.ProfessionRepository;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper
public abstract class EmployeeMapper {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProfessionRepository professionRepository;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "departmentName", source = "employee.department.name")
    @Mapping(target = "addressCountry", source = "employee.address.country")
    @Mapping(target = "addressCity", source = "employee.address.city")
    @Mapping(target = "addressStreetName", source = "employee.address.streetName")
    @Mapping(target = "companyName", source = "employee.company.name")
    @Mapping(target = "professionNames", expression = "java(getProfessionNamesByProfessions(employee.getProfessions()))")
    public abstract EmployeeResponseDto toResponseDto(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "department", expression = "java(getDepartmentById(dto.getDepartmentId()))")
    @Mapping(target = "address", expression = "java(getAddressById(dto.getAddressId()))")
    @Mapping(target = "company", expression = "java(getCompanyById(dto.getCompanyId()))")
    @Mapping(target = "isBusy", expression = "java(dto.getCompanyId() != null ? true : false)")
    @Mapping(target = "professions", expression = "java(getProfessionsByNames(dto.getProfessionNames()))")
    public abstract Employee fromRequestDto(EmployeeRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "departmentName", source = "employee.department.name")
    @Mapping(target = "companyName", source = "employee.company.name")
    public abstract EmployeeResponseForAddressDto mapEmployeeToForAddressDto(Employee employee);

    protected List<String> getProfessionNamesByProfessions(Set<Profession> professions) {
        return professions.stream().map(Profession::getName).toList();
    }

    protected Set<Profession> getProfessionsByNames(List<String> professionNames) {
        return new HashSet<>(professionRepository.getAllByNameIn(professionNames));
    }

    protected Department getDepartmentById(Long id) {
        if (id != null && departmentRepository.findById(id).isPresent()) {
            return departmentRepository.findById(id).get();
        }
        return null;
    }

    protected Address getAddressById(Long id) {
        if (addressRepository.findById(id).isPresent()) {
            return addressRepository.findById(id).get();
        }
        return null;
    }

    protected Company getCompanyById(Long id) {
        if (id != null && companyRepository.findById(id).isPresent()) {
            return companyRepository.findById(id).get();
        }
        return null;
    }
}
