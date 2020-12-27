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

import org.openhab.binding.homematicip.internal.model.channel.Channel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

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
    protected List<Channel> channels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getMetaGroupId() {
        return metaGroupId;
    }

    public void setMetaGroupId(String metaGroupId) {
        this.metaGroupId = metaGroupId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUnreach() {
        return unreach;
    }

    public void setUnreach(String unreach) {
        this.unreach = unreach;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
