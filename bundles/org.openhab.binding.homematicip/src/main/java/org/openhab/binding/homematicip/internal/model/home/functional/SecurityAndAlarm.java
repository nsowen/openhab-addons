package org.openhab.binding.homematicip.internal.model.home.functional;

import java.time.Instant;
import java.util.Map;
import java.util.StringJoiner;

import org.openhab.binding.homematicip.internal.model.common.SecuritySwitchingGroupType;
import org.openhab.binding.homematicip.internal.model.common.SecurityZoneActivationModeType;
import org.openhab.binding.homematicip.internal.model.common.SecurityZoneType;

/**
 * Security and alarm group
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class SecurityAndAlarm extends FunctionalHome {

    private Instant alarmEventTimestamp;
    private String alarmEventDeviceId;
    private String alarmEventTriggerId;
    // todo private String alarmEventDeviceChannel;
    // todo private String alarmSecurityJournalEntryType;
    private boolean alarmActive;
    private Instant safetyAlarmEventTimestamp;
    // todo private String safetyAlarmEventDeviceChannel;
    // todo private String safetyAlarmSecurityJournalEntryType;
    private boolean safetyAlarmActive;
    private Instant intrusionAlarmEventTimestamp;
    // todo private String intrusionAlarmEventDeviceChannel;
    // todo private String intrusionAlarmSecurityJournalEntryType;
    private boolean intrusionAlarmActive;
    private Map<SecurityZoneType, String> securityZones;
    private Map<SecuritySwitchingGroupType, String> securitySwitchingGroups;
    private float zoneActivationDelay;
    private boolean intrusionAlertThroughSmokeDetectors;
    private SecurityZoneActivationModeType securityZoneActivationMode;
    private boolean activationInProgress;

    public Instant getAlarmEventTimestamp() {
        return alarmEventTimestamp;
    }

    public void setAlarmEventTimestamp(Instant alarmEventTimestamp) {
        this.alarmEventTimestamp = alarmEventTimestamp;
    }

    public String getAlarmEventDeviceId() {
        return alarmEventDeviceId;
    }

    public void setAlarmEventDeviceId(String alarmEventDeviceId) {
        this.alarmEventDeviceId = alarmEventDeviceId;
    }

    public String getAlarmEventTriggerId() {
        return alarmEventTriggerId;
    }

    public void setAlarmEventTriggerId(String alarmEventTriggerId) {
        this.alarmEventTriggerId = alarmEventTriggerId;
    }

    public boolean isAlarmActive() {
        return alarmActive;
    }

    public void setAlarmActive(boolean alarmActive) {
        this.alarmActive = alarmActive;
    }

    public Instant getSafetyAlarmEventTimestamp() {
        return safetyAlarmEventTimestamp;
    }

    public void setSafetyAlarmEventTimestamp(Instant safetyAlarmEventTimestamp) {
        this.safetyAlarmEventTimestamp = safetyAlarmEventTimestamp;
    }

    public boolean isSafetyAlarmActive() {
        return safetyAlarmActive;
    }

    public void setSafetyAlarmActive(boolean safetyAlarmActive) {
        this.safetyAlarmActive = safetyAlarmActive;
    }

    public Instant getIntrusionAlarmEventTimestamp() {
        return intrusionAlarmEventTimestamp;
    }

    public void setIntrusionAlarmEventTimestamp(Instant intrusionAlarmEventTimestamp) {
        this.intrusionAlarmEventTimestamp = intrusionAlarmEventTimestamp;
    }

    public boolean isIntrusionAlarmActive() {
        return intrusionAlarmActive;
    }

    public void setIntrusionAlarmActive(boolean intrusionAlarmActive) {
        this.intrusionAlarmActive = intrusionAlarmActive;
    }

    public Map<SecurityZoneType, String> getSecurityZones() {
        return securityZones;
    }

    public void setSecurityZones(Map<SecurityZoneType, String> securityZones) {
        this.securityZones = securityZones;
    }

    public Map<SecuritySwitchingGroupType, String> getSecuritySwitchingGroups() {
        return securitySwitchingGroups;
    }

    public void setSecuritySwitchingGroups(Map<SecuritySwitchingGroupType, String> securitySwitchingGroups) {
        this.securitySwitchingGroups = securitySwitchingGroups;
    }

    public float getZoneActivationDelay() {
        return zoneActivationDelay;
    }

    public void setZoneActivationDelay(float zoneActivationDelay) {
        this.zoneActivationDelay = zoneActivationDelay;
    }

    public boolean isIntrusionAlertThroughSmokeDetectors() {
        return intrusionAlertThroughSmokeDetectors;
    }

    public void setIntrusionAlertThroughSmokeDetectors(boolean intrusionAlertThroughSmokeDetectors) {
        this.intrusionAlertThroughSmokeDetectors = intrusionAlertThroughSmokeDetectors;
    }

    public SecurityZoneActivationModeType getSecurityZoneActivationMode() {
        return securityZoneActivationMode;
    }

    public void setSecurityZoneActivationMode(SecurityZoneActivationModeType securityZoneActivationMode) {
        this.securityZoneActivationMode = securityZoneActivationMode;
    }

    public boolean isActivationInProgress() {
        return activationInProgress;
    }

    public void setActivationInProgress(boolean activationInProgress) {
        this.activationInProgress = activationInProgress;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SecurityAndAlarm.class.getSimpleName() + "[", "]")
                .add("alarmEventTimestamp=" + alarmEventTimestamp)
                .add("alarmEventDeviceId='" + alarmEventDeviceId + "'")
                .add("alarmEventTriggerId='" + alarmEventTriggerId + "'").add("alarmActive=" + alarmActive)
                .add("safetyAlarmEventTimestamp=" + safetyAlarmEventTimestamp)
                .add("safetyAlarmActive=" + safetyAlarmActive)
                .add("intrusionAlarmEventTimestamp=" + intrusionAlarmEventTimestamp)
                .add("intrusionAlarmActive=" + intrusionAlarmActive).add("securityZones=" + securityZones)
                .add("securitySwitchingGroups=" + securitySwitchingGroups)
                .add("zoneActivationDelay=" + zoneActivationDelay)
                .add("intrusionAlertThroughSmokeDetectors=" + intrusionAlertThroughSmokeDetectors)
                .add("securityZoneActivationMode=" + securityZoneActivationMode)
                .add("activationInProgress=" + activationInProgress).toString();
    }
}
