package com.galdino.ufood.infrastructure.service.report;

import com.galdino.ufood.domain.filter.DailySaleFilter;
import com.galdino.ufood.domain.service.SaleQueryService;
import com.galdino.ufood.domain.service.SaleReportService;
import org.springframework.stereotype.Service;

@Service
public class JasperSaleReportServiceImpl implements SaleReportService {

    private final SaleQueryService saleQueryService;

    public JasperSaleReportServiceImpl(SaleQueryService saleQueryService) {
        this.saleQueryService = saleQueryService;
    }

    @Override
    public byte[] buildDailySaleReport(DailySaleFilter filter, String timeOffset) {

//        try (InputStream inputStream = this.getClass().getResourceAsStream("/reports/dailysales.jasper")) {
//
//            List<DailySale> dailySales = saleQueryService.findDailySales(filter, timeOffset);
//            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dailySales);
//
//            HashMap<String, Object> parameters = new HashMap<>();
//            parameters.put("REPORT_LOCALE", new Locale("en", "US"));
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
//            return JasperExportManager.exportReportToPdf(jasperPrint);
//        } catch (Exception e) {
//            throw new ReportException("Daily Sale Report generation error.", e);
//        }

        return new byte[0];
    }

}
