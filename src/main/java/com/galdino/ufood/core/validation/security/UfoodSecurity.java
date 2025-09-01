package com.galdino.ufood.core.validation.security;

import com.galdino.ufood.domain.repository.RestaurantRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class UfoodSecurity {

    private final RestaurantRepository restaurantRepository;

    public UfoodSecurity(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("user_id");
    }

    public boolean manageRestaurants(Long restaurantId) {
        return restaurantRepository.existsUser(restaurantId, getUserId());
    }

    public boolean authenticatedUserIsEqualTo(Long userId) {
        return getUserId() != null && userId != null && getUserId().equals(userId);
    }

}
