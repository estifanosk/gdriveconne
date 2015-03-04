package com.gdriveconnect;

import com.gdriveconnect.representations.BlogPost;
import com.gdriveconnect.resources.ConnectResource;
import com.gdriveconnect.resources.DriveResource;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import org.mongojack.JacksonDBCollection;


public class ConnectService extends Service<ConnectConfiguration> {

    public static void main(String[] args) throws Exception {
        new ConnectService().run(new String[] { "server", "config.yml" });
    }

    
    @Override
    public void initialize(Bootstrap<ConnectConfiguration> bootstrap) {
        bootstrap.setName("concur-drive-connect");
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(ConnectConfiguration configuration, Environment environment) throws Exception {
        Mongo mongo = new Mongo(configuration.mongohost, configuration.mongoport);
        DB db = mongo.getDB(configuration.mongodb);
        
        JacksonDBCollection<BlogPost, String> users = JacksonDBCollection.wrap(db.getCollection("users"), BlogPost.class, String.class);
        MongoManaged mongoManaged = new MongoManaged(mongo);
        environment.manage(mongoManaged);
        
        environment.addHealthCheck(new MongoHealthCheck(mongo));
        
        //environment.addResource(new BlogResource(blogs));
        //environment.addResource(new IndexResource(blogs));
        environment.addResource(new ConnectResource(users));
    }
}
