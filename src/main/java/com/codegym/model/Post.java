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

    @Column(nullable = false,columnDefinition="varchar(500)")
    @NotNull
    @NotEmpty
    private String title;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Long numberView;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Long numberLike;

    @Column(nullable = false,columnDefinition="LONGTEXT")
    @NotNull
    @NotEmpty
    private String description;

    @Column(nullable = false,columnDefinition="LONGTEXT")
    @NotNull
    @NotEmpty
    private String content;

    private String image;

    @Column(columnDefinition="DATETIME")
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
