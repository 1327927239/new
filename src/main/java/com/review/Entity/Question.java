package com.review.Entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private String creator;
    @Column(columnDefinition ="int default 0" )
//    评论数
    private Integer commentCount;
    @Column(columnDefinition ="int default 0" )
    private Integer viewCount;
    @Column(columnDefinition ="int default 0" )
    private Integer likeCount;
    @Column(length = 256)
    private String tag;

}
