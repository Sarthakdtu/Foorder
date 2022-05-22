package com.foorder;

import com.foorder.common.FoorderEnvironment;
import com.foorder.common.object.user.ImmutableBaseUser;
import com.foorder.common.property.KafkaProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class FoorderBackendApplication {

    public static void main(String[] args) {
        ImmutableBaseUser user = ImmutableBaseUser.builder().username("Sarthak").build();
        System.out.println(user.username());
        System.out.println(FoorderEnvironment.getKafka(KafkaProperty.BROKER_URL));
        SpringApplication.run(FoorderBackendApplication.class, args);
    }
}
