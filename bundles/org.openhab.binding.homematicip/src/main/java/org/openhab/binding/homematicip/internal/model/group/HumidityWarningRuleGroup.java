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
package org.openhab.binding.homematicip.internal.model.group;

import java.time.Instant;

import org.openhab.binding.homematicip.internal.model.common.HumidityValidationType;

/**
 * Group-specific implementation
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class HumidityWarningRuleGroup extends Group {

    protected boolean enabled;
    protected HumidityValidationType humidityValidationResult = HumidityValidationType.GREATER_LOWER_LESSER_UPPER_THRESHOLD;
    protected int humidityLowerThreshold = 0;
    protected int humidityUpperThreshold = 0;
    protected boolean triggered;
    protected boolean ventilationRecommended;
    protected Instant lastExecutionTimestamp;
    protected Instant lastStatusUpdate;
    // protected Object outdoorClimateSensor; // device
}
