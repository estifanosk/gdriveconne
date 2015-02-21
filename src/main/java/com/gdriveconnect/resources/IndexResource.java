package com.gdriveconnect.resources;

import com.gdriveconnect.representations.Blog;
import com.gdriveconnect.views.IndexView;
import com.yammer.dropwizard.views.View;
import com.yammer.metrics.annotation.Timed;
import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class IndexResource {

    private JacksonDBCollection<Blog, String> collection;

    public IndexResource(JacksonDBCollection<Blog, String> blogs) {
        this.collection = blogs;
    }

    @GET
    @Produces("text/html;charset=UTF-8")
    @Timed
    public View index() {
        DBCursor<Blog> dbCursor = collection.find();
        List<Blog> blogs = new ArrayList<>();
        while (dbCursor.hasNext()) {
            Blog blog = dbCursor.next();
            blogs.add(blog);
        }
        return new IndexView(blogs);
    }

}
