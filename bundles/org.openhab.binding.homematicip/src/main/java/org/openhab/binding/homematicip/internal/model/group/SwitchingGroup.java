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

import org.openhab.binding.homematicip.internal.model.common.ShadingStateType;

/**
 * Group-specific implementation
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class SwitchingGroup extends SwitchGroupBase {

    private boolean processing;
    private double shutterLevel;
    private double slatsLevel;
    private double primaryShadingLevel;
    private ShadingStateType primaryShadingStateType = ShadingStateType.NOT_EXISTENT;
    private double secondaryShadingLevel;
    private ShadingStateType secondaryShadingStateType = ShadingStateType.NOT_EXISTENT;

    public boolean isProcessing() {
        return processing;
    }

    public double getShutterLevel() {
        return shutterLevel;
    }

    public double getSlatsLevel() {
        return slatsLevel;
    }

    public double getPrimaryShadingLevel() {
        return primaryShadingLevel;
    }

    public ShadingStateType getPrimaryShadingStateType() {
        return primaryShadingStateType;
    }

    public double getSecondaryShadingLevel() {
        return secondaryShadingLevel;
    }

    public ShadingStateType getSecondaryShadingStateType() {
        return secondaryShadingStateType;
    }
}
