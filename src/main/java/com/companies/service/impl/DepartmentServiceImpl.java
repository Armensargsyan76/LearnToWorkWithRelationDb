package com.companies.service.impl;

import com.companies.dto.department.DepartmentRequestDto;
import com.companies.dto.department.DepartmentResponseDto;
import com.companies.entity.Department;
import com.companies.mapper.DepartmentMapper;
import com.companies.repository.DepartmentRepository;
import com.companies.service.DepartmentService;
import javax.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentMapper departmentMapper;
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(@Lazy DepartmentMapper departmentMapper, DepartmentRepository departmentRepository) {
        this.departmentMapper = departmentMapper;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentResponseDto getDepartmentById(Long id) {
        return departmentMapper.toResponseDto(departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("not found department by id = %s", id))));
    }

    @Override
    public List<DepartmentResponseDto> getDepartmentList() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toResponseDto)
                .toList();
    }

    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {
        Department department = departmentMapper.fromRequestDto(requestDto);
        return departmentMapper.toResponseDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto requestDto) {
        existDepartment(id);
        Department department = departmentMapper.fromRequestDto(requestDto);
        department.setId(id);
        return departmentMapper.toResponseDto(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartment(Long id) {
        existDepartment(id);
        departmentRepository.deleteById(id);
    }

    private void existDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("not found department by id = %s", id));
        }
    }
}
