package it.fabiofenoglio.lelohub.web.rest;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for view and managing Log Level at runtime.
 */
@RestController
@RequestMapping ( "/api" )
public class StatusResource {

	private static final int INTERVAL_SECONDS = 60 * 3;
    
	private Environment env;

	private HealthEndpoint healthEndpoint;
	
	private HealthComponent lastSnapshot = null;

	private ZonedDateTime lastSnapshotTime = null;
	
    public StatusResource ( Environment env, HealthEndpoint healthEndpoint ) {
        super ();
        this.env = env;
        this.healthEndpoint = healthEndpoint;
    }

    @GetMapping ( "/status" )
    public Map<String, Object> getStatus () {
    	Map<String, Object> output = new HashMap<>();
    	HealthComponent healthReport = getHealth();

    	output.put("status", healthReport.getStatus().getCode());
    	output.put("message", healthReport.getStatus().getDescription());
    	output.put("profiles", env.getActiveProfiles());
    	output.put("timestamp", lastSnapshotTime);

    	if (healthReport instanceof Health) {
        	for (Entry<String, Object> detail: ((Health)healthReport).getDetails().entrySet()) {
        		output.put(detail.getKey(), detail.getValue());
        	}
    	}

    	return output;
    }

    private synchronized HealthComponent getHealth() {
    	ZonedDateTime now = ZonedDateTime.now();
    	if (lastSnapshot == null || lastSnapshotTime == null 
    			|| lastSnapshotTime.until(now, ChronoUnit.SECONDS) > INTERVAL_SECONDS) {
    		lastSnapshot = healthEndpoint.health();
    		lastSnapshotTime = now;
    	}
    	return lastSnapshot;
    }
}
