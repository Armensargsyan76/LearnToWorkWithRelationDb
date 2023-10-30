package com.companies.service.impl;

import com.companies.dto.company.CompanyRequestDto;
import com.companies.dto.company.CompanyResponseDto;
import com.companies.entity.Address;
import com.companies.entity.Company;
import com.companies.mapper.CompanyMapper;
import com.companies.repository.AddressRepository;
import com.companies.repository.CompanyRepository;
import com.companies.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;

    @Override
    public CompanyResponseDto getCompanyById(Long id) {

        return companyMapper.toResponseDto(companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("not found company by id = %s", id))));
    }

    @Override
    public List<CompanyResponseDto> getCompanyList() {
        return companyRepository.findAll().stream()
                .map(companyMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public CompanyResponseDto createCompany(CompanyRequestDto requestDto) {
        Company company = companyMapper.fromRequestDto(requestDto);
        addCompanyInAddress(company);
        return companyMapper.toResponseDto(companyRepository.save(company));
    }

    @Override
    public CompanyResponseDto updateCompany(Long id, CompanyRequestDto requestDto) {
        existCompany(id);
        Company company = companyMapper.fromRequestDto(requestDto);
        company.setId(id);
        return companyMapper.toResponseDto(companyRepository.save(company));
    }

    @Override
    public void deleteCompany(Long id) {
        existCompany(id);
        companyRepository.deleteById(id);
    }

    private void existCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("not found company by id = %s", id));
        }
    }

    private void addCompanyInAddress(Company company) {
        Address address = company.getAddress();
        address.setCompany(company);
        addressRepository.save(address);
    }
}
