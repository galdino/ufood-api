package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.model.UOrderInput;
import com.galdino.ufood.api.model.UOrderModel;
import com.galdino.ufood.api.model.UOrderSummaryModel;
import com.galdino.ufood.core.validation.data.PageableTranslator;
import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.filter.UOrderFilter;
import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.repository.UOrderRepository;
import com.galdino.ufood.domain.service.UOrderRegisterService;
import com.galdino.ufood.infrastructure.repository.spec.UOrderSpecs;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

//    @GetMapping
//    public MappingJacksonValue list(@RequestParam(required = false) String fields) {
//        List<UOrderSummaryModel> uOrderSummaryModelList = genericAssembler.toCollection(uOrderRepository.findAll(), UOrderSummaryModel.class);
//
//        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
//        simpleFilterProvider.addFilter("uOrderFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if (StringUtils.isNotBlank(fields)) {
//            simpleFilterProvider.addFilter("uOrderFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
//        }
//
//        MappingJacksonValue uOrderSummaryModelWrapper = new MappingJacksonValue(uOrderSummaryModelList);
//        uOrderSummaryModelWrapper.setFilters(simpleFilterProvider);
//
//        return uOrderSummaryModelWrapper;
//    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Fields names for filtering in the response, separated by commas", name = "fields",
                              paramType = "query", type = "string")
    })
    @GetMapping
    public Page<UOrderSummaryModel> search(UOrderFilter filter, @PageableDefault(size = 1) Pageable pageable) {
        pageableTranslator(pageable);

        List<UOrder> uOrderList = uOrderRepository.findAll(UOrderSpecs.useFilter(filter), pageable).getContent();

        List<UOrderSummaryModel> uOrderSummaryModelList = genericAssembler.toCollection(uOrderList, UOrderSummaryModel.class);

        return new PageImpl<>(uOrderSummaryModelList, pageable, uOrderSummaryModelList.size());
    }

    private void pageableTranslator(Pageable pageable) {
        ImmutableMap<String, String> map = ImmutableMap.of("code", "code",
                                                           "restaurant.name", "restaurant.name",
                                                           "userName", "user.name",
                                                           "totalAmount", "totalAmount");

        PageableTranslator.translate(pageable, map);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Fields names for filtering in the response, separated by commas", name = "fields",
                              paramType = "query", type = "string")
    })
    @GetMapping("/{code}")
    public UOrderModel findByCode(@PathVariable String code) {
        UOrder uOrder = uOrderRegisterService.findOrThrow(code);
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
