package com.gdriveconnect.resources;

import com.gdriveconnect.DriveAuth;
import com.gdriveconnect.representations.User;
import com.yammer.metrics.annotation.Timed;
import org.apache.log4j.Logger;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;
import org.omg.CORBA.*;

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
        this.collection = coll;
    }

    @GET
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Timed
    public Response index() throws  Exception {

        /*
        URI uri = UriBuilder.fromUri("http://news.bbc.com").build();
        log.info("\n state : " + id );

        User foundUser = collection.findOneById(id);
        foundUser.setDriveAccessToken("atoken");
        foundUser.setDriveRefreshToken("rtoken");
        log.info(foundUser.getId());

        collection.updateById(state,foundUser);
        foundUser = collection.findOneById(state);
        */

        User user = new User();
        WriteResult<User, String> result = collection.insert(user);
        String state = result.getSavedId();

        URI uri = UriBuilder.fromUri(DriveAuth.newAuthUrl(state)).build();
        return Response.seeOther(uri).build();

    }
}
