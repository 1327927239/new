package com.review.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer parentId;
    private String comment;
    private Integer type;
    private String commentCreator;
    private Long gmtCreate;
    private Long gmtModified;
    @Column(columnDefinition ="int default 0" )
    private Long likeCount;
}
