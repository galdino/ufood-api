package com.galdino.ufood.jpa;

import com.galdino.ufood.UfoodApiApplication;
import com.galdino.ufood.domain.repository.KitchenRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class KitchenFindMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(UfoodApiApplication.class)
                                                        .web(WebApplicationType.NONE)
                                                        .run(args);
        KitchenRepository kitchenRepository = applicationContext.getBean(KitchenRepository.class);

//        Kitchen kitchen = kitchenRepository.findById(1L);
//        System.out.println(kitchen.getName());
    }
}
