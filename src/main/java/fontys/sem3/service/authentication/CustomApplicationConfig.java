package fontys.sem3.service.authentication;

import org.glassfish.jersey.server.ResourceConfig;

public class CustomApplicationConfig extends ResourceConfig {

    public CustomApplicationConfig() {
        packages("fontys.sem3.service.resources");
        // register AuthenticationFilter
        register(AuthenticationFilter.class);
    }
}
