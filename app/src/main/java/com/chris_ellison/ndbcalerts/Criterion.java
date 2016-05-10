package com.chris_ellison.ndbcalerts;

/**
 * Bean object representing a criterion for triggering a notification.  If the property falls between
 * the minimum and maximum values specified (inclusive), the notification will be triggered.  If the
 * minimum or maximum value is unset, the range is considered to extend to infinity in that direction.
 * Created by Christopher on 5/10/2016.
 */
public class Criterion {

    private String propertyName;
    private String minValue;
    private String maxValue;

    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Injects the property name this criterion will check against.  The property name must match
     * the string between the <em>strong</em> tags in the message's HTML snippet, sans the colon.
     *
     * @param propertyName The exact String to be searched for in the feed message.
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

}
