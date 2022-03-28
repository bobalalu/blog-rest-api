package com.bobalalu.blog.post;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Babatunde Obalalu - bobalalu@yahoo.com - +2348034627801
 */
@Entity(name = "posts")
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Title is required")
    private String title;

    //@NotNull(message = "Slug is required")
    private String slug;

    @NotNull(message = "Content is required")
    private String content;

    public Post () {}

    public Post (long id, String title, String slug, String content) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.content = content;
    }

    public String toFormat() {
        return String.format("Blog Post::: ID: %d, Title: %s, Content:%s", id, title, content);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = this.id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}