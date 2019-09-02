package seeder;

import seeder.AuthorityTableSeed;
import seeder.UserTableSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevDatabaseSeederImpl implements DatabaseSeeder {

    @Autowired
    AuthorityTableSeed authorityTableSeed;

    @Autowired
    UserTableSeed userTableSeed;

    @Override
    //@EventListener
    public void seed(ContextRefreshedEvent event) {
        authorityTableSeed.seed("admin");
        authorityTableSeed.seed("regular");

        userTableSeed.seed("admin1");
        userTableSeed.seed("admin2");
        userTableSeed.seed("regular1");
        userTableSeed.seed("regular2");

    }
}
