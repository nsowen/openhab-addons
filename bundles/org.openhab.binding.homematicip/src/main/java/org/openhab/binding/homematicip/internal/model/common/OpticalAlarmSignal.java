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
package org.openhab.binding.homematicip.internal.model.common;

/**
 * Homematic IP Optical alarm types
 *
 * @author Nils Sowen (n.sowen@2scale.net)
 * @since 2020-12-24
 */
public enum OpticalAlarmSignal {
    DISABLE_OPTICAL_SIGNAL,
    BLINKING_ALTERNATELY_REPEATING,
    BLINKING_BOTH_REPEATING,
    DOUBLE_FLASHING_REPEATING,
    FLASHING_BOTH_REPEATING,
    CONFIRMATION_SIGNAL_0,
    CONFIRMATION_SIGNAL_1,
    CONFIRMATION_SIGNAL_2
}
