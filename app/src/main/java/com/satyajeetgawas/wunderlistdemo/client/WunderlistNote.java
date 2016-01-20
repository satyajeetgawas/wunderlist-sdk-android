package com.satyajeetgawas.wunderlistdemo.client;

import java.util.Date;

/**
 * Created by satyajeet on 11/22/2015.
 */
public class WunderlistNote {
    private String mId;
    private String mTitle;
    private String mContent;
    private Date mCreatedDate;



    public WunderlistNote(String mId, String mTitle, String mContent, Date date) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mCreatedDate = date;
    }


    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public Date getCreated() {
        return mCreatedDate;
    }
}
