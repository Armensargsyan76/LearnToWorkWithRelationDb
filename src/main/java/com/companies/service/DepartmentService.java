package com.companies.service;

import com.companies.dto.department.DepartmentRequestDto;
import com.companies.dto.department.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {

    DepartmentResponseDto getDepartmentById(Long id);

    List<DepartmentResponseDto> getDepartmentList();

    DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto);

    DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto requestDto);

    void deleteDepartment(Long id);
}
