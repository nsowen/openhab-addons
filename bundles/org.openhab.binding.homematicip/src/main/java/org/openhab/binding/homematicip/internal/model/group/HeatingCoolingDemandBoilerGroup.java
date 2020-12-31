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

/**
 * Group-specific implementation
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class HeatingCoolingDemandBoilerGroup extends Group {

    private long boilerFollowUpTime;
    private long boilerLeadTime;
    private boolean on;
    private float dimLevel;

    public long getBoilerFollowUpTime() {
        return boilerFollowUpTime;
    }

    public long getBoilerLeadTime() {
        return boilerLeadTime;
    }

    public boolean isOn() {
        return on;
    }

    public float getDimLevel() {
        return dimLevel;
    }
}
