package com.gdriveconnect.resources;

import com.gdriveconnect.DriveAuth;
import com.gdriveconnect.representations.User;
import com.yammer.metrics.annotation.Timed;
import org.apache.log4j.Logger;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;


@Path("/connect")
public class ConnectResource {

    private JacksonDBCollection<User, String> collection;

    static Logger log = Logger.getLogger(DriveResource.class.getName());

    public ConnectResource(JacksonDBCollection<User,String>  coll) {
        this.collection= coll;
    }

    @GET
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Timed
    public Response index() {

        URI uri = UriBuilder.fromUri("http://news.bbc.com").build();

        User user = new User();
        WriteResult<User, String> result = collection.insert(user);
        String state = result.getSavedId();

        log.info("\n state : " + state );

        try {
            uri = UriBuilder.fromUri(DriveAuth.newAuthUrl(state)).build();
        }
        catch ( Exception ex)
        {
            System.console().printf("%s",ex.getMessage());
        }

        return Response.seeOther(uri).build();
    }
}
