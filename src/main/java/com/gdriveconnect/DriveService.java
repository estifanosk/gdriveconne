package com.gdriveconnect;

/**
 * Created by Estifanos on 3/7/2015.
 */
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Channel;
import org.apache.log4j.Logger;
import com.gdriveconnect.representations.User;

import java.io.IOException;
import java.util.UUID;
// ...

public class DriveService {
    // ...
    static Logger log = Logger.getLogger(
            DriveService.class.getName());

    private static final String NOTIFICATION_URL = "http://ec2-52-10-13-41.us-west-2.compute.amazonaws.com:8080/drive-notification";

    /**
     * Build a Drive service object.
     *
     * @param credentials OAuth 2.0 credentials.
     * @return Drive service object.
     */
    public static Drive buildService(GoogleCredential credentials) {
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();

        return new Drive.Builder(httpTransport, jsonFactory, credentials)
                .build();
    }

    public static void watchFile(String resourceId, String accessToken) {

        Drive drive = buildService(new GoogleCredential().setAccessToken(accessToken));

        String subscriptionId = UUID.randomUUID().toString();

        Channel request = new Channel()
                .setId(subscriptionId)
                .setType("web_hook")
                .setAddress(NOTIFICATION_URL);
        try {
            drive.files().watch(resourceId,request).execute();
        }
        catch (IOException ex){
            log.error("Error : unable to setup channel for end point : " + resourceId );
        }
    }

    // ...
}