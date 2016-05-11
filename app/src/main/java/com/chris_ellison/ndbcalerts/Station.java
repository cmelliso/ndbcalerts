package com.chris_ellison.ndbcalerts;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a single observation station (e.g., buoy).
 *
 * Created by Christopher on 5/10/2016.
 */
public class Station {

    /*
        FIXME Station name and feed URL is currently defaulted in the preferences file.  These should
        really be updated by a StationActivity or something rather than hardcoding the name in the
        preferences.
     */

    private String feedUrl;
    private String name;
    private Collection<Criterion> criteria;

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Criterion> getCriteria() {
        return criteria;
    }

    public void setCriteria(Collection<Criterion> criteria) {
        this.criteria = criteria;
    }

    public void addCriterion(Criterion criterion) {
        if(null == this.criteria) {
            this.criteria = new ArrayList<Criterion>();
        }
        this.criteria.add(criterion);
    }
}
