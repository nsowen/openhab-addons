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
 * Homematic IP acc sensor
 *
 * @author Nils Sowen (n.sowen@2scale.net)
 * @since 2020-12-24
 */
public enum AccelerationSensorSensitivity {
    SENSOR_RANGE_16G,
    SENSOR_RANGE_8G,
    SENSOR_RANGE_4G,
    SENSOR_RANGE_2G,
    SENSOR_RANGE_2G_PLUS_SENS,
    SENSOR_RANGE_2G_2PLUS_SENSE
}
