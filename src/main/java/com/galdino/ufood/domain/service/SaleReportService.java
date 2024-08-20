package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.filter.DailySaleFilter;

public interface SaleReportService {

    byte[] buildDailySaleReport(DailySaleFilter filter, String timeOffset);

}
