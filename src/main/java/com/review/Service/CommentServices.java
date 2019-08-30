package com.review.Service;

import com.review.Entity.Comment;
import com.review.Entity.Notification;
import com.review.Entity.Question;
import com.review.Entity.User;
import com.review.Enum.NotificationEnum;
import com.review.Enum.NotificationTypeEnum;
import com.review.Mapper.QuestionMapper;
import com.review.Mapper.UserMapper;
import com.review.Repository.NotificationRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServices {
    @Autowired
    NotificationRepositiry notificationRepositiry;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    public void reply(Comment comment, NotificationTypeEnum notificationTypeEnum) {
//        通过parentid 查出question表里的问题创建者，也就是这里的回复接受者
        Question question = questionMapper.SelectCreator(comment.getParentId());
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNitifier(comment.getCommentCreator());
        notification.setReceiver(Integer.valueOf(question.getCreator()));
        notification.setType(NotificationEnum.Reply_Question.getType());
        notification.setOuterid(comment.getParentId());
        notification.setStatus(notificationTypeEnum.Unread.getStatus());
        //设置notification表里面的评论者的名字
        String name = userMapper.selectByCommentCreator(comment.getCommentCreator());
        notification.setNitifierName(name);
        //设置notification表里面的问题的title
        notification.setQuestionTitle(question.getTitle());
        //将数据插入数据库
        notificationRepositiry.save(notification);
    }
}
