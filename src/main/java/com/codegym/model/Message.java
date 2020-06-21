package com.codegym.model;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "messages")
@Data
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 10, max = 2000, message
            = "Comment must be min 10 characters")
    private String writeComment;
    private short isDelete;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Column(columnDefinition="DATE")
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWriteComment() {
        return writeComment;
    }

    public void setWriteComment(String writeComment) {
        this.writeComment = writeComment;
    }

    public short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(short isDelete) {
        this.isDelete = isDelete;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}