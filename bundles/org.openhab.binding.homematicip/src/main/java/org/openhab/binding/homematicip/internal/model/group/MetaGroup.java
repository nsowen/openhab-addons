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

import org.openhab.binding.homematicip.internal.model.device.Device;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Group-specific implementation
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class MetaGroup extends Group {

    private Boolean lowBat;
    private Boolean sabotage;
    private boolean configPending;
    private boolean dutyCycle;
    // todo type private String incorrectPositioned;
    private List<String> groups = Collections.emptyList();
    private List<Group> resolvedGroups = Collections.emptyList();
    private String groupIcon;

    public MetaGroup() {
        System.out.println("new meta");
    }

    public Boolean getLowBat() {
        return lowBat;
    }

    public Boolean getSabotage() {
        return sabotage;
    }

    public boolean isConfigPending() {
        return configPending;
    }

    public boolean isDutyCycle() {
        return dutyCycle;
    }

    public List<String> getGroups() {
        return groups;
    }

    public List<Group> getResolvedGroups() {
        return resolvedGroups;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    @Override
    public void resolveMappings(Map<String, Device> devices, Map<String, Group> groups) {
        super.resolveMappings(devices, groups);
        this.resolvedGroups = groups.values().stream().map(g -> groups.get(g.getId())).collect(Collectors.toList());
    }
}
