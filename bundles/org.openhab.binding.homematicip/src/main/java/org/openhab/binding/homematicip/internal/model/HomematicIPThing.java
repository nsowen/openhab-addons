package org.openhab.binding.homematicip.internal.model;

/**
 * Thing instance
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-31
 */
public interface HomematicIPThing {

    /**
     * Returns the unique ID of the thing
     * 
     * @return unique ID of the thing
     */
    String getId();

    /**
     * Returns the home ID of the home the thing is located
     * 
     * @return home ID of the home the thing is located
     */
    String getHomeId();

    /**
     * Returns a human-readable label of the thing
     * 
     * @return human-readable label of the thing
     */
    String getLabel();

    /**
     * Returns a human-readable type of the thing
     * 
     * @return human-readable type of the thing
     */
    String getType();
}
