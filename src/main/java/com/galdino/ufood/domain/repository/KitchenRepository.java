package com.galdino.ufood.domain.repository;

import com.galdino.ufood.domain.model.Kitchen;

import java.util.List;

public interface KitchenRepository {
    List<Kitchen> list();
    List<Kitchen> findByName(String name);
    Kitchen findById(Long id);
    Kitchen add(Kitchen kitchen);
    void delete(Long id);
}
