package project;

import org.glassfish.jersey.server.ResourceConfig;
import project.filters.CorsFilter;

import javax.ws.rs.ApplicationPath;


/**
 * Root resource (exposed at "myresource" path)
 */
@ApplicationPath("/app")
public class MyRestApp extends ResourceConfig {

    public MyRestApp() {
        // Ovde se navodi paket u kome smo definisali servlet-mapping
        packages("project");
        register(CorsFilter.class);
    }

}
