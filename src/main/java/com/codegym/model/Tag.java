package com.codegym.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
@Entity
@Table(name = "tags")
@Data
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition="varchar(75)")
    private String title;
    private short isDelete;
}