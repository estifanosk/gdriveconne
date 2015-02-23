package com.gdriveconnect.representations;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.ObjectId;

public class User {

    @Id
    @ObjectId
    private String id;


    @NotBlank
    private String driveAuthCode;

    private final Date publishedOn = new Date();

    public User() {
    }

    public User(String driveAuthCode) {
        super();
        this.driveAuthCode = driveAuthCode;
    }

    public String getId() {
        return id;
    }

    public String getDriveAuthCode() {
        return driveAuthCode;
    }

}
