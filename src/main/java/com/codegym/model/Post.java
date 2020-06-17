package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
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

    @ManyToOne
    @JoinColumn(name = "categories_id")
    public Category category;
}
