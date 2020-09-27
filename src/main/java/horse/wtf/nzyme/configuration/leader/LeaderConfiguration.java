package horse.wtf.nzyme.configuration.leader;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.typesafe.config.Config;
import horse.wtf.nzyme.Role;
import horse.wtf.nzyme.alerts.Alert;
import horse.wtf.nzyme.alerts.service.callbacks.AlertCallback;
import horse.wtf.nzyme.configuration.*;
import horse.wtf.nzyme.notifications.uplinks.graylog.GraylogAddress;

import javax.annotation.Nullable;
import java.net.URI;
import java.nio.file.Path;


@AutoValue
public abstract class LeaderConfiguration {

    public abstract boolean versionchecksEnabled();
    public abstract boolean fetchOuis();

    public abstract Role role();

    public abstract String adminPasswordHash();

    public abstract String databasePath();

    public abstract String pythonExecutable();
    public abstract String pythonScriptDirectory();
    public abstract String pythonScriptPrefix();

    public abstract URI restListenUri();
    public abstract URI httpExternalUri();

    public abstract boolean useTls();

    @Nullable
    public abstract Path tlsCertificatePath();

    @Nullable
    public abstract Path tlsKeyPath();

    public abstract ImmutableList<Dot11MonitorDefinition> dot11Monitors();
    public abstract ImmutableList<Dot11NetworkDefinition> dot11Networks();
    public abstract ImmutableList<Dot11TrapDeviceDefinition> dot11TrapDevices();

    public abstract ImmutableList<Alert.TYPE_WIDE> dot11Alerts();
    public abstract int alertingTrainingPeriodSeconds();

    public abstract ImmutableList<GraylogAddress> graylogUplinks();

    public abstract ImmutableList<AlertCallback> alertCallbacks();

    @Nullable
    public abstract Config debugConfig();

    @Nullable
    public abstract TrackerDeviceConfiguration trackerDevice();

    public ImmutableList<String> ourSSIDs() {
        ImmutableList.Builder<String> ssids = new ImmutableList.Builder<>();
        dot11Networks().forEach(n -> ssids.add(n.ssid()));
        return ssids.build();
    }

    public static LeaderConfiguration create(boolean versionchecksEnabled, boolean fetchOuis, Role role, String adminPasswordHash, String databasePath, String pythonExecutable, String pythonScriptDirectory, String pythonScriptPrefix, URI restListenUri, URI httpExternalUri, boolean useTls, Path tlsCertificatePath, Path tlsKeyPath, ImmutableList<Dot11MonitorDefinition> dot11Monitors, ImmutableList<Dot11NetworkDefinition> dot11Networks, ImmutableList<Dot11TrapDeviceDefinition> dot11TrapDevices, ImmutableList<Alert.TYPE_WIDE> dot11Alerts, int alertingTrainingPeriodSeconds, ImmutableList<GraylogAddress> graylogUplinks, ImmutableList<AlertCallback> alertCallbacks, Config debugConfig, TrackerDeviceConfiguration trackerDevice) {
        return builder()
                .versionchecksEnabled(versionchecksEnabled)
                .fetchOuis(fetchOuis)
                .role(role)
                .adminPasswordHash(adminPasswordHash)
                .databasePath(databasePath)
                .pythonExecutable(pythonExecutable)
                .pythonScriptDirectory(pythonScriptDirectory)
                .pythonScriptPrefix(pythonScriptPrefix)
                .restListenUri(restListenUri)
                .httpExternalUri(httpExternalUri)
                .useTls(useTls)
                .tlsCertificatePath(tlsCertificatePath)
                .tlsKeyPath(tlsKeyPath)
                .dot11Monitors(dot11Monitors)
                .dot11Networks(dot11Networks)
                .dot11TrapDevices(dot11TrapDevices)
                .dot11Alerts(dot11Alerts)
                .alertingTrainingPeriodSeconds(alertingTrainingPeriodSeconds)
                .graylogUplinks(graylogUplinks)
                .alertCallbacks(alertCallbacks)
                .debugConfig(debugConfig)
                .trackerDevice(trackerDevice)
                .build();
    }

    @Nullable
    public Dot11NetworkDefinition findNetworkDefinition(String bssid, String ssid) {
        for (Dot11NetworkDefinition network : dot11Networks()) {
            if (network.allBSSIDAddresses().contains(bssid) && network.ssid().equals(ssid)) {
                return network;
            }
        }

        return null;
    }

    @Nullable
    public Dot11BSSIDDefinition findBSSIDDefinition(String bssid, String ssid) {
        Dot11NetworkDefinition networkDefinition = findNetworkDefinition(bssid, ssid);
        if (networkDefinition != null) {
            for (Dot11BSSIDDefinition dot11BSSIDDefinition : networkDefinition.bssids()) {
                if (dot11BSSIDDefinition.address().equals(bssid)) {
                    return dot11BSSIDDefinition;
                }
            }
        }

        return null;
    }

    public static Builder builder() {
        return new AutoValue_LeaderConfiguration.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder versionchecksEnabled(boolean versionchecksEnabled);

        public abstract Builder fetchOuis(boolean fetchOuis);

        public abstract Builder role(Role role);

        public abstract Builder adminPasswordHash(String adminPasswordHash);

        public abstract Builder databasePath(String databasePath);

        public abstract Builder pythonExecutable(String pythonExecutable);

        public abstract Builder pythonScriptDirectory(String pythonScriptDirectory);

        public abstract Builder pythonScriptPrefix(String pythonScriptPrefix);

        public abstract Builder restListenUri(URI restListenUri);

        public abstract Builder httpExternalUri(URI httpExternalUri);

        public abstract Builder useTls(boolean useTls);

        public abstract Builder tlsCertificatePath(Path tlsCertificatePath);

        public abstract Builder tlsKeyPath(Path tlsKeyPath);

        public abstract Builder dot11Monitors(ImmutableList<Dot11MonitorDefinition> dot11Monitors);

        public abstract Builder dot11Networks(ImmutableList<Dot11NetworkDefinition> dot11Networks);

        public abstract Builder dot11TrapDevices(ImmutableList<Dot11TrapDeviceDefinition> dot11TrapDevices);

        public abstract Builder dot11Alerts(ImmutableList<Alert.TYPE_WIDE> dot11Alerts);

        public abstract Builder alertingTrainingPeriodSeconds(int alertingTrainingPeriodSeconds);

        public abstract Builder graylogUplinks(ImmutableList<GraylogAddress> graylogUplinks);

        public abstract Builder alertCallbacks(ImmutableList<AlertCallback> alertCallbacks);

        public abstract Builder debugConfig(Config debugConfig);

        public abstract Builder trackerDevice(TrackerDeviceConfiguration trackerDevice);

        public abstract LeaderConfiguration build();
    }
}