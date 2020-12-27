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

import java.util.List;
import java.util.Map;

/**
 * Homematic IP heating cooling stuff
 *
 * @author Nils Sowen (n.sowen@2scale.net)
 * @since 2020-12-24
 */
public class HeatingCoolingProfile {

    private String id;
    private String homeId;
    private String groupId;
    private int index;
    private boolean visible;
    private boolean enabled;
    private String name;
    private String type;
    private Map<String,HeatingCoolingProfileDay> profileDays;

}
