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
 * Homematic IP Valve State
 *
 * @author Nils Sowen (n.sowen@2scale.net)
 * @since 2020-12-24
 */
public enum ValveState {
    STATE_NOT_AVAILABLE,
    RUN_TO_START,
    WAIT_FOR_ADAPTION,
    ADAPTION_IN_PROGRESS,
    ADAPTION_DONE,
    TOO_TIGHT,
    ADJUSTMENT_TOO_BIG,
    ADJUSTMENT_TOO_SMALL,
    ERROR_POSITION
}
