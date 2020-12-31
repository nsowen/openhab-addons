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
package org.openhab.binding.homematicip.internal.model.device;

import java.io.IOException;
import java.util.StringJoiner;

import org.openhab.binding.homematicip.internal.model.channel.AccessControllerChannel;
import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannelType;

/**
 * Device-specific implementation
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class HomeControlAccessPoint extends Device<AccessControllerChannel> {

    public HomeControlAccessPoint() {
        this.baseFunctionalChannelType = FunctionalChannelType.ACCESS_CONTROLLER_CHANNEL;
    }

    public float getDutyCycleLevel() {
        return getBaseFunctionalChannel().map(AccessControllerChannel::getDutyCycleLevel).orElse(0.0f);
    }

    public int getAccessPointPriority() {
        return getBaseFunctionalChannel().map(AccessControllerChannel::getAccessPointPriority).orElse(0);
    }

    public float getSignalBrightness() {
        return getBaseFunctionalChannel().map(AccessControllerChannel::getSignalBrightness).orElse(0.0f);
    }

    public void setSignalBrightness(float brightness) throws IOException {
        getConnection().setSignalBrightness(this, brightness);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HomeControlAccessPoint.class.getSimpleName() + "[", "]")
                .add("id='" + getId() + "'").add("label='" + getLabel() + "'")
                .add("firmwareVersion='" + getFirmwareVersion() + "'").add("dutyCycleLevel=" + getDutyCycleLevel())
                .add("accessPointPriority=" + getAccessPointPriority()).add("signalBrightness=" + getSignalBrightness())
                .toString();
    }
}
