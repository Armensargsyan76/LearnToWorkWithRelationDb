package com.companies.service;

import com.companies.dto.address.AddressFilterRequestDto;
import com.companies.dto.address.AddressRequestDto;
import com.companies.dto.address.AddressResponseDto;
import com.companies.utils.ListWrapper;

import java.util.List;

public interface AddressService {

    AddressResponseDto getAddressById(Long id);

    List<AddressResponseDto> getAddressList();

    AddressResponseDto createAddress(AddressRequestDto requestDto);

    AddressResponseDto updateAddress(Long id, AddressRequestDto requestDto);

    void deleteAddress(Long id);

    ListWrapper<?> getByFilter(AddressFilterRequestDto dto);
}
