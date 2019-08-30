package com.review.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
//当回复的时候，将回复的状态插入到表里面
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //写回复的那一个人的id
    private String nitifier;
    //收到回复的那一个人的id
    private Integer receiver;
    //回复的是问题，评论，或者点赞
    private Integer outerid;
    //区分是回复还是评论
    private int type;
    //创建时间
    private Long gmtCreate;
    //回复是已读还是未读，0表示未读，1表示已读，默认为0
    @Column(columnDefinition ="int default 0")
    private int status;
    //当前问题的title
    private String QuestionTitle;
    //写回复的那一个人的名字
    private String nitifierName;
}
