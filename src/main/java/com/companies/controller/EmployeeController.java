package com.companies.controller;

import com.companies.dto.address.AddressFilterRequestDto;
import com.companies.dto.employee.EmployeeFilterRequestDto;
import com.companies.dto.employee.EmployeeRequestDto;
import com.companies.dto.employee.EmployeeResponseDto;
import com.companies.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeeList() {
        return ResponseEntity.ok(employeeService.getEmployeeList());
    }

    @PostMapping("/filter/spec")
    public ResponseEntity<?> getByFilter(@RequestBody EmployeeFilterRequestDto dto) {
        dto.setPage(dto.getPage() - 1);
        return ResponseEntity.ok(employeeService.getByFilter(dto));
    }

    @PostMapping("/filter/jpql")
    public ResponseEntity<?> getByFilterJPQL(@RequestBody EmployeeFilterRequestDto dto) {
        dto.setPage(dto.getPage() - 1);
        return ResponseEntity.ok(employeeService.getByFilterJPQL(dto));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeRequestDto requestDto) {
        return ResponseEntity.ok(employeeService.createEmployee(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable("id") Long id,
                                                                  @RequestBody EmployeeRequestDto requestDto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
    }
}
