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
package org.openhab.binding.homematicip.internal.model.home.functional;

import java.util.List;
import java.util.StringJoiner;

/**
 * Light and shadow functional home
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class LightAndShadow extends FunctionalHome {

    private List<String> extendedLinkedShutterGroups;
    private List<String> extendedLinkedSwitchingGroups;
    private List<String> shutterProfileGroups;
    private List<String> switchingProfileGroups;

    public List<String> getExtendedLinkedShutterGroups() {
        return extendedLinkedShutterGroups;
    }

    public void setExtendedLinkedShutterGroups(List<String> extendedLinkedShutterGroups) {
        this.extendedLinkedShutterGroups = extendedLinkedShutterGroups;
    }

    public List<String> getExtendedLinkedSwitchingGroups() {
        return extendedLinkedSwitchingGroups;
    }

    public void setExtendedLinkedSwitchingGroups(List<String> extendedLinkedSwitchingGroups) {
        this.extendedLinkedSwitchingGroups = extendedLinkedSwitchingGroups;
    }

    public List<String> getShutterProfileGroups() {
        return shutterProfileGroups;
    }

    public void setShutterProfileGroups(List<String> shutterProfileGroups) {
        this.shutterProfileGroups = shutterProfileGroups;
    }

    public List<String> getSwitchingProfileGroups() {
        return switchingProfileGroups;
    }

    public void setSwitchingProfileGroups(List<String> switchingProfileGroups) {
        this.switchingProfileGroups = switchingProfileGroups;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LightAndShadow.class.getSimpleName() + "[", "]")
                .add("extendedLinkedShutterGroups=" + extendedLinkedShutterGroups)
                .add("extendedLinkedSwitchingGroups=" + extendedLinkedSwitchingGroups)
                .add("shutterProfileGroups=" + shutterProfileGroups)
                .add("switchingProfileGroups=" + switchingProfileGroups)
                .toString();
    }
}
