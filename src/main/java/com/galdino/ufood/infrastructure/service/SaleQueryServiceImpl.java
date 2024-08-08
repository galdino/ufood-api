package com.galdino.ufood.infrastructure.service;

import com.galdino.ufood.domain.filter.DailySaleFilter;
import com.galdino.ufood.domain.model.dto.DailySale;
import com.galdino.ufood.domain.service.SaleQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleQueryServiceImpl implements SaleQueryService {
    @Override
    public List<DailySale> findDailySales(DailySaleFilter filter) {
        return null;
    }
}
