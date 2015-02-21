package com.gdriveconnect.views;

import java.util.List;

import com.google.common.base.Charsets;
import com.gdriveconnect.representations.Blog;
import com.yammer.dropwizard.views.View;

public class IndexView extends View {

    private List<Blog> blogs;
    
    public IndexView(List<Blog> blogs) {
        super("/views/index.ftl", Charsets.UTF_8);
        this.blogs = blogs;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }
}
