package com.companies.service.impl;

import com.companies.dto.address.AddressFilterRequestDto;
import com.companies.dto.address.AddressRequestDto;
import com.companies.dto.address.AddressResponseDto;
import com.companies.entity.Address;
import com.companies.entity.Address_;
import com.companies.mapper.AddressMapper;
import com.companies.repository.AddressRepository;
import com.companies.service.AddressService;
import com.companies.utils.ListWrapper;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.companies.specification.AddressSpecification.getAddressByCompanyName;
import static com.companies.specification.AddressSpecification.getAddressByEmployeeName;
import static com.companies.specification.AddressSpecification.getAddressByIds;
import static com.companies.specification.AddressSpecification.getByAddressEmployeeAgeGreaterThan;
import static com.companies.specification.AddressSpecification.getByAddressEmployeeCompany;
import static com.companies.specification.AddressSpecification.getByAddressEmployeeIsBusy;
import static com.companies.specification.AddressSpecification.getByAddressEmployeeProfession;
import static com.companies.specification.AddressSpecification.nameLikeOrNull;
import static com.companies.utils.FilterUtil.addressFilter;
import static com.companies.utils.MakeResponseService.makeResponseForList;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    @Override
    public AddressResponseDto getAddressById(Long id) {
        return addressMapper.toResponseDto(addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("not found address by id = %s", id))));
    }

    @Override
    public List<AddressResponseDto> getAddressList() {
        return addressRepository.findAll().stream()
                .map(addressMapper::toResponseDto)
                .toList();
    }

    @Override
    public AddressResponseDto createAddress(AddressRequestDto requestDto) {
        return addressMapper.toResponseDto(addressRepository.save(addressMapper.fromRequestDto(requestDto)));
    }

    @Override
    public AddressResponseDto updateAddress(Long id, AddressRequestDto requestDto) {
        existAddress(id);
        Address address = addressMapper.fromRequestDto(requestDto);
        address.setId(id);
        return addressMapper.toResponseDto(addressRepository.save(address));
    }

    @Override
    public void deleteAddress(Long id) {
        existAddress(id);
        addressRepository.deleteById(id);
    }

    @Override
    public ListWrapper<?> getByFilter(AddressFilterRequestDto dto) {

        var sorter = addressFilter.get(dto.getSort());
        var sortDirection = "desc".equalsIgnoreCase(dto.getSortDirection()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sorter);
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getCount(), sort);

        Specification<Address> specification = Specification.where(
                nameLikeOrNull(dto.getCountry(), Address_.COUNTRY)
                        .and(nameLikeOrNull(dto.getCity(), Address_.CITY))
                        .and(nameLikeOrNull(dto.getStreetName(), Address_.STREET_NAME))
                        .and(getAddressByEmployeeName(dto.getAddressEmployeeName()))
                        .and(getAddressByCompanyName(dto.getAddressCompanyName()))
                        .and(getByAddressEmployeeIsBusy(dto.getIsAddressEmployeeBusy()))
                        .and(getByAddressEmployeeCompany(dto.getAddressEmployeeCompanyName()))
                        .and(getAddressByIds(dto.getIds()))
                        .and(getByAddressEmployeeProfession(dto.getProfessionIds()))
                        .and(getByAddressEmployeeAgeGreaterThan(dto.getAddressEmployeeAge()))

        );

        var result = addressRepository.findAll(specification, pageable);
        return makeResponseForList(result.stream().map(addressMapper::toResponseDto).collect(Collectors.toList()), result.getTotalElements());
    }

    private void existAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("not found address by id = %s", id));
        }
    }
}
