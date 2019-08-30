package com.review.Dto;

import com.review.Entity.User;
import lombok.Data;

@Data
public class CommentAndCreator {
    private Integer id;
    private Integer parentId;
    private String comment;
    private Integer type;
    private String commentCreator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private User user;
}
