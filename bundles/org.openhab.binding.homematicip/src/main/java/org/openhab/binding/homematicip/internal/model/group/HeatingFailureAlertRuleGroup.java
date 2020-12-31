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
package org.openhab.binding.homematicip.internal.model.group;

import java.time.Instant;

import org.openhab.binding.homematicip.internal.model.common.HeatingFailueValidationType;

/**
 * Group-specific implementation
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class HeatingFailureAlertRuleGroup extends Group {

    private boolean enabled;
    private HeatingFailueValidationType heatingFailureValidationResult;
    private long checkInterval;
    private long validationTimeout;
    private Instant lastExecutionTimestamp;

    public boolean isEnabled() {
        return enabled;
    }

    public HeatingFailueValidationType getHeatingFailureValidationResult() {
        return heatingFailureValidationResult;
    }

    public long getCheckInterval() {
        return checkInterval;
    }

    public long getValidationTimeout() {
        return validationTimeout;
    }

    public Instant getLastExecutionTimestamp() {
        return lastExecutionTimestamp;
    }
}
