package com.codegym.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "post_tags")
public class Post_Tag {
    @Id
    @GeneratedValue
    private Long id;

    private short isDelete;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    public Tag tag;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Post_Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(short isDelete) {
        this.isDelete = isDelete;
    }
}
