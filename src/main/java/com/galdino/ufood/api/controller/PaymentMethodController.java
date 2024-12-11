package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.model.PaymentMethodInput;
import com.galdino.ufood.api.model.PaymentMethodModel;
import com.galdino.ufood.domain.model.PaymentMethod;
import com.galdino.ufood.domain.repository.PaymentMethodRepository;
import com.galdino.ufood.domain.service.PaymentMethodRegisterService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    private PaymentMethodRepository paymentMethodRepository;
    private PaymentMethodRegisterService paymentMethodRegisterService;
    private GenericAssembler genericAssembler;

    public PaymentMethodController(PaymentMethodRepository paymentMethodRepository,
                                   PaymentMethodRegisterService paymentMethodRegisterService,
                                   GenericAssembler genericAssembler) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodRegisterService = paymentMethodRegisterService;
        this.genericAssembler = genericAssembler;
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodModel>> list() {
        List<PaymentMethodModel> paymentMethodModels = genericAssembler.toCollection(paymentMethodRepository.findAll(), PaymentMethodModel.class);
        return ResponseEntity.ok()
//                             .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//                             .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
                             .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
//                             .cacheControl(CacheControl.noCache())
//                             .cacheControl(CacheControl.noStore())
                             .body(paymentMethodModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodModel> findById(@PathVariable Long id) {
        PaymentMethod paymentMethod = paymentMethodRegisterService.findOrThrow(id);
        PaymentMethodModel paymentMethodModel = genericAssembler.toClass(paymentMethod, PaymentMethodModel.class);

        return ResponseEntity.ok()
                             .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                             .body(paymentMethodModel);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodModel add(@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
        PaymentMethod paymentMethod = genericAssembler.toClass(paymentMethodInput, PaymentMethod.class);
        paymentMethod = paymentMethodRegisterService.add(paymentMethod);
        return genericAssembler.toClass(paymentMethod, PaymentMethodModel.class);
    }

    @PutMapping("/{id}")
    public PaymentMethodModel update(@PathVariable Long id, @RequestBody @Valid PaymentMethodInput paymentMethodInput) {
        PaymentMethod paymentMethod = paymentMethodRegisterService.findOrThrow(id);

        genericAssembler.copyToObject(paymentMethodInput, paymentMethod);

        paymentMethod = paymentMethodRegisterService.add(paymentMethod);

        return genericAssembler.toClass(paymentMethod, PaymentMethodModel.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        paymentMethodRegisterService.remove(id);
    }

}
