/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.homematicip.internal.model.home;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.openhab.binding.homematicip.internal.model.HomematicIPObject;
import org.openhab.binding.homematicip.internal.model.HomematicIPThing;
import org.openhab.binding.homematicip.internal.model.common.ApExchangeState;
import org.openhab.binding.homematicip.internal.model.common.DeviceUpdateStrategy;
import org.openhab.binding.homematicip.internal.model.common.FunctionalHomeType;
import org.openhab.binding.homematicip.internal.model.common.HomeUpdateState;
import org.openhab.binding.homematicip.internal.model.home.functional.FunctionalHome;

/**
 * Represents the overall home state as returned from Homematic IP API
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class Home extends HomematicIPObject implements HomematicIPThing {

    private String id;
    private Weather weather;
    private List<String> metaGroups;
    private List<String> clients;
    private boolean connected;
    private String currentAPVersion;
    private String availableAPVersion;
    private String timeZoneId;
    private Location location;
    private boolean pinAssigned;
    private boolean isLiveUpdateSupported = true;
    private float dutyCycle;
    private float carrierSense;
    private HomeUpdateState updateState;
    private float powerMeterUnitPrice;
    private String powerMeterCurrency;
    private DeviceUpdateStrategy deviceUpdateStrategy;
    private long lastReadyForUpdateTimestamp;
    private Map<FunctionalHomeType, FunctionalHome> functionalHomes;
    private String inboxGroup;
    private String apExchangeClientId;
    private ApExchangeState apExchangeState;
    private VoiceControlSettings voiceControlSettings;
    private List<String> ruleGroups;
    // todo private Map<?,String> ruleMetaDatas;
    private LiveOTAUStatus liveOTAUStatus;
    private Map<String, AccessPointStatusUpdate> accessPointUpdateStates;
    private boolean liveUpdateSupported;

    @Override
    public String toString() {
        return new StringJoiner(", ", Home.class.getSimpleName() + "[", "]").add("weather=" + weather)
                .add("connected=" + connected).add("currentAPVersion='" + currentAPVersion + "'")
                .add("availableAPVersion='" + availableAPVersion + "'").add("timeZoneId='" + timeZoneId + "'")
                .add("location=" + location).add("pinAssigned=" + pinAssigned)
                .add("isLiveUpdateSupported=" + isLiveUpdateSupported).add("dutyCycle=" + dutyCycle)
                .add("carrierSense=" + carrierSense).add("updateState=" + updateState)
                .add("powerMeterUnitPrice=" + powerMeterUnitPrice)
                .add("powerMeterCurrency='" + powerMeterCurrency + "'")
                .add("deviceUpdateStrategy=" + deviceUpdateStrategy)
                .add("lastReadyForUpdateTimestamp=" + lastReadyForUpdateTimestamp)
                .add("functionalHomes=" + functionalHomes).toString();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getHomeId() {
        return id;
    }

    @Override
    public String getLabel() {
        return "home";
    }

    @Override
    public String getType() {
        return "home";
    }

    public Weather getWeather() {
        return weather;
    }

    public List<String> getMetaGroups() {
        return metaGroups;
    }

    public List<String> getClients() {
        return clients;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getCurrentAPVersion() {
        return currentAPVersion;
    }

    public String getAvailableAPVersion() {
        return availableAPVersion;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isPinAssigned() {
        return pinAssigned;
    }

    public boolean isLiveUpdateSupported() {
        return isLiveUpdateSupported;
    }

    public float getDutyCycle() {
        return dutyCycle;
    }

    public float getCarrierSense() {
        return carrierSense;
    }

    public HomeUpdateState getUpdateState() {
        return updateState;
    }

    public float getPowerMeterUnitPrice() {
        return powerMeterUnitPrice;
    }

    public String getPowerMeterCurrency() {
        return powerMeterCurrency;
    }

    public DeviceUpdateStrategy getDeviceUpdateStrategy() {
        return deviceUpdateStrategy;
    }

    public long getLastReadyForUpdateTimestamp() {
        return lastReadyForUpdateTimestamp;
    }

    public Map<FunctionalHomeType, FunctionalHome> getFunctionalHomes() {
        return functionalHomes;
    }

    public String getInboxGroup() {
        return inboxGroup;
    }

    public String getApExchangeClientId() {
        return apExchangeClientId;
    }

    public ApExchangeState getApExchangeState() {
        return apExchangeState;
    }

    public VoiceControlSettings getVoiceControlSettings() {
        return voiceControlSettings;
    }

    public List<String> getRuleGroups() {
        return ruleGroups;
    }

    public LiveOTAUStatus getLiveOTAUStatus() {
        return liveOTAUStatus;
    }

    public Map<String, AccessPointStatusUpdate> getAccessPointUpdateStates() {
        return accessPointUpdateStates;
    }
}
