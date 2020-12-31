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

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.openhab.binding.homematicip.internal.model.HomematicIPObject;
import org.openhab.binding.homematicip.internal.model.HomematicIPThing;
import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannel;
import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannelType;
import org.openhab.binding.homematicip.internal.model.common.ConnectionType;
import org.openhab.binding.homematicip.internal.model.common.DeviceUpdateState;
import org.openhab.binding.homematicip.internal.model.group.Group;

/**
 * Abstract Homematic IP device
 *
 * @param <T> FunctionalChannel implementation that defines the base channel
 * @author Nils Sowen (n.sowen@2scale.net)
 * @since 2020-12-24
 */
public abstract class Device<T extends FunctionalChannel> extends HomematicIPObject implements HomematicIPThing {

    protected String id;
    protected String homeId;
    protected String label;
    protected Instant lastStatusUpdate;
    protected int manufacturerCode;
    protected String oem;
    protected int firmwareVersionInteger;
    protected DeviceUpdateState updateState = DeviceUpdateState.UP_TO_DATE;
    protected String firmwareVersion;
    protected ConnectionType connectionType = ConnectionType.HMIP_LAN;
    protected int modelId;
    protected String modelType;
    protected String serializedGlobalTradeItemNumber;
    protected String type;
    protected String availableFirmwareVersion;
    protected boolean permanentlyReachable;

    protected Map<Integer, FunctionalChannel> functionalChannels = new HashMap<>();
    protected Map<FunctionalChannelType, FunctionalChannel> functionalChannelMap = new ConcurrentHashMap<>();

    protected FunctionalChannelType baseFunctionalChannelType = FunctionalChannelType.DEVICE_BASE;

    public String getId() {
        return id;
    }

    public String getHomeId() {
        return homeId;
    }

    public String getLabel() {
        return label;
    }

    public Instant getLastStatusUpdate() {
        return lastStatusUpdate;
    }

    public int getManufacturerCode() {
        return manufacturerCode;
    }

    public String getOem() {
        return oem;
    }

    public int getFirmwareVersionInteger() {
        return firmwareVersionInteger;
    }

    public DeviceUpdateState getUpdateState() {
        return updateState;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public int getModelId() {
        return modelId;
    }

    public String getModelType() {
        return modelType;
    }

    public String getSerializedGlobalTradeItemNumber() {
        return serializedGlobalTradeItemNumber;
    }

    public String getType() {
        return type;
    }

    public String getAvailableFirmwareVersion() {
        return availableFirmwareVersion;
    }

    public boolean isPermanentlyReachable() {
        return permanentlyReachable;
    }

    public Map<Integer, FunctionalChannel> getFunctionalChannels() {
        return functionalChannels;
    }

    public Optional<T> getBaseFunctionalChannel() {
        return (Optional<T>) getFunctionalChannel(baseFunctionalChannelType);
    }

    public Optional<? extends FunctionalChannel> getFunctionalChannel(FunctionalChannelType type) {
        var channel = functionalChannelMap.get(type);
        if (channel != null) {
            return Optional.of(channel);
        }
        var _channel = functionalChannels.values().stream().filter(fc -> fc.getFunctionalChannelType() == type)
                .findFirst();
        _channel.ifPresent(c -> functionalChannelMap.put(type, c));
        return _channel;
    }

    public void setType(String typeString) {
        this.type = typeString;
    }

    public void resolveMappings(Map<String, Device> devices, Map<String, Group> groups) {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLastStatusUpdate(Instant lastStatusUpdate) {
        this.lastStatusUpdate = lastStatusUpdate;
    }

    public void setManufacturerCode(int manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public void setOem(String oem) {
        this.oem = oem;
    }

    public void setFirmwareVersionInteger(int firmwareVersionInteger) {
        this.firmwareVersionInteger = firmwareVersionInteger;
    }

    public void setUpdateState(DeviceUpdateState updateState) {
        this.updateState = updateState;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public void setSerializedGlobalTradeItemNumber(String serializedGlobalTradeItemNumber) {
        this.serializedGlobalTradeItemNumber = serializedGlobalTradeItemNumber;
    }

    public void setAvailableFirmwareVersion(String availableFirmwareVersion) {
        this.availableFirmwareVersion = availableFirmwareVersion;
    }

    public void setPermanentlyReachable(boolean permanentlyReachable) {
        this.permanentlyReachable = permanentlyReachable;
    }

    public void setFunctionalChannels(Map<Integer, FunctionalChannel> functionalChannels) {
        this.functionalChannels = functionalChannels;
    }

    public void setFunctionalChannelMap(Map<FunctionalChannelType, FunctionalChannel> functionalChannelMap) {
        this.functionalChannelMap = functionalChannelMap;
    }

    public void setBaseFunctionalChannelType(FunctionalChannelType baseFunctionalChannelType) {
        this.baseFunctionalChannelType = baseFunctionalChannelType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Device.class.getSimpleName() + "[", "]").add("id='" + id + "'")
                .add("homeId='" + homeId + "'").add("label='" + label + "'").add("lastStatusUpdate=" + lastStatusUpdate)
                .add("manufacturerCode=" + manufacturerCode).add("oem='" + oem + "'")
                .add("firmwareVersionInteger=" + firmwareVersionInteger).add("updateState=" + updateState)
                .add("firmwareVersion='" + firmwareVersion + "'").add("connectionType=" + connectionType)
                .add("modelId=" + modelId).add("modelType='" + modelType + "'")
                .add("serializedGlobalTradeItemNumber='" + serializedGlobalTradeItemNumber + "'")
                .add("deviceType='" + type + "'").add("availableFirmwareVersion='" + availableFirmwareVersion + "'")
                .add("permanentlyReachable=" + permanentlyReachable).add("functionalChannels=" + functionalChannels)
                .add("baseChannel=" + baseFunctionalChannelType).toString();
    }
}
