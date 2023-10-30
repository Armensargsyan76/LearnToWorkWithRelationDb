package com.companies.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonView
public class GeneralRequestDto {

    @Builder.Default
    private String sortDirection = "desc";

    @Builder.Default
    private String sort = "id";

    private Integer page;

    private Integer count;
}
