package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.filter.DailySaleFilter;
import com.galdino.ufood.domain.model.dto.DailySale;

import java.util.List;

public interface SaleQueryService {

    List<DailySale> findDailySales(DailySaleFilter filter, String timeOffset);

}
