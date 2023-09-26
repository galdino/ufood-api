package com.galdino.ufood.api.controller;

import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.repository.KitchenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private KitchenRepository kitchenRepository;

    public TestController(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    @GetMapping("/kitchens")
    public List<Kitchen> findByName(@RequestParam("name") String name) {
        return kitchenRepository.findByName(name);
    }
}
