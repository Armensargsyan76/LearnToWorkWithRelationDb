package com.companies.dto.address;

import com.companies.dto.GeneralRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddressFilterRequestDto extends GeneralRequestDto {
    
    private List<Long> ids;

    private String country;

    private String city;

    private String streetName;

    private String addressCompanyName;

    private String addressEmployeeName;

    private String addressEmployeeCompanyName;

    private Boolean isAddressEmployeeBusy;

    private Integer addressEmployeeAge;

    private List<Long> professionIds;
}
