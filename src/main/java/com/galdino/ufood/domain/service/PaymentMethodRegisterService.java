package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.exception.PaymentMethodNotFoundException;
import com.galdino.ufood.domain.model.PaymentMethod;
import com.galdino.ufood.domain.repository.PaymentMethodRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentMethodRegisterService {

    public static final String UNABLE_TO_FIND_PAYMENT_METHOD = "Unable to find payment method with id %d";

    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodRegisterService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Transactional
    public PaymentMethod add(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod findOrThrow(Long id) {
        return paymentMethodRepository.findById(id).orElseThrow(() -> new PaymentMethodNotFoundException(id));
    }

    @Transactional
    public void remove(Long id) {
        try {
            paymentMethodRepository.deleteById(id);
            paymentMethodRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PaymentMethodNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("Payment method with id %d cannot be removed, it is in use." , id));
        }
    }
}
