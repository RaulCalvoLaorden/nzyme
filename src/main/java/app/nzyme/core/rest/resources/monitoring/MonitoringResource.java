package app.nzyme.core.rest.resources.monitoring;

import app.nzyme.plugin.RegistryCryptoException;
import app.nzyme.plugin.rest.security.RESTSecured;
import com.google.common.collect.Maps;
import app.nzyme.core.NzymeNode;
import app.nzyme.core.monitoring.exporters.prometheus.PrometheusRegistryKeys;
import app.nzyme.core.rest.responses.monitoring.MonitoringSummaryResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/api/system/monitoring")
@RESTSecured
@Produces(MediaType.APPLICATION_JSON)
public class MonitoringResource {

    @Inject
    private NzymeNode nzyme;

    private static final Logger LOG = LogManager.getLogger(MonitoringResource.class);

    @GET
    @Path("/summary")
    public Response summary() {
        boolean prometheusReportEnabled;
        try {
            prometheusReportEnabled = nzyme.getDatabaseCoreRegistry()
                    .getValue(PrometheusRegistryKeys.REST_REPORT_ENABLED.key())
                    .filter(Boolean::parseBoolean)
                    .isPresent()
                    && nzyme.getDatabaseCoreRegistry().getValue(PrometheusRegistryKeys.REST_REPORT_USERNAME.key())
                    .isPresent()
                    && nzyme.getDatabaseCoreRegistry().getEncryptedValue(PrometheusRegistryKeys.REST_REPORT_PASSWORD.key())
                    .isPresent();
        } catch(RegistryCryptoException e) {
            LOG.error("Could not decrypt encrypted registry value", e);
            return Response.serverError().build();
        }

        Map<String, Boolean> exporters = Maps.newHashMap();
        exporters.put("prometheus", prometheusReportEnabled);

        return Response.ok(MonitoringSummaryResponse.create(exporters)).build();
    }

}
