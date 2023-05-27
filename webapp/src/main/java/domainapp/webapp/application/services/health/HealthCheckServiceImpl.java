package domainapp.webapp.application.services.health;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.health.Health;
import org.apache.causeway.applib.services.health.HealthCheckService;

import domainapp.modules.simple.dom.so.Vidrios;

@Service
@Named("domainapp.HealthCheckServiceImpl")
public class HealthCheckServiceImpl implements HealthCheckService {

    private final Vidrios simpleObjects;

    @Inject
    public HealthCheckServiceImpl(Vidrios simpleObjects) {
        this.simpleObjects = simpleObjects;
    }

    @Override
    public Health check() {
        try {
//            simpleObjects.ping();
            return Health.ok();
        } catch (Exception ex) {
            return Health.error(ex);
        }
    }
}
