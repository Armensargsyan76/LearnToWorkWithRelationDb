package com.companies.controller;

import com.companies.dto.address.AddressFilterRequestDto;
import com.companies.dto.address.AddressRequestDto;
import com.companies.dto.address.AddressResponseDto;
import com.companies.service.AddressService;
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
@RequestMapping("/v1/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAddressList() {
        return ResponseEntity.ok(addressService.getAddressList());
    }

    @PostMapping("/filter")
    public ResponseEntity<?> getByFilter(@RequestBody AddressFilterRequestDto dto) {
        dto.setPage(dto.getPage() - 1);
        return ResponseEntity.ok(addressService.getByFilter(dto));
    }

    @PostMapping
    public ResponseEntity<AddressResponseDto> createAddress(@RequestBody AddressRequestDto requestDto) {
        return ResponseEntity.ok(addressService.createAddress(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDto> updateAddress(@PathVariable("id") Long id, @RequestBody AddressRequestDto requestDto) {
        return ResponseEntity.ok(addressService.updateAddress(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable("id") Long id) {
        addressService.deleteAddress(id);
    }
}
