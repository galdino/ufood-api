package com.galdino.ufood.domain.service;

import com.galdino.ufood.api.v1.assembler.GenericAssembler;
import com.galdino.ufood.api.v1.model.UOrderInput;
import com.galdino.ufood.api.v1.model.UOrderItemInput;
import com.galdino.ufood.core.validation.uOrderInput.*;
import com.galdino.ufood.domain.exception.UOrderNotFoundException;
import com.galdino.ufood.domain.model.*;
import com.galdino.ufood.domain.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UOrderRegisterService {

    private final UOrderRepository uOrderRepository;
    private final RestaurantRepository restaurantRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;
    private final GenericAssembler genericAssembler;

    public UOrderRegisterService(UOrderRepository uOrderRepository, RestaurantRepository restaurantRepository, PaymentMethodRepository paymentMethodRepository, CityRepository cityRepository, UserRepository userRepository, GenericAssembler genericAssembler) {
        this.uOrderRepository = uOrderRepository;
        this.restaurantRepository = restaurantRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
        this.genericAssembler = genericAssembler;
    }

    public UOrder findOrThrow(String code) {
        return uOrderRepository.findByCode(code)
                               .orElseThrow(() -> new UOrderNotFoundException(code));
    }

    @Transactional
    public UOrder add(UOrderInput uOrderInput) {

        Optional<Restaurant> restaurant = restaurantRepository.findById(uOrderInput.getRestaurant().getId());
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(uOrderInput.getPaymentMethod().getId());
        Optional<User> user = userRepository.findById(1L);
        Optional<City> city = cityRepository.findById(uOrderInput.getAddress().getCity().getId());

        validate(uOrderInput, restaurant, paymentMethod, city);

        List<UOrderItem> uOrderItems = new ArrayList<>();

        uOrderInput.getItems().forEach(uOrderItemInput -> createUOrderItem(uOrderItemInput, uOrderItems, restaurant.get()));

        UOrder uOrder = new UOrder();

        genericAssembler.copyToObject(uOrderInput, uOrder);

        uOrder.setDeliveryFee(restaurant.get().getDeliveryFee());
        uOrder.setUorderItems(uOrderItems);
        uOrder.getUorderItems().forEach(uOrderItem -> uOrderItem.setUorder(uOrder));
        uOrder.setRestaurant(restaurant.get());
        uOrder.setPaymentMethod(paymentMethod.get());
        uOrder.setPartialAmount();
        uOrder.setTotalAmount();
        uOrder.setUser(user.get());
        uOrder.getAddress().setCity(city.get());

        return uOrderRepository.save(uOrder);
    }

    private void validate(UOrderInput uOrderInput, Optional<Restaurant> restaurant, Optional<PaymentMethod> paymentMethod, Optional<City> city) {
        List<UOrderInputValidator> validators = List.of(new UOIRestaurantValidator(restaurant),
                                                        new UOIPaymentMethodValidator(paymentMethod),
                                                        new UOIRestaurantPaymentMethodValidator(restaurant, paymentMethod),
                                                        new UOICityValidator(city),
                                                        new UOIRestaurantProductValidator(restaurant));

        validators.forEach(v -> v.validate(uOrderInput));
    }

    private void createUOrderItem(UOrderItemInput uOrderItemInput, List<UOrderItem> uOrderItems, Restaurant restaurant) {

        Optional<UOrderItem> uOrderItemOpt = restaurant.getProducts().stream()
                                                               .filter(p -> p.getId().equals(uOrderItemInput.getProductId()))
                                                               .map(pFilter -> new UOrderItem(uOrderItemInput.getQuantity(),
                                                                                              pFilter.getPrice(),
                                                                                              uOrderItemInput.getNotes(),
                                                                                              pFilter))
                                                               .findFirst();

        if (uOrderItemOpt.isPresent()) {
            UOrderItem uOrderItem = uOrderItemOpt.get();
            uOrderItem.setTotalPrice();

            uOrderItems.add(uOrderItem);
        }

    }
}
