package com.galdino.ufood.jpa;

import com.galdino.ufood.UfoodApiApplication;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.repository.KitchenRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class KitchenConsultationMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(UfoodApiApplication.class)
                                                        .web(WebApplicationType.NONE)
                                                        .run(args);
        KitchenRepository kitchenRepository = applicationContext.getBean(KitchenRepository.class);

        List<Kitchen> kitchens = kitchenRepository.list();
        kitchens.forEach(kitchen -> System.out.println(kitchen.getName()));
    }
}
