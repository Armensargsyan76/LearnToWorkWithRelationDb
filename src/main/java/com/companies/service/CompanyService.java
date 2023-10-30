package com.companies.service;

import com.companies.dto.company.CompanyRequestDto;
import com.companies.dto.company.CompanyResponseDto;
import com.companies.utils.ListWrapper;

import java.util.List;

public interface CompanyService {

    CompanyResponseDto getCompanyById(Long id);

    List<CompanyResponseDto> getCompanyList();

    CompanyResponseDto createCompany(CompanyRequestDto requestDto);

    CompanyResponseDto updateCompany(Long id, CompanyRequestDto requestDto);

    void deleteCompany(Long id);
}
