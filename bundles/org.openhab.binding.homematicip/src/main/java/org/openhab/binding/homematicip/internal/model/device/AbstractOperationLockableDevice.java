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

import org.openhab.binding.homematicip.internal.model.channel.DeviceOperationLockChannel;
import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannelType;

/**
 * Device that can be locked for operation
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public abstract class AbstractOperationLockableDevice extends Device<DeviceOperationLockChannel> {

    public AbstractOperationLockableDevice() {
        this.baseFunctionalChannelType = FunctionalChannelType.DEVICE_OPERATIONLOCK;
    }

    public boolean isOperationaLockActive() {
        return getBaseFunctionalChannel().map(DeviceOperationLockChannel::isOperationLockActive).orElse(false);
    }
}
