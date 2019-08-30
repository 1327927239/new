package com.review.Dto;

import lombok.Data;

@Data
public class CommentDto {
    private Integer parentId;
    private String comment;
    private Integer type;
}
