package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 30, message
            = "Title min 5 characters and max 30")
    private String title;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Long numberView;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Long numberLike;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 5, message
            = "description min 5 characters")
    private String description;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 5, message
            = "Content min 5 characters and max 5000")
    private String content;

    private String image;

    private LocalDateTime Date;

    @Transient
    @JsonIgnore
    private CommonsMultipartFile[] fileImage;

    private short isDelete;


    @JsonIgnore
    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER)
    public Set<Post_Tag> post_tags;

    @Transient
    private Set<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    public Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER)
    public Set<Message> messages;

    public Set<Post_Tag> getPost_tags() {
        return post_tags;
    }

    public void setPost_tags(Set<Post_Tag> post_tags) {
        this.post_tags = post_tags;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
