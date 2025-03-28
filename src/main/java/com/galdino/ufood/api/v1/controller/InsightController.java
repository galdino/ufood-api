package com.galdino.ufood.api.v1.controller;

import com.galdino.ufood.domain.filter.DailySaleFilter;
import com.galdino.ufood.domain.model.dto.DailySale;
import com.galdino.ufood.domain.service.SaleQueryService;
import com.galdino.ufood.domain.service.SaleReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/insights")
public class InsightController {

    private final SaleQueryService saleQueryService;
    private final SaleReportService saleReportService;

    public InsightController(SaleQueryService saleQueryService, SaleReportService saleReportService) {
        this.saleQueryService = saleQueryService;
        this.saleReportService = saleReportService;
    }

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySale> findDailySales(DailySaleFilter filter,
                                          @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return saleQueryService.findDailySales(filter, timeOffset);
    }

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> findDailySalesPDF(DailySaleFilter filter,
                                                    @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

        byte[] bytesReport = saleReportService.buildDailySaleReport(filter, timeOffset);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sale.pdf");

        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_PDF)
                             .headers(httpHeaders)
                             .body(bytesReport);

    }
}
