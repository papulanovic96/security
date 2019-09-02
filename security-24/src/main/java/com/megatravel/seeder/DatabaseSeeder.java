package com.megatravel.seeder;

import org.springframework.context.event.ContextRefreshedEvent;

public interface DatabaseSeeder {

    void seed(ContextRefreshedEvent event);

}
