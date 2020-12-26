/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
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
package org.openhab.binding.homematicip.internal.model.device;

import org.openhab.binding.homematicip.internal.model.ConnectionType;
import org.openhab.binding.homematicip.internal.model.DeviceUpdateState;
import org.openhab.binding.homematicip.internal.model.FunctionalChannel;
import org.openhab.binding.homematicip.internal.model.LiveUpdateState;

import java.util.Set;

/**
 * Abstract Homematic IP device
 *
 * @author Nils Sowen (n.sowen@2scale.net)
 * @since 2020-12-24
 */
public abstract class Device {

    protected String id;
    protected String homeId;
    protected String label;
    protected ConnectionType connectionType = ConnectionType.HMIP_LAN;
    protected String lastStatusUpdate;
    protected String deviceType;
    protected DeviceUpdateState updateState = DeviceUpdateState.UP_TO_DATE;
    protected String firmwareVersion;
    protected int firmwareVersionInteger;
    protected String availableFirmwareVersion;
    protected String modelType;
    protected int modelId;
    protected String oem;
    protected int manufacturerCode;
    protected String serializedGlobalTradeItemNumber;

    // FunctionalChannel "base": available on most devices
    protected FunctionalChannel baseChannel = FunctionalChannel.DEVICE_BASE;
    protected boolean unreach;
    protected float rssiDeviceValue;
    protected float rssiPeerValue;
    protected boolean lowBat;
    protected boolean routerModuleSupported;
    protected boolean routerModuleEnabled;
    protected boolean dutyCycle;
    protected boolean configPending;
    protected boolean permanentlyReachable;
    protected LiveUpdateState liveUpdateState = LiveUpdateState.LIVE_UPDATE_NOT_SUPPORTED;
    protected Set<String> functionalChannels;
    protected int functionalChannelCount;
    protected boolean deviceOverheated;
    protected boolean deviceOverloaded;
    protected boolean temperatureOutOfRange;
    protected boolean coProFaulty;
    protected boolean coProRestartNeeded;
    protected boolean coProUpdateFailure;
    protected boolean busConfigMismatch;
    protected boolean shortCircuitDataLine;
    protected boolean powerShortCircuit;
    protected boolean deviceUndervoltage;
    protected boolean devicePowerFailureDetected;
    protected boolean deviceIdentifySupported;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public String getLastStatusUpdate() {
        return lastStatusUpdate;
    }

    public void setLastStatusUpdate(String lastStatusUpdate) {
        this.lastStatusUpdate = lastStatusUpdate;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public DeviceUpdateState getUpdateState() {
        return updateState;
    }

    public void setUpdateState(DeviceUpdateState updateState) {
        this.updateState = updateState;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public int getFirmwareVersionInteger() {
        return firmwareVersionInteger;
    }

    public void setFirmwareVersionInteger(int firmwareVersionInteger) {
        this.firmwareVersionInteger = firmwareVersionInteger;
    }

    public String getAvailableFirmwareVersion() {
        return availableFirmwareVersion;
    }

    public void setAvailableFirmwareVersion(String availableFirmwareVersion) {
        this.availableFirmwareVersion = availableFirmwareVersion;
    }

    public boolean isUnreach() {
        return unreach;
    }

    public void setUnreach(boolean unreach) {
        this.unreach = unreach;
    }

    public boolean isLowBat() {
        return lowBat;
    }

    public void setLowBat(boolean lowBat) {
        this.lowBat = lowBat;
    }

    public boolean isRouterModuleSupported() {
        return routerModuleSupported;
    }

    public void setRouterModuleSupported(boolean routerModuleSupported) {
        this.routerModuleSupported = routerModuleSupported;
    }

    public boolean isRouterModuleEnabled() {
        return routerModuleEnabled;
    }

    public void setRouterModuleEnabled(boolean routerModuleEnabled) {
        this.routerModuleEnabled = routerModuleEnabled;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getOem() {
        return oem;
    }

    public void setOem(String oem) {
        this.oem = oem;
    }

    public int getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(int manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getSerializedGlobalTradeItemNumber() {
        return serializedGlobalTradeItemNumber;
    }

    public void setSerializedGlobalTradeItemNumber(String serializedGlobalTradeItemNumber) {
        this.serializedGlobalTradeItemNumber = serializedGlobalTradeItemNumber;
    }

    public float getRssiDeviceValue() {
        return rssiDeviceValue;
    }

    public void setRssiDeviceValue(float rssiDeviceValue) {
        this.rssiDeviceValue = rssiDeviceValue;
    }

    public float getRssiPeerValue() {
        return rssiPeerValue;
    }

    public void setRssiPeerValue(float rssiPeerValue) {
        this.rssiPeerValue = rssiPeerValue;
    }

    public boolean isDutyCycle() {
        return dutyCycle;
    }

    public void setDutyCycle(boolean dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    public boolean isConfigPending() {
        return configPending;
    }

    public void setConfigPending(boolean configPending) {
        this.configPending = configPending;
    }

    public boolean isPermanentlyReachable() {
        return permanentlyReachable;
    }

    public void setPermanentlyReachable(boolean permanentlyReachable) {
        this.permanentlyReachable = permanentlyReachable;
    }

    public LiveUpdateState getLiveUpdateState() {
        return liveUpdateState;
    }

    public void setLiveUpdateState(LiveUpdateState liveUpdateState) {
        this.liveUpdateState = liveUpdateState;
    }

    public Set<String> getFunctionalChannels() {
        return functionalChannels;
    }

    public void setFunctionalChannels(Set<String> functionalChannels) {
        this.functionalChannels = functionalChannels;
    }

    public int getFunctionalChannelCount() {
        return functionalChannelCount;
    }

    public void setFunctionalChannelCount(int functionalChannelCount) {
        this.functionalChannelCount = functionalChannelCount;
    }

    public boolean isDeviceOverheated() {
        return deviceOverheated;
    }

    public void setDeviceOverheated(boolean deviceOverheated) {
        this.deviceOverheated = deviceOverheated;
    }

    public boolean isDeviceOverloaded() {
        return deviceOverloaded;
    }

    public void setDeviceOverloaded(boolean deviceOverloaded) {
        this.deviceOverloaded = deviceOverloaded;
    }

    public boolean isTemperatureOutOfRange() {
        return temperatureOutOfRange;
    }

    public void setTemperatureOutOfRange(boolean temperatureOutOfRange) {
        this.temperatureOutOfRange = temperatureOutOfRange;
    }

    public boolean isCoProFaulty() {
        return coProFaulty;
    }

    public void setCoProFaulty(boolean coProFaulty) {
        this.coProFaulty = coProFaulty;
    }

    public boolean isCoProRestartNeeded() {
        return coProRestartNeeded;
    }

    public void setCoProRestartNeeded(boolean coProRestartNeeded) {
        this.coProRestartNeeded = coProRestartNeeded;
    }

    public boolean isCoProUpdateFailure() {
        return coProUpdateFailure;
    }

    public void setCoProUpdateFailure(boolean coProUpdateFailure) {
        this.coProUpdateFailure = coProUpdateFailure;
    }

    public boolean isBusConfigMismatch() {
        return busConfigMismatch;
    }

    public void setBusConfigMismatch(boolean busConfigMismatch) {
        this.busConfigMismatch = busConfigMismatch;
    }

    public boolean isShortCircuitDataLine() {
        return shortCircuitDataLine;
    }

    public void setShortCircuitDataLine(boolean shortCircuitDataLine) {
        this.shortCircuitDataLine = shortCircuitDataLine;
    }

    public boolean isPowerShortCircuit() {
        return powerShortCircuit;
    }

    public void setPowerShortCircuit(boolean powerShortCircuit) {
        this.powerShortCircuit = powerShortCircuit;
    }

    public boolean isDeviceUndervoltage() {
        return deviceUndervoltage;
    }

    public void setDeviceUndervoltage(boolean deviceUndervoltage) {
        this.deviceUndervoltage = deviceUndervoltage;
    }

    public boolean isDevicePowerFailureDetected() {
        return devicePowerFailureDetected;
    }

    public void setDevicePowerFailureDetected(boolean devicePowerFailureDetected) {
        this.devicePowerFailureDetected = devicePowerFailureDetected;
    }

    public boolean isDeviceIdentifySupported() {
        return deviceIdentifySupported;
    }

    public void setDeviceIdentifySupported(boolean deviceIdentifySupported) {
        this.deviceIdentifySupported = deviceIdentifySupported;
    }
}
