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

import java.util.Optional;
import java.util.StringJoiner;

import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannelType;
import org.openhab.binding.homematicip.internal.model.channel.ShutterContactChannel;
import org.openhab.binding.homematicip.internal.model.common.WindowState;

/**
 * HMIP-SWDO (Door / Window Contact - optical) / HMIP-SWDO-I (Door / Window Contact Invisible - optical)
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class ShutterContact extends AbstractSabotageDevice {

    public WindowState getWindowState() {
        return getChannel().map(ShutterContactChannel::getWindowState).orElse(WindowState.CLOSED);
    }

    public long getEventDelay() {
        return getChannel().map(ShutterContactChannel::getEventDelay).orElse(0L);
    }

    private Optional<ShutterContactChannel> getChannel() {
        return (Optional<ShutterContactChannel>) getFunctionalChannel(FunctionalChannelType.SHUTTER_CONTACT_CHANNEL);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ShutterContact.class.getSimpleName() + "[", "]")
                .add("windowState=" + getWindowState()).add("eventDelay=" + getEventDelay()).toString();
    }
}
