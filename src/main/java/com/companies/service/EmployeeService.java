package com.companies.service;

import com.companies.dto.employee.EmployeeFilterRequestDto;
import com.companies.dto.employee.EmployeeRequestDto;
import com.companies.dto.employee.EmployeeResponseDto;
import com.companies.utils.ListWrapper;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDto getEmployeeById(Long id);

    List<EmployeeResponseDto> getEmployeeList();

    EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto);

    EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto requestDto);

    void deleteEmployee(Long id);

    ListWrapper<?> getByFilter(EmployeeFilterRequestDto dto);

    ListWrapper<?> getByFilterJPQL(EmployeeFilterRequestDto dto);
}
