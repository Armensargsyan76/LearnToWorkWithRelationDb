package com.companies.controller;

import com.companies.dto.company.CompanyFilterRequestDto;
import com.companies.dto.company.CompanyRequestDto;
import com.companies.dto.company.CompanyResponseDto;
import com.companies.querydsl.CompanyQueryDSLService;
import com.companies.service.CompanyService;
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
@RequestMapping("/v1/company")
public class CompanyController {

    private final CompanyQueryDSLService companyQueryDSLService;
    private final CompanyService companyService;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getCompanyList() {
        return ResponseEntity.ok(companyService.getCompanyList());
    }

    @PostMapping("/filter")
    public ResponseEntity<?> getByFilter(@RequestBody CompanyFilterRequestDto requestDto) {
        return ResponseEntity.ok(companyQueryDSLService.getByFilterType(requestDto));
    }

    @PostMapping
    public ResponseEntity<CompanyResponseDto> createCompany(@RequestBody CompanyRequestDto requestDto) {
        return ResponseEntity.ok(companyService.createCompany(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> updateCompany(@PathVariable("id") Long id,
                                                            @RequestBody CompanyRequestDto requestDto) {
        return ResponseEntity.ok(companyService.updateCompany(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteCompany(id);
    }
}
