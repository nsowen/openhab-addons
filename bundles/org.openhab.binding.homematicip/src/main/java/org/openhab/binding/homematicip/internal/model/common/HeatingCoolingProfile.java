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
package org.openhab.binding.homematicip.internal.model.common;

import java.util.Map;
import java.util.StringJoiner;

import org.openhab.binding.homematicip.internal.model.HomematicIPObject;

/**
 * Homematic IP heating cooling stuff
 *
 * @author Nils Sowen (n.sowen@2scale.net)
 * @since 2020-12-24
 */
public class HeatingCoolingProfile extends HomematicIPObject {

    private String id;
    private String homeId;
    private String groupId;
    private int index;
    private boolean visible;
    private boolean enabled;
    private String name;
    private String type;
    private Map<String, HeatingCoolingProfileDay> profileDays;

    public String getId() {
        return id;
    }

    public String getHomeId() {
        return homeId;
    }

    public String getGroupId() {
        return groupId;
    }

    public int getIndex() {
        return index;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Map<String, HeatingCoolingProfileDay> getProfileDays() {
        return profileDays;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HeatingCoolingProfile.class.getSimpleName() + "[", "]").add("id='" + id + "'")
                .add("homeId='" + homeId + "'").add("groupId='" + groupId + "'").add("index=" + index)
                .add("visible=" + visible).add("enabled=" + enabled).add("name='" + name + "'")
                .add("type='" + type + "'").add("profileDays=" + profileDays).toString();
    }
}
