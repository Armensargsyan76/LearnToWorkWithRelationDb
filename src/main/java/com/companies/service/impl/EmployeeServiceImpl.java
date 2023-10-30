package com.companies.service.impl;

import com.companies.dto.employee.EmployeeFilterRequestDto;
import com.companies.dto.employee.EmployeeRequestDto;
import com.companies.dto.employee.EmployeeResponseDto;
import com.companies.entity.Company;
import com.companies.entity.Employee;
import com.companies.entity.Employee_;
import com.companies.mapper.EmployeeMapper;
import com.companies.repository.CompanyRepository;
import com.companies.repository.EmployeeRepository;
import com.companies.service.EmployeeService;
import com.companies.utils.ListWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.companies.specification.EmployeeSpecification.getByBetweenSalary;
import static com.companies.specification.EmployeeSpecification.getByLessThenAge;
import static com.companies.specification.EmployeeSpecification.getNameAndSurname;
import static com.companies.specification.EmployeeSpecification.getNameOrSurname;
import static com.companies.specification.EmployeeSpecification.getNotValue;
import static com.companies.utils.FilterUtil.employeeFilter;
import static com.companies.utils.MakeResponseService.makeResponseForList;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {
        return employeeMapper.toResponseDto(employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("not found employee by id = %s", id))));
    }

    @Override
    public List<EmployeeResponseDto> getEmployeeList() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toResponseDto)
                .toList();
    }

    @Override
    public ListWrapper<?> getByFilter(EmployeeFilterRequestDto dto) {

        var sorter = employeeFilter.get(dto.getSort());
        var sortDirection = "desc".equalsIgnoreCase(dto.getSortDirection()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sorter);
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getCount(), sort);

        Specification<Employee> specification = Specification.where(
                getNameOrSurname(dto.getName(), dto.getSurname())
                        .and(getNameAndSurname(dto.getName(), dto.getSurname()))
                        .and(getNotValue(dto.getPhone(), Employee_.PHONE))
                        .and(getByLessThenAge(dto.getAge()))
                        .and(getByBetweenSalary(dto.getStartSalary(), dto.getEndSalary()))
        );
        var result = employeeRepository.findAll(specification, pageable);
        return makeResponseForList(result.stream().map(employeeMapper::toResponseDto)
                .collect(Collectors.toList()), result.getTotalElements());
    }

    @Override
    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {
        Employee employee = employeeMapper.fromRequestDto(requestDto);
        if (employee.getCompany() != null) {
            addEmployeeInCompanyForCount(employee);
        }
        return employeeMapper.toResponseDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto requestDto) {
        existEmployee(id);
        Employee employee = employeeMapper.fromRequestDto(requestDto);
        employee.setId(id);
        return employeeMapper.toResponseDto(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(Long id) {
        existEmployee(id);
        employeeRepository.deleteById(id);
    }

    @Override
    public ListWrapper<?> getByFilterJPQL(EmployeeFilterRequestDto dto) {

        List<Employee> employees = employeeRepository.findEmployeesWhereProfessionsIsEmpty();
        return makeResponseForList(employees.stream().map(employeeMapper::toResponseDto)
                .collect(Collectors.toList()), (long) employees.size());
    }

    private void existEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("not found employee by id = %s", id));
        }
    }

    private void addEmployeeInCompanyForCount(Employee employee) {
        Company company = employee.getCompany();
        company.setEmployeeCount(company.getEmployees().size() + 1);
        companyRepository.save(company);
    }
}
