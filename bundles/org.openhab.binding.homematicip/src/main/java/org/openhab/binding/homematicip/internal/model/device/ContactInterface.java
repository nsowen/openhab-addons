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

import java.util.Optional;
import java.util.StringJoiner;

import org.openhab.binding.homematicip.internal.model.channel.ContactInterfaceChannel;
import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannelType;
import org.openhab.binding.homematicip.internal.model.common.WindowState;

/**
 * Device-specific implementation
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class ContactInterface extends Device<ContactInterfaceChannel> {

    public WindowState getWindowState() {
        return getChannel().map(ContactInterfaceChannel::getWindowState).orElse(WindowState.CLOSED);
    }

    public long getEventDelay() {
        return getChannel().map(ContactInterfaceChannel::getEventDelay).orElse(0L);
    }

    private Optional<ContactInterfaceChannel> getChannel() {
        return getFunctionalChannel(FunctionalChannelType.CONTACT_INTERFACE_CHANNEL, ContactInterfaceChannel.class);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ContactInterface.class.getSimpleName() + "[", "]")
                .add("windowState=" + getWindowState()).add("eventDelay=" + getEventDelay()).toString();
    }
}
