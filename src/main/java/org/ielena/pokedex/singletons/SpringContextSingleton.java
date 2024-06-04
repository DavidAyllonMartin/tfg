package org.ielena.pokedex.singletons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringContextSingleton {

    @Setter
    @Getter
    private static ConfigurableApplicationContext context;

    private SpringContextSingleton() {
    }

}

