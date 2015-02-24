package com.gdriveconnect.resources;

import com.gdriveconnect.DriveAuth;
import com.google.api.client.auth.oauth2.Credential;
import com.yammer.metrics.annotation.Timed;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/drivecallback")
public class DriveResource {

    static Logger log = Logger.getLogger(
            DriveResource.class.getName());

    public DriveResource() {

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
            log.info("access token : " + credential.getAccessToken());
            log.info(("refresh token:") + credential.getRefreshToken());

        }
        catch ( Exception ex){
            log.error("Error getting credential:" + ex.getMessage());
        }

        return null;
    }
}
