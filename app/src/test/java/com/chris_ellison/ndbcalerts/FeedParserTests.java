package com.chris_ellison.ndbcalerts;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Tester for the FeedParser class.
 *
 * Created by Christopher on 5/10/2016.
 */
@RunWith(Parameterized.class)
public class FeedParserTests {

    private static final String MESSAGE_TEMPLATE = "        <strong>_DATETIME_</strong><br />\n" +
            "        <strong>Location:</strong> _LAT_ _LONG_<br />\n" +
            "        <strong>Wind Direction:</strong> _NESW_ (_HEADING_)<br />\n" +
            "        <strong>Wind Speed:</strong> _SPEED_ knots<br />\n" +
            "        <strong>Wind Gust:</strong> _GUST_ knots<br />\n" +
            "        <strong>Atmospheric Pressure:</strong> _PRES_IN_ in (_PRES_MB_ mb)<br />\n" +
            "        <strong>Air Temperature:</strong> _AIRTEMP_F_F (_AIRTEMP_C_C)<br />\n" +
            "        <strong>Water Temperature:</strong> _WATERTEMP_F_F (_WATERTEMP_C_C)<br />";

    private static final String DATE = "May 10, 2016 5:30 pm EDT";
    private static final String LAT = "37.227N";
    private static final String LONG = "76.479W";
    private static final String NESW = "E";
    private static final String HEADING = "80&#176;";
    private static final String PRES_IN = "30.19";
    private static final String PRES_MB = "1022.5";
    private static final String AIRTEMP_F = "62.1&#176;";
    private static final String AIRTEMP_C = "16.7&#176;";
    private static final String WATERTEMP_F = "64.4&#176;";
    private static final String WATERTEMP_C = "18.0&#176;";

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        //FIXME sometimes the message doesn't contain wind speed/wind gust data.  Not sure if other lines can also be missing.  Need to generate test cases for these conditions.
        String slowWindMsg = createMessage(DATE, LAT, LONG, NESW, HEADING, "1.0", "2.1", PRES_IN, PRES_MB, AIRTEMP_F, AIRTEMP_C, WATERTEMP_F, WATERTEMP_C);
        String medWindMsg = createMessage(DATE, LAT, LONG, NESW, HEADING, "10.0", "12.1", PRES_IN, PRES_MB, AIRTEMP_F, AIRTEMP_C, WATERTEMP_F, WATERTEMP_C);
        String fastWindMsg = createMessage(DATE, LAT, LONG, NESW, HEADING, "25.0", "30.1", PRES_IN, PRES_MB, AIRTEMP_F, AIRTEMP_C, WATERTEMP_F, WATERTEMP_C);

        return Arrays.asList(new Object[][] {
                // message    , propertyName , minValue , maxValue , expected
                { slowWindMsg , "Wind Speed" , "5.0"    , "12.0"   , false },
                { medWindMsg  , "Wind Speed" , "5.0"    , "12.0"   , true  },
                { fastWindMsg , "Wind Speed" , "5.0"    , "12.0"   , false },
                { slowWindMsg , "Wind Speed" , "5.0"    , null     , false },
                { medWindMsg  , "Wind Speed" , "5.0"    , null     , true  },
                { fastWindMsg , "Wind Speed" , "5.0"    , null     , true  },
                { slowWindMsg , "Wind Speed" , null     , "10.0"   , true  },
                { medWindMsg  , "Wind Speed" , null     , "10.0"   , true  },
                { fastWindMsg , "Wind Speed" , null     , "10.0"   , false },
                { medWindMsg  , "Wind Speed" , "9.9"    , null     , true  },
                { medWindMsg  , "Wind Speed" , "10.0"   , null     , true  },
                { medWindMsg  , "Wind Speed" , "10.1"   , null     , false },
                { fastWindMsg , "Wind Speed" , null     , "24.9"   , false },
                { fastWindMsg , "Wind Speed" , null     , "25.0"   , true  },
                { fastWindMsg , "Wind Speed" , null     , "25.1"   , true  },
                // test cases - less than min, more than max, within range, null min, null max, float vs. integer values, different properties - different types of properties?
        });
    }

    private String message;
    private String propertyName;
    private String minValue;
    private String maxValue;
    private boolean expected;

    public FeedParserTests(String message, String propertyName, String minValue, String maxValue, boolean expected) {
        this.message = message;
        this.propertyName = propertyName;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.expected = expected;
    }

    @Test
    public void testFeedParsing() {
        FeedMessage feedMessage = new FeedMessage();
        feedMessage.setDescription(message);

        Criterion criterion = new Criterion();
        criterion.setPropertyName(propertyName);
        criterion.setMinValue(minValue);
        criterion.setMaxValue(maxValue);

        FeedParser parser = new FeedParser();
        boolean actual = parser.alertOnMessage(feedMessage, criterion);

        Assert.assertEquals("Result didn't match.", expected, actual);
    }

    private static String createMessage(String dateRepl, String latRepl, String longRepl, String neswRepl, String headingRepl, String windSpeedRepl, String windGustRepl, String presInRepl, String presMbRepl, String airTempFRepl, String airTempCRepl, String waterTempFRepl, String waterTempCRepl) {
        String myResult = MESSAGE_TEMPLATE;
        myResult = myResult.replaceAll("_DATETIME_", dateRepl);
        myResult = myResult.replaceAll("_LAT_", latRepl);
        myResult = myResult.replaceAll("_LONG_", longRepl);
        myResult = myResult.replaceAll("_NESW_", neswRepl);
        myResult = myResult.replaceAll("_HEADING_", headingRepl);
        myResult = myResult.replaceAll("_SPEED_", windSpeedRepl);
        myResult = myResult.replaceAll("_GUST_", windGustRepl);
        myResult = myResult.replaceAll("_PRES_IN_", presInRepl);
        myResult = myResult.replaceAll("_PRES_MB_", presMbRepl);
        myResult = myResult.replaceAll("_AIR_TEMP_F_", airTempFRepl);
        myResult = myResult.replaceAll("_AIR_TEMP_C_", airTempCRepl);
        myResult = myResult.replaceAll("_WATER_TEMP_F_", waterTempFRepl);
        myResult = myResult.replaceAll("_WATER_TEMP_C_", waterTempCRepl);
        return myResult;
    }

    private static final String EXAMPLE_TITLE = "Station YKTV2 - 8637689 - Yorktown, VA";
    private static final String EXAMPLE_DESC = "        <strong>May 10, 2016 5:30 pm EDT</strong><br />\n" +
            "        <strong>Location:</strong> 37.227N 76.479W<br />\n" +
            "        <strong>Wind Direction:</strong> E (80&#176;)<br />\n" +
            "        <strong>Wind Speed:</strong> 7.0 knots<br />\n" +
            "        <strong>Wind Gust:</strong> 8.9 knots<br />\n" +
            "        <strong>Atmospheric Pressure:</strong> 30.19 in (1022.5 mb)<br />\n" +
            "        <strong>Air Temperature:</strong> 62.1&#176;F (16.7&#176;C)<br />\n" +
            "        <strong>Water Temperature:</strong> 64.4&#176;F (18.0&#176;C)<br />";
    private static final String EXAMPLE_URL = "http://weather.gov/images/xml_logo.gif";
}
