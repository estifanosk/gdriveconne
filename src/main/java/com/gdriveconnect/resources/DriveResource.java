package com.gdriveconnect.resources;

import com.gdriveconnect.representations.Blog;
import com.yammer.metrics.annotation.Timed;
import net.vz.mongodb.jackson.JacksonDBCollection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Path("/drivecallback")
public class DriveResource {


    public DriveResource() {

    }

    @GET
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Timed
    public Response index() {

        URI uri = UriBuilder.fromUri("http://news.bbc.com").build();
        return Response.seeOther(uri).build();
    }
}
