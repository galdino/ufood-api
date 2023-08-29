package com.galdino.ufood.jpa;

import com.galdino.ufood.UfoodApiApplication;
import com.galdino.ufood.domain.model.Kitchen;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class KitchenUpdateMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(UfoodApiApplication.class)
                                                        .web(WebApplicationType.NONE)
                                                        .run(args);
        KitchenRegistration kitchenRegistration = applicationContext.getBean(KitchenRegistration.class);

        Kitchen kitchen = new Kitchen();
        kitchen.setId(1L);
        kitchen.setName("Brazilian");

        kitchenRegistration.add(kitchen);

    }
}
