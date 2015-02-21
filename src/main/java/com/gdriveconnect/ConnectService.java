package com.gdriveconnect;;

import net.vz.mongodb.jackson.JacksonDBCollection;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.gdriveconnect.representations.Blog;
import com.gdriveconnect.resources.BlogResource;
import com.gdriveconnect.resources.IndexResource;
import com.gdriveconnect.resources.ConnectResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

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
        
      //  JacksonDBCollection<Blog, String> blogs = JacksonDBCollection.wrap(db.getCollection("blogs"), Blog.class, String.class);
        MongoManaged mongoManaged = new MongoManaged(mongo);
        environment.manage(mongoManaged);
        
        environment.addHealthCheck(new MongoHealthCheck(mongo));
        
        //environment.addResource(new BlogResource(blogs));
        //environment.addResource(new IndexResource(blogs));
        environment.addResource(new ConnectResource());
    }
}
