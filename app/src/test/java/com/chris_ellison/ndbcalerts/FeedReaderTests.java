package com.chris_ellison.ndbcalerts;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Tester for the FeedReader class.
 *
 * Created by Christopher on 5/10/2016.
 */
@RunWith(Parameterized.class)
public class FeedReaderTests {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // url                                                 , feedTitle
                { "http://www.vogella.com/article.rss"                 , "Eclipse and Android Information" },
                { "http://www.ndbc.noaa.gov/data/latest_obs/yktv2.rss" , "NOAA - National Weather Service" },
        });
    }

    private String url;
    private String feedTitle;

    public FeedReaderTests(String url, String feedTitle) {
        this.url = url;
        this.feedTitle = feedTitle;
    }

    //TODO pull up more NOAA feeds as tests instead of demo feed
    @Test
    public void testFeedReading() {
        FeedReader parser = new FeedReader(this.url);
        Feed feed = parser.readFeed();
        Assert.assertEquals("Feed title was not as expected.", this.feedTitle, feed.getTitle().trim());
        for (FeedMessage message : feed.getMessages()) {
            Assert.assertTrue("Message title was empty.", StringUtils.isNotBlank(message.getTitle()));
        }
    }
}
