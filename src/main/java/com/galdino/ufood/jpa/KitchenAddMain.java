package com.galdino.ufood.jpa;

import com.galdino.ufood.UfoodApiApplication;
import com.galdino.ufood.domain.model.Kitchen;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class KitchenAddMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(UfoodApiApplication.class)
                                                        .web(WebApplicationType.NONE)
                                                        .run(args);
        KitchenRegistration kitchenRegistration = applicationContext.getBean(KitchenRegistration.class);

        Kitchen kitchen1 = new Kitchen();
        kitchen1.setName("Brazilian");

        Kitchen kitchen2 = new Kitchen();
        kitchen2.setName("Japanese");

        kitchen1 = kitchenRegistration.add(kitchen1);
        kitchen2 = kitchenRegistration.add(kitchen2);

        System.out.printf("%d - %s\n", kitchen1.getId(), kitchen1.getName());
        System.out.printf("%d - %s\n", kitchen2.getId(), kitchen2.getName());

    }
}
