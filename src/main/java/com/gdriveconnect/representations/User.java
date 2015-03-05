package com.gdriveconnect.representations;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import org.mongojack.Id;
import org.mongojack.ObjectId;

public class User {


    private String id;

    @ObjectId
    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @ObjectId
    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }


    private String name;

    @JsonProperty("name")
    public void setName(String name) {
        this.name=name;
    }

    @JsonProperty("name")
    public String getName() { return this.name;}


    @NotBlank
    private String driveAuthCode;

    @NotBlank
    private String driveAccessToken;

    @NotBlank
    private String driveRefreshToken;


    private final Date publishedOn = new Date();


    public User() {

    }

    /*
    @JsonCreator
    public User(@Id @ObjectId id) {
        this.id = id;
    }
      */

    public String getDriveAuthCode() {
        return driveAuthCode;
    }

    public String getDriveAccessToken() {
        return driveAccessToken;
    }


    public void setDriveAccessToken(String driveAccessToken) {
        this.driveAccessToken = driveAccessToken;
    }

    public String getDriveRefreshToken() {
        return driveRefreshToken;
    }

    public void setDriveRefreshToken(String driveRefreshToken) {
        this.driveRefreshToken = driveRefreshToken;
    }


}
