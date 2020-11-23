package fontys.sem3.service;


import fontys.sem3.service.authentication.AuthenticationFilter;
import fontys.sem3.service.authentication.CorsFilter;
import fontys.sem3.service.authentication.JWTTokenNeededFilter;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomApplicationConfig extends ResourceConfig
{
	public CustomApplicationConfig()
	{
		packages("fontys.sem3.service.resources"); // find all resource endpoint classes in this package
		// log exchanged http messages

/*		If there is a CORS error you have to comment the AuthenticationFilter registration here
		and run the server, afterwards, stop the server, uncomment the filter and run again */
		//register(AuthenticationFilter.class);
		register(JWTTokenNeededFilter.class);
		register(CorsFilter.class);
		register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME),
				Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, LoggingFeature.DEFAULT_MAX_ENTITY_SIZE));
	}
}
