package com.galdino.ufood.api.v1.controller;

import com.galdino.ufood.domain.service.FlowUOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/uorders/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlowUOrderController {

    private final FlowUOrderService flowUOrderService;

    public FlowUOrderController(FlowUOrderService flowUOrderService) {
        this.flowUOrderService = flowUOrderService;
    }

    @PutMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable String code) {
        flowUOrderService.confirm(code);
    }

    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable String code) {
        flowUOrderService.cancel(code);
    }

    @PutMapping("/deliver")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deliver(@PathVariable String code) {
        flowUOrderService.deliver(code);
    }
}
