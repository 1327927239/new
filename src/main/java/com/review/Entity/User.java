package com.review.Entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table(name="userReview")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private String accountId;
    @Column(length = 50)
    private String name;
    @Column(length = 36)
    private String tooken;
    private Long gmtCreate;
    private Long gmtModified;
    @Column(length = 256)
    private String bio;
    private String avatarUrl;
}
