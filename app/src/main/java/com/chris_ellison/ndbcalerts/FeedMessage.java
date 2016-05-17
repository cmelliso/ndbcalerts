package com.chris_ellison.ndbcalerts;

import android.text.style.TtsSpan;

import java.util.Date;

/**
 * Bean object representing a single message from an RSS feed.
 * Created by Christopher on 5/10/2016.
 */
public class FeedMessage {
    private static final String VALUE_START_MARKER = "</strong>";
    private static final String VALUE_END_MARKER_WIND = "knots";
    private static final String VALUE_END_MARKER = "<br />";


    private String title;
    private String description;
    private String url;
    private String author;
    private String guid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    //TODO add helper messages to parse out and retrieve relevant parts of the message
    public Float getWindSpeed() {
        String valueString = extractDataItem("Wind Speed");
        return Float.valueOf(valueString);
    }

    public String getTime() {
        return extractDataItem("<strong>");
    }

    private String extractDataItem(String propName) {
        String myResult = "";
        int propNameIndex = description.indexOf(propName);
        if(propNameIndex >= 0) {
            int propValueSubstrStart = description.indexOf(VALUE_START_MARKER, propNameIndex) + VALUE_START_MARKER.length();
            int propValueSubstrEnd = 0;
            if(propName.startsWith("Wind")) {
                propValueSubstrEnd = description.indexOf(VALUE_END_MARKER_WIND, propNameIndex);
            } else {
                propValueSubstrEnd = description.indexOf(VALUE_END_MARKER, propNameIndex);
            }

            myResult = description.substring(propValueSubstrStart, propValueSubstrEnd).trim();
        }
        return myResult;
    }
}
