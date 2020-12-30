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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.openhab.binding.homematicip.internal.model.channel.Channel;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.response.GetCurrentStateResponse;

/**
 * New class.
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public abstract class Group {

    protected String id;
    protected String homeId;
    protected String metaGroupId;
    protected String label;
    protected Instant lastStatusUpdate;
    protected String unreach;
    protected String type;
    protected List<Channel> channels = Collections.emptyList();
    protected List<Device> devices = Collections.emptyList();

    public String getId() {
        return id;
    }

    public String getHomeId() {
        return homeId;
    }

    public String getMetaGroupId() {
        return metaGroupId;
    }

    public String getLabel() {
        return label;
    }

    public Instant getLastStatusUpdate() {
        return lastStatusUpdate;
    }

    public String getUnreach() {
        return unreach;
    }

    public String getType() {
        return type;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public List<Device> getResolvedDevices() {
        return devices;
    }

    public void resolveMappings(Map<String, Device> devices, Map<String, Group> groups) {
        this.devices = getChannels().stream().map(channel -> devices.get(channel.getDeviceId())).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Group.class.getSimpleName() + "[", "]").add("id='" + id + "'")
                .add("homeId='" + homeId + "'").add("metaGroupId='" + metaGroupId + "'").add("label='" + label + "'")
                .add("lastStatusUpdate=" + lastStatusUpdate).add("unreach='" + unreach + "'").add("type='" + type + "'")
                .add("channels=" + channels).toString();
    }


}
