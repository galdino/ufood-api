package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.exception.RestaurantNotFoundException;
import com.galdino.ufood.domain.model.*;
import com.galdino.ufood.domain.repository.RestaurantRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantRegisterService {

    public static final String RESTAURANT_IN_USE = "Restaurant with id %d cannot be removed, it is in use.";

    private RestaurantRepository restaurantRepository;
    private KitchenRegisterService kitchenRegisterService;
    private CityRegisterService cityRegisterService;
    private PaymentMethodRegisterService paymentMethodRegisterService;
    private final UserRegisterService userRegisterService;

    public RestaurantRegisterService(RestaurantRepository restaurantRepository, KitchenRegisterService kitchenRegisterService,
                                     CityRegisterService cityRegisterService, PaymentMethodRegisterService paymentMethodRegisterService,
                                     UserRegisterService userRegisterService) {
        this.restaurantRepository = restaurantRepository;
        this.kitchenRegisterService = kitchenRegisterService;
        this.cityRegisterService = cityRegisterService;
        this.paymentMethodRegisterService = paymentMethodRegisterService;
        this.userRegisterService = userRegisterService;
    }

    @Transactional
    public Restaurant add(Restaurant restaurant) {
        Long kitchenId = restaurant.getKitchen().getId();
        Long cityId = restaurant.getAddress().getCity().getId();

        Kitchen kitchen = kitchenRegisterService.findOrThrow(kitchenId);
        City city = cityRegisterService.findOrThrow(cityId);

        restaurant.setKitchen(kitchen);
        restaurant.getAddress().setCity(city);

        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void remove(Long id) {
        try {
            restaurantRepository.deleteById(id);
            restaurantRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RestaurantNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(RESTAURANT_IN_USE, id));
        }
    }

    @Transactional
    public void activate(Long id) {
        Restaurant restaurant = findOrThrow(id);
        restaurant.activate();
    }

    @Transactional
    public void deactivate(Long id) {
        Restaurant restaurant = findOrThrow(id);
        restaurant.deactivate();
    }

    @Transactional
    public void detachPaymentMethod(Long rId, Long pId) {
        Restaurant restaurant = findOrThrow(rId);
        PaymentMethod paymentMethod = paymentMethodRegisterService.findOrThrow(pId);

        restaurant.removePaymentMethod(paymentMethod);
    }

    @Transactional
    public void attachPaymentMethod(Long rId, Long pId) {
        Restaurant restaurant = findOrThrow(rId);
        PaymentMethod paymentMethod = paymentMethodRegisterService.findOrThrow(pId);

        restaurant.addPaymentMethod(paymentMethod);
    }

    @Transactional
    public void detachUser(Long rId, Long uId) {
        Restaurant restaurant = findOrThrow(rId);
        User user = userRegisterService.findOrThrow(uId);

        restaurant.removeUser(user);
    }

    @Transactional
    public void attachUser(Long rId, Long uId) {
        Restaurant restaurant = findOrThrow(rId);
        User user = userRegisterService.findOrThrow(uId);

        restaurant.addUser(user);
    }

    @Transactional
    public void close(Long id) {
        Restaurant restaurant = findOrThrow(id);
        restaurant.close();
    }

    @Transactional
    public void open(Long id) {
        Restaurant restaurant = findOrThrow(id);
        restaurant.open();
    }

    public Restaurant findOrThrow(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
    }

}
