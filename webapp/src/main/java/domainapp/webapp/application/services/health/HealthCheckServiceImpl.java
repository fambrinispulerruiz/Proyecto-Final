package domainapp.webapp.application.services.health;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import domainapp.modules.simple.dom.so.vidrio.VidrioServices;

import org.apache.causeway.applib.services.health.Health;
import org.apache.causeway.applib.services.health.HealthCheckService;

@Service
@Named("domainapp.HealthCheckServiceImpl")
public class HealthCheckServiceImpl implements HealthCheckService {

    private final VidrioServices vidrios;

    @Inject
    public HealthCheckServiceImpl(VidrioServices vidrios) {
        this.vidrios = vidrios;
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
