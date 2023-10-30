package com.companies.utils;

import java.util.List;

public class MakeResponseService {

    public static ListWrapper<?> makeResponseForList(List<?> dto, Long total) {
        var meta = new Meta(total);
        return new ListWrapper<>(dto, meta);
    }
}
