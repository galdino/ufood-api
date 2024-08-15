package com.galdino.ufood.api.controller;

import com.galdino.ufood.domain.filter.DailySaleFilter;
import com.galdino.ufood.domain.model.dto.DailySale;
import com.galdino.ufood.domain.service.SaleQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/insights")
public class InsightController {

    private final SaleQueryService saleQueryService;

    public InsightController(SaleQueryService saleQueryService) {
this.saleQueryService = saleQueryService;
    }

    @GetMapping("/daily-sales")
public List<DailySale> findDailySales(DailySaleFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return saleQueryService.findDailySales(filter, timeOffset);
    }
}
