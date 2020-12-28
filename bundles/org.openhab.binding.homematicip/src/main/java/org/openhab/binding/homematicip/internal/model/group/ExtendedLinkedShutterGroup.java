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

import org.openhab.binding.homematicip.internal.model.common.GroupVisibility;
import org.openhab.binding.homematicip.internal.model.common.ShadingStateType;

/**
 * Group-specific implementation
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class ExtendedLinkedShutterGroup extends Group {

    protected boolean dutyCycle;
    protected boolean lowBat;
    protected double shutterLevel;
    protected boolean processing;
    protected double slatsLevel;
    protected double topSlatsLevel;
    protected double bottomSlatsLevel;
    protected double primaryShadingLevel;
    protected ShadingStateType primaryShadingStateType = ShadingStateType.NOT_EXISTENT;
    protected double secondaryShadingLevel;
    protected ShadingStateType secondaryShadingStateType = ShadingStateType.NOT_EXISTENT;
    protected GroupVisibility groupVisibility = GroupVisibility.INVISIBLE_GROUP_AND_CONTROL;
}
