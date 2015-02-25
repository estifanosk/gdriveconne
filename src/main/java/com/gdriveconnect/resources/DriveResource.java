package com.gdriveconnect.resources;

import com.gdriveconnect.DriveAuth;
import com.gdriveconnect.representations.User;
import com.google.api.client.auth.oauth2.Credential;
import com.yammer.metrics.annotation.Timed;
import org.apache.log4j.Logger;
import org.mongojack.JacksonDBCollection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/drivecallback")
public class DriveResource {

    private JacksonDBCollection<User, String> collection;

    static Logger log = Logger.getLogger(
            DriveResource.class.getName());

    public DriveResource(JacksonDBCollection<User,String> coll) {
        this.collection = coll;
    }

    @GET
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Timed
    public Response index(@QueryParam("code") String code,
                          @QueryParam("state") String state
) {

        log.info("code : " + code);
        log.info("state: " + state);




        try {

            Credential credential =  DriveAuth.retrieve(code);

            User foundUser = collection.findOneById(state);

            if (foundUser !=null) {
                foundUser.setDriveAccessToken(credential.getAccessToken());
                foundUser.setDriveRefreshToken(credential.getRefreshToken());
            }

            log.info("\n access token : " + credential.getAccessToken());
            log.info(("\n refresh token:") + credential.getRefreshToken());

        }
        catch ( Exception ex){
            log.error("Error getting credential:" + ex.getMessage());
        }

        return null;
    }
}
