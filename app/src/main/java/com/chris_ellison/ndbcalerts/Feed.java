package com.chris_ellison.ndbcalerts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher on 5/10/2016.
 */
public class Feed {
    private final String title;
    private final String url;
    private final String description;
    private final String language;
    private final String copyright;
    private final String publicationDate;

    private final List<FeedMessage> entries = new ArrayList<FeedMessage>();

    public Feed(String title, String url, String description, String language,
                String copyright, String publicationDate) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.language = language;
        this.copyright = copyright;
        this.publicationDate = publicationDate;
    }

    public List<FeedMessage> getMessages() {
        return entries;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getPublicationDate() {
        return publicationDate;
    }
}
