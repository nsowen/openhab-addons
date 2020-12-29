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
import java.util.Map;
import java.util.StringJoiner;

import org.openhab.binding.homematicip.internal.model.HomematicIPObject;
import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannel;
import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannelType;
import org.openhab.binding.homematicip.internal.model.common.ConnectionType;
import org.openhab.binding.homematicip.internal.model.common.DeviceUpdateState;

/**
 * Abstract Homematic IP device
 *
 * @author Nils Sowen (n.sowen@2scale.net)
 * @since 2020-12-24
 */
public abstract class Device extends HomematicIPObject {

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
    protected String deviceType;
    protected String availableFirmwareVersion;
    protected boolean permanentlyReachable;
    protected Map<Integer, FunctionalChannel> functionalChannels;

    protected FunctionalChannelType baseChannel = FunctionalChannelType.DEVICE_BASE;

    @Override
    public String toString() {
        return new StringJoiner(", ", Device.class.getSimpleName() + "[", "]").add("id='" + id + "'")
                .add("homeId='" + homeId + "'").add("label='" + label + "'").add("lastStatusUpdate=" + lastStatusUpdate)
                .add("manufacturerCode=" + manufacturerCode).add("oem='" + oem + "'")
                .add("firmwareVersionInteger=" + firmwareVersionInteger).add("updateState=" + updateState)
                .add("firmwareVersion='" + firmwareVersion + "'").add("connectionType=" + connectionType)
                .add("modelId=" + modelId).add("modelType='" + modelType + "'")
                .add("serializedGlobalTradeItemNumber='" + serializedGlobalTradeItemNumber + "'")
                .add("deviceType='" + deviceType + "'")
                .add("availableFirmwareVersion='" + availableFirmwareVersion + "'")
                .add("permanentlyReachable=" + permanentlyReachable).add("functionalChannels=" + functionalChannels)
                .add("baseChannel=" + baseChannel).toString();
    }

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

    public String getDeviceType() {
        return deviceType;
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

    public FunctionalChannelType getBaseChannel() {
        return baseChannel;
    }

    public void setDeviceType(String typeString) {
        this.deviceType = typeString;
    }
}
