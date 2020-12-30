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
package org.openhab.binding.homematicip.internal.model.channel;

import java.util.List;
import java.util.StringJoiner;

import org.openhab.binding.homematicip.internal.model.HomematicIPObject;

/**
 * Functional-channel-specific implementation
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public abstract class FunctionalChannel extends HomematicIPObject {

    protected int index;
    protected int groupIndex;
    protected String label;
    protected FunctionalChannelType functionalChannelType;
    protected List<String> groups;

    public int getIndex() {
        return index;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public String getLabel() {
        return label;
    }

    public FunctionalChannelType getFunctionalChannelType() {
        return functionalChannelType;
    }

    public List<String> getGroups() {
        return groups;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FunctionalChannel.class.getSimpleName() + "[", "]")
                .add("index=" + index)
                .add("groupIndex=" + groupIndex)
                .add("label='" + label + "'")
                .add("functionalChannelType=" + functionalChannelType)
                .add("groups=" + groups)
                .toString();
    }
}
