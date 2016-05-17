package com.chris_ellison.ndbcalerts;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * This parser is currently very hardcoded to deal with the NDBC RSS feed for Yorktown, VA, station YKTV2.  I've been using that as my lead test case.  The messages follow this format.
 * <p>
 *     <em>Title</em>: the station identifier, formatted like <code>Station YKTV2 - 8637689 - Yorktown, VA</code>
 * </p>
 * <p>
 *     <em>Description</em>: the station message.  The description contains the meat of the message as an HTML snipped, with parameters wrapped with <em>strong</em> tags and followed by colons.  The values are between the closing <em>strong</em> tag and a single <em>br</em> tag.  We can use these delimiters to find the parameter we care about.
 * </p>
 * <p>
 *     <em>URL</em>:  The message URL is just a link to a logo GIF.
 * </p>
 * Created by Christopher on 5/10/2016.
 */
public class FeedParser {

    private static final String VALUE_START_MARKER = "</strong>";
    private static final String VALUE_END_MARKER_WIND = "knots";
    private static final String VALUE_END_MARKER = "<br />";

    public boolean alertOnMessage(FeedMessage message, Criterion... criteria) {
        //FIXME This first pass is only going to handle Wind Speed criteria.

        String content = message.getDescription();

        boolean myResult = true;
        for(Criterion criterion : criteria) {
            String propName = criterion.getPropertyName();
            int propNameIndex = content.indexOf(propName);
            if(propNameIndex >= 0) {
                //TODO look up property by reflection or by map of key to method in FeedMessage class or something and let FeedMessage parse out the relevant bits.
                // Old FeedParserTests will need to be refactored to test FeedMessage methods instead.
                int propValueSubstrStart = content.indexOf(VALUE_START_MARKER, propNameIndex) + VALUE_START_MARKER.length();
                int propValueSubstrEnd = content.indexOf(VALUE_END_MARKER_WIND, propNameIndex);

                String valueString = content.substring(propValueSubstrStart, propValueSubstrEnd).trim();
                float value = Float.parseFloat(valueString);

                String minValueString = criterion.getMinValue();
                if(StringUtils.isNotBlank(minValueString)) {
                    myResult &= (Float.parseFloat(minValueString) <= value);
                }

                String maxValueString = criterion.getMaxValue();
                if(StringUtils.isNotBlank(maxValueString)) {
                    myResult &= (Float.parseFloat(maxValueString) >= value);
                }
            }
        }

        return myResult;
    }
}
