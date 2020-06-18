package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "varchar(75)")
    private String title;

    private short isDelete;

    @Transient
    private boolean selected;
    @JsonIgnore
    @OneToMany(mappedBy = "tag", fetch = FetchType.EAGER)
    public Set<Post_Tag> post_tags;

    public Set<Post_Tag> getPost_tags() {
        return post_tags;
    }

    public void setPost_tags(Set<Post_Tag> post_tags) {
        this.post_tags = post_tags;
    }

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(short isDelete) {
        this.isDelete = isDelete;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}