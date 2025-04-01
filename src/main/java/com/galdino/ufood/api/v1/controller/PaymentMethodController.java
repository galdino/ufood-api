package com.galdino.ufood.api.v1.controller;

import com.galdino.ufood.api.v1.assembler.GenericAssembler;
import com.galdino.ufood.api.v1.model.PaymentMethodInput;
import com.galdino.ufood.api.v1.model.PaymentMethodModel;
import com.galdino.ufood.api.v1.openapi.controller.PaymentMethodControllerOpenApi;
import com.galdino.ufood.domain.exception.PaymentMethodNotFoundException;
import com.galdino.ufood.domain.model.PaymentMethod;
import com.galdino.ufood.domain.repository.PaymentMethodRepository;
import com.galdino.ufood.domain.service.PaymentMethodRegisterService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/v1/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentMethodController implements PaymentMethodControllerOpenApi {

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
    public ResponseEntity<List<PaymentMethodModel>> list(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime maxUpdateDate = paymentMethodRepository.getMaxUpdateDate();

        if (maxUpdateDate != null) {
            eTag = String.valueOf(maxUpdateDate.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        List<PaymentMethodModel> paymentMethodModels = genericAssembler.toCollection(paymentMethodRepository.findAll(), PaymentMethodModel.class);
        return ResponseEntity.ok()
//                             .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//                             .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
//                             .cacheControl(CacheControl.noCache())
//                             .cacheControl(CacheControl.noStore())
                             .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                             .eTag(eTag)
                             .body(paymentMethodModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodModel> findById(@PathVariable Long id, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag;

        OffsetDateTime updateDateById = paymentMethodRepository.getUpdateDateById(id);

        if (updateDateById != null) {
            eTag = String.valueOf(updateDateById.toEpochSecond());
        } else {
            throw new PaymentMethodNotFoundException(id);
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        PaymentMethod paymentMethod = paymentMethodRegisterService.findOrThrow(id);
        PaymentMethodModel paymentMethodModel = genericAssembler.toClass(paymentMethod, PaymentMethodModel.class);

        return ResponseEntity.ok()
                             .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                             .eTag(eTag)
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
