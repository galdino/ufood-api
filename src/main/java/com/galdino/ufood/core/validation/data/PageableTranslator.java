package com.galdino.ufood.core.validation.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PageableTranslator {
    public static void translate(Pageable pageable, Map<String, String> fieldsMapping) {
        List<Sort.Order> orderList = pageable.getSort().stream()
                                                       .filter(order -> fieldsMapping.containsKey(order.getProperty()))
                                                       .map(order -> new Sort.Order(order.getDirection(), fieldsMapping.get(order.getProperty())))
                                                       .collect(Collectors.toList());

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orderList));
    }
}
