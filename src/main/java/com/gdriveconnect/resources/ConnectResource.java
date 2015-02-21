package com.gdriveconnect.resources;

import com.gdriveconnect.DriveAuth;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;


@Path("/connect")
public class ConnectResource {


    public ConnectResource() {

    }

    @GET
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Timed
    public Response index() {

        URI uri = UriBuilder.fromUri("http://news.bbc.com").build();

        try {
            uri = UriBuilder.fromUri(DriveAuth.newAuthUrl()).build();
        }
        catch ( Exception ex)
        {
            System.console().printf("%s",ex.getMessage());
        }

        return Response.seeOther(uri).build();
    }
}
