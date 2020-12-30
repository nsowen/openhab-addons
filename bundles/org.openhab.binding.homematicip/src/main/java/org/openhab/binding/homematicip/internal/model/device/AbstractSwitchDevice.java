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

import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannelType;
import org.openhab.binding.homematicip.internal.model.channel.SwitchChannel;
import org.openhab.binding.homematicip.internal.model.common.ProfileMode;

import java.util.StringJoiner;

/**
 * Device that can switch on / off
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public abstract class AbstractSwitchDevice extends Device<SwitchChannel> {

    public AbstractSwitchDevice() {
        this.baseFunctionalChannelType = FunctionalChannelType.SWITCH_CHANNEL;
    }

    public boolean isOn() {
        return getBaseFunctionalChannel().map(SwitchChannel::isOn).orElse(false);
    }

    public ProfileMode getProfileMode() {
        return getBaseFunctionalChannel().map(SwitchChannel::getProfileMode).orElse(ProfileMode.AUTOMATIC);
    }

    public ProfileMode getUserDesiredProfileMode() {
        return getBaseFunctionalChannel().map(SwitchChannel::getUserDesiredProfileMode).orElse(ProfileMode.AUTOMATIC);
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", AbstractSwitchDevice.class.getSimpleName() + "[", "]")
                .add("on=" + isOn())
                .add("profileMode=" + getProfileMode())
                .add("userDesiredProfileMode=" + getUserDesiredProfileMode())
                .add("id='" + getId() + "'")
                .add("homeId='" + getHomeId() + "'")
                .add("label='" + getLabel() + "'")
                .add("firmwareVersion='" + getFirmwareVersion() + "'")
                .toString();
    }
}
