package com.gdriveconnect.resources;

import com.gdriveconnect.DriveAuth;
import com.gdriveconnect.DriveService;
import com.gdriveconnect.representations.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.yammer.metrics.annotation.Timed;
import org.apache.http.client.HttpResponseException;
import org.apache.log4j.Logger;
import org.mongojack.JacksonDBCollection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

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
                          @QueryParam("state") String state)  {

        log.info("code : " + code);
        log.info("state: " + state);

        try {

            GoogleCredential credential =  DriveAuth.retrieve(code);
            Drive drive = DriveService.buildService(credential);

            User foundUser = collection.findOneById(state);

            if (foundUser !=null) {

                foundUser.setDriveAuthCode(code);
                foundUser.setDriveAccessToken(credential.getAccessToken());
                foundUser.setDriveRefreshToken(credential.getRefreshToken());

                String fileId = createFolder( drive, "concur_expense");

                if (fileId!=null && !fileId.equals("")){
                    foundUser.setDriveResourceId(fileId);
                }

                collection.updateById(state,foundUser);
            }

            log.info("\n access token : " + credential.getAccessToken());
            log.info(("\n refresh token:") + credential.getRefreshToken());
        }
        catch ( Exception ex){
            log.error("Error getting credential:" + ex.getMessage());
        }

        return null;
    }

    /**
     * Creates a folder [concur_expense] in users google drive.
     *
     * @param service Drive API service instance.
     * @param fileId Name of folder
     *
     * Returns the create folder id.
     */
    static String createFolder(Drive service, String fileId) {
        try {

            File body = new File();
            body.setTitle(fileId);
            body.setMimeType("application/vnd.google-apps.folder");
            File file = service.files().insert(body).execute();

            return  file.getId();

        } catch (HttpResponseException e) {
            if (e.getStatusCode() == 401) {
                // Credentials have been revoked.
                // TODO: Redirect the user to the authorization URL.
                throw new UnsupportedOperationException();
            }
        } catch (IOException e) {
            System.out.println("An error occurred creatign resource in google drive: " + e);
        }

        return "";
    }

}
