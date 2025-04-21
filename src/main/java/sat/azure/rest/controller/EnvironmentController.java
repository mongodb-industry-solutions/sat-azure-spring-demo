package sat.azure.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin // Allows cross-origin requests (for development purposes)
public class EnvironmentController {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentController.class);

    @Autowired
    private Environment environment;

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Map<String, String>> getEnvironmentData() {
        logger.info("Received OPTIONS request for /api to get environment data.");
        Map<String, String> environmentVariables = new HashMap<>();
        environmentVariables.put("MONGO_URI", environment.getProperty("MONGO_URI"));

        // Add dynamically all environment vars into to the hashmap structure
        org.springframework.core.env.MutablePropertySources propertySources
                = ((org.springframework.core.env.ConfigurableEnvironment) environment).getPropertySources();

        for (org.springframework.core.env.PropertySource<?> propertySource : propertySources) {
            if (propertySource instanceof org.springframework.core.env.EnumerablePropertySource) {
                org.springframework.core.env.EnumerablePropertySource<?> enumerablePropertySource
                        = (org.springframework.core.env.EnumerablePropertySource<?>) propertySource;
                        
                for (String propertyName : enumerablePropertySource.getPropertyNames()) {
                    environmentVariables.put(propertyName, environment.getProperty(propertyName));
                    logger.debug("Retrieved environment variable: {}={}", propertyName, environment.getProperty(propertyName));
                }
            } else {
                logger.debug("Property source '{}' is not enumerable.", propertySource.getName());
            }
        }

        // Add other relevant environment variables as needed
        if (environmentVariables.containsKey("MONGO_URI")) {
            logger.info("Successfully retrieved MONGO_URI from environment.");
        } else {
            logger.warn("MONGO_URI not found in the environment.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "OPTIONS"); // Indicate that OPTIONS method is allowed

        return new ResponseEntity<>(environmentVariables, headers, HttpStatus.OK);
    }
}
