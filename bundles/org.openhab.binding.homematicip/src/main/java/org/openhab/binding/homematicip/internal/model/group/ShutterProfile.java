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

import org.openhab.binding.homematicip.internal.model.common.ProfileMode;
import org.openhab.binding.homematicip.internal.model.common.ShadingStateType;

/**
 * Group-specific implementation
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class ShutterProfile extends Group {

    protected boolean unreach;
    protected boolean dutyCycle;
    protected boolean lowBat;
    protected boolean processing;
    protected double shutterLevel;
    protected double slatsLevel;
    protected double primaryShadingLevel;
    protected ShadingStateType primaryShadingStateType = ShadingStateType.NOT_EXISTENT;
    protected double secondaryShadingLevel;
    protected ShadingStateType secondaryShadingStateType = ShadingStateType.NOT_EXISTENT;
    protected ProfileMode profileMode = ProfileMode.MANUAL;

}
