package com.gdriveconnect.resources;

import com.gdriveconnect.representations.FileChange;
import com.gdriveconnect.representations.User;
import com.yammer.metrics.annotation.Timed;
import org.apache.log4j.Logger;
import org.mongojack.JacksonDBCollection;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/drive-notification")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public class NotificationResource {

    private JacksonDBCollection<User, String> collection;

    static Logger log = Logger.getLogger(
            NotificationResource.class.getName());


    @POST
    @Timed
    public Response index(@Valid FileChange change) {

        log.info("change notificaion received:"+  change.toString());

        return null;
    }

}
