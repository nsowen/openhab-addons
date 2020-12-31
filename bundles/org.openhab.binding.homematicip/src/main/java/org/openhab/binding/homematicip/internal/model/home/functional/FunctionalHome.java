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
package org.openhab.binding.homematicip.internal.model.home.functional;

import java.util.List;

import org.openhab.binding.homematicip.internal.model.HomematicIPObject;

/**
 * Functional Home
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public abstract class FunctionalHome extends HomematicIPObject {

    protected List<String> functionalGroups;
    protected String solution;
    protected boolean active;

    public List<String> getFunctionalGroups() {
        return functionalGroups;
    }

    public void setFunctionalGroups(List<String> functionalGroups) {
        this.functionalGroups = functionalGroups;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
