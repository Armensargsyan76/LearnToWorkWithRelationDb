package com.companies.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListWrapper<T> {

    private List<T> data;

    private Meta meta;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Meta {
    private long total;
}

