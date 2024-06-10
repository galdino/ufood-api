package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.model.UOrderInput;
import com.galdino.ufood.api.model.UOrderModel;
import com.galdino.ufood.api.model.UOrderSummaryModel;
import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.repository.UOrderRepository;
import com.galdino.ufood.domain.service.UOrderRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<UOrderSummaryModel> list() {
        return genericAssembler.toCollection(uOrderRepository.findAll(), UOrderSummaryModel.class);
    }

    @GetMapping("/{id}")
    public UOrderModel findById(@PathVariable Long id) {
        UOrder uOrder = uOrderRegisterService.findOrThrow(id);
        return genericAssembler.toClass(uOrder, UOrderModel.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UOrderModel add(@RequestBody @Valid UOrderInput uOrderInput) {
        try {
            UOrder uOrder = uOrderRegisterService.add(uOrderInput);
            return genericAssembler.toClass(uOrder, UOrderModel.class);
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

}
