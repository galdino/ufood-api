package com.galdino.ufood.api.controller;

import com.galdino.ufood.domain.service.FlowUOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/uorders/{id}")
public class FlowUOrderController {

    private final FlowUOrderService flowUOrderService;

    public FlowUOrderController(FlowUOrderService flowUOrderService) {
        this.flowUOrderService = flowUOrderService;
    }

    @PutMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable Long id) {
        flowUOrderService.confirm(id);
    }

    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long id) {
        flowUOrderService.cancel(id);
    }

    @PutMapping("/deliver")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deliver(@PathVariable Long id) {
        flowUOrderService.deliver(id);
    }
}
