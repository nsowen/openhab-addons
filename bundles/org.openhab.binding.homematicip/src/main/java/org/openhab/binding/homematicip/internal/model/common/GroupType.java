package org.openhab.binding.homematicip.internal.model.common;

import org.openhab.binding.homematicip.internal.model.group.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * New class.
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public enum GroupType {

    META(MetaGroup.class),
    ALARM_SWITCHING(AlarmSwitchingGroup.class),
    ENVIRONMENT(EnvironmentGroup.class),
    EXTENDED_LINKED_SHUTTER(ExtendedLinkedShutterGroup.class),
    EXTENDED_LINKED_SWITCHING(ExtendedLinkedSwitchingGroup.class),
    HEATING(HeatingGroup.class),
    HEATING_CHANGEOVER(HeatingChangeoverGroup.class),
    HEATING_COOLING_DEMAND(HeatingCoolingDemandGroup.class),
    HEATING_COOLING_DEMAND_BOILER(HeatingCoolingDemandBoilerGroup.class),
    HEATING_COOLING_DEMAND_PUMP(HeatingCoolingDemandPumpGroup.class),
    HEATING_DEHUMIDIFIER(HeatingDehumidifierGroup.class),
    HEATING_EXTERNAL_CLOCK(HeatingExternalClockGroup.class),
    HEATING_FAILURE_ALERT_RULE_GROUP(HeatingFailureAlertRuleGroup.class),
    HEATING_HUMIDITY_LIMITER(HeatingHumidyLimiterGroup.class),
    HEATING_TEMPERATURE_LIMITER(HeatingTemperatureLimiterGroup.class),
    HOT_WATER(HotWaterGroup.class),
    HUMIDITY_WARNING_RULE_GROUP(HumidityWarningRuleGroup.class),
    INBOX(InboxGroup.class),
    LINKED_SWITCHING(LinkedSwitchingGroup.class),
    LOCK_OUT_PROTECTION_RULE(LockOutProtectionRule.class),
    OVER_HEAT_PROTECTION_RULE(OverHeatProtectionRule.class),
    SECURITY(SecurityGroup.class),
    SECURITY_BACKUP_ALARM_SWITCHING(AlarmSwitchingGroup.class),
    SECURITY_ZONE(SecurityZoneGroup.class),
    SHUTTER_PROFILE(ShutterProfile.class),
    SHUTTER_WIND_PROTECTION_RULE(ShutterWindProtectionRule.class),
    SMOKE_ALARM_DETECTION_RULE(SmokeAlarmDetectionRule.class),
    SWITCHING(SwitchingGroup.class),
    SWITCHING_PROFILE(SwitchingProfileGroup.class);

    private final Class<? extends Group> clazz;

    GroupType(Class<? extends Group> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Group> getClazz() {
        return clazz;
    }

}
