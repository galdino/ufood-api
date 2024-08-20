package com.galdino.ufood.infrastructure.service.report;

import com.galdino.ufood.domain.filter.DailySaleFilter;
import com.galdino.ufood.domain.service.SaleReportService;
import org.springframework.stereotype.Service;

@Service
public class JasperSaleReportServiceImpl implements SaleReportService {

    @Override
    public byte[] buildDailySaleReport(DailySaleFilter filter, String timeOffset) {
        return new byte[0];
    }

}
