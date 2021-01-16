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
package org.openhab.persistence.dynamodb.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.jupiter.api.Test;
import org.openhab.core.library.items.CallItem;
import org.openhab.core.library.items.ColorItem;
import org.openhab.core.library.items.ContactItem;
import org.openhab.core.library.items.DateTimeItem;
import org.openhab.core.library.items.DimmerItem;
import org.openhab.core.library.items.LocationItem;
import org.openhab.core.library.items.NumberItem;
import org.openhab.core.library.items.PlayerItem;
import org.openhab.core.library.items.RollershutterItem;
import org.openhab.core.library.items.StringItem;
import org.openhab.core.library.items.SwitchItem;

/**
 * Test for AbstractDynamoDBItem.getDynamoItemClass
 *
 * @author Sami Salonen - Initial contribution
 */
@NonNullByDefault
public class AbstractDynamoDBItemGetDynamoItemClassTest {

    @Test
    public void testCallItem() throws IOException {
        assertEquals(DynamoDBStringItem.class, AbstractDynamoDBItem.getDynamoItemClass(CallItem.class));
    }

    @Test
    public void testContactItem() throws IOException {
        assertEquals(DynamoDBBigDecimalItem.class, AbstractDynamoDBItem.getDynamoItemClass(ContactItem.class));
    }

    @Test
    public void testDateTimeItem() throws IOException {
        assertEquals(DynamoDBStringItem.class, AbstractDynamoDBItem.getDynamoItemClass(DateTimeItem.class));
    }

    @Test
    public void testStringItem() throws IOException {
        assertEquals(DynamoDBStringItem.class, AbstractDynamoDBItem.getDynamoItemClass(StringItem.class));
    }

    @Test
    public void testLocationItem() throws IOException {
        assertEquals(DynamoDBStringItem.class, AbstractDynamoDBItem.getDynamoItemClass(LocationItem.class));
    }

    @Test
    public void testNumberItem() throws IOException {
        assertEquals(DynamoDBBigDecimalItem.class, AbstractDynamoDBItem.getDynamoItemClass(NumberItem.class));
    }

    @Test
    public void testColorItem() throws IOException {
        assertEquals(DynamoDBStringItem.class, AbstractDynamoDBItem.getDynamoItemClass(ColorItem.class));
    }

    @Test
    public void testDimmerItem() throws IOException {
        assertEquals(DynamoDBBigDecimalItem.class, AbstractDynamoDBItem.getDynamoItemClass(DimmerItem.class));
    }

    @Test
    public void testPlayerItem() throws IOException {
        assertEquals(DynamoDBStringItem.class, AbstractDynamoDBItem.getDynamoItemClass(PlayerItem.class));
    }

    @Test
    public void testRollershutterItem() throws IOException {
        assertEquals(DynamoDBBigDecimalItem.class, AbstractDynamoDBItem.getDynamoItemClass(RollershutterItem.class));
    }

    @Test
    public void testOnOffTypeWithSwitchItem() throws IOException {
        assertEquals(DynamoDBBigDecimalItem.class, AbstractDynamoDBItem.getDynamoItemClass(SwitchItem.class));
    }
}
