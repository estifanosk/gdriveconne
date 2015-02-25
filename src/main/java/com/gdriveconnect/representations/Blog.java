package com.gdriveconnect.representations;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import org.mongojack.Id;
import org.mongojack.ObjectId;

public class Blog {

    @Id
    @ObjectId
    private String id;

    @NotBlank
    private String title;

    @URL
    @NotBlank
    private String url;
    
    private final Date publishedOn = new Date();

    public Blog() {
    }

    public Blog(String title, String url) {
        super();
        this.title = title;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }
}
