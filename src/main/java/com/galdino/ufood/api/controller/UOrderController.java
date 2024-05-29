package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.model.UOrderModel;
import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.repository.UOrderRepository;
import com.galdino.ufood.domain.service.UOrderRegisterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/uorders")
public class UOrderController {

    private final UOrderRegisterService uOrderRegisterService;

    private final GenericAssembler genericAssembler;

    private final UOrderRepository uOrderRepository;

    public UOrderController(UOrderRegisterService uOrderRegisterService, GenericAssembler genericAssembler, UOrderRepository uOrderRepository) {
        this.uOrderRegisterService = uOrderRegisterService;
        this.genericAssembler = genericAssembler;
        this.uOrderRepository = uOrderRepository;
    }

    @GetMapping
    public List<UOrderModel> list() {
        return genericAssembler.toCollection(uOrderRepository.findAll(), UOrderModel.class);
    }

    @GetMapping("/{id}")
    public UOrderModel findById(@PathVariable Long id) {
        UOrder uOrder = uOrderRegisterService.findOrThrow(id);
        return genericAssembler.toClass(uOrder, UOrderModel.class);
    }

}
