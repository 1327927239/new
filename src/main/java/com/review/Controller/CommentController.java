package com.review.Controller;

import com.review.Dto.CommentDto;
import com.review.Entity.Comment;
import com.review.Entity.Question;
import com.review.Entity.User;
import com.review.Enum.NotificationTypeEnum;
import com.review.Mapper.QuestionMapper;
import com.review.Repository.CommentRepository;
import com.review.Service.CommentServices;
import com.review.Service.QuestionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    QuestionServices questionServices;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    CommentServices commentServices;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDto commentDto,
                       Model model,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("message", "没有登录哦，请登录");
            return "error";
        }
        Question question = questionMapper.selectById(commentDto.getParentId());
        if (question == null) {
            model.addAttribute("message", "回复的问题不存在哦");
            return "error";
        }
        if (commentDto.getComment().length()<1) {
            model.addAttribute("message", "回复的内容为空");
            return "error";
        }
        if (commentDto.getComment() != null || commentDto.getComment().length()>0 ) {
            Comment comment = new Comment();
            comment.setComment(commentDto.getComment());
            comment.setType(commentDto.getType());
            comment.setParentId(commentDto.getParentId());
            comment.setCommentCreator(user.getAccountId());
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setGmtModified(comment.getGmtCreate());
            commentRepository.save(comment);
            questionServices.addCommentCount(commentDto.getParentId());
            commentServices.reply(comment, NotificationTypeEnum.Unread);

        }

        return null;

    }
}
