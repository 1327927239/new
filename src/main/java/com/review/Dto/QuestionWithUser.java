package com.review.Dto;

import com.review.Entity.User;
import lombok.Data;


@Data
public class QuestionWithUser {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private String creator;
//    评论数
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
