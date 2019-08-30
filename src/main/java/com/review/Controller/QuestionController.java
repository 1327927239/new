package com.review.Controller;

import com.review.Dto.CommentAndCreator;
import com.review.Dto.QuestionWithUser;
import com.review.Entity.Comment;
import com.review.Entity.Notification;
import com.review.Entity.Question;
import com.review.Entity.User;
import com.review.Mapper.CommentMapper;
import com.review.Mapper.NotificationMapper;
import com.review.Mapper.UserMapper;
import com.review.Repository.CommentRepository;
import com.review.Service.QuestionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class QuestionController {

    @Autowired
    QuestionServices questionServices;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationMapper notificationMapper;

    @GetMapping("/question/{id}")
    public String Question(HttpServletRequest request, @PathVariable(name = "id") Integer id,
                           Model model) {
        User SessionUser =(User) request.getSession().getAttribute("user");
        QuestionWithUser questionAndUser = questionServices.findById(id);
        //根据tag查找出来的相同标签的问题
        List<Question> questionsByTag =questionServices.selectRelated(questionAndUser);

        //转发到页面
        model.addAttribute("questionsByTags",questionsByTag );
        //浏览数增加
        questionServices.IncViewCount(id);
        model.addAttribute("questionAndUser", questionAndUser);
        //查找出问题所对应的评论
        List<CommentAndCreator> commentAndCreators = new ArrayList<>();
        commentAndCreators = commentMapper.selectByParentId(id);
        //当登陆用户和问题发布用户相同时，就已读,只有当用户登录了才会判断
        if (SessionUser!= null &&  SessionUser.getAccountId()==questionAndUser.getCreator()){
            notificationMapper.update(SessionUser.getAccountId(),id);
        }
        if (commentAndCreators != null) {
            //根据查找出来的评论，查找出评论者的头像，用户名
            List<User> users = new ArrayList<>();
            for (CommentAndCreator commentAndCreator : commentAndCreators) {
                User user = userMapper.selectByCreator(commentAndCreator.getCommentCreator());
                commentAndCreator.setUser(user);
            }
            System.out.println(commentAndCreators.size());
            model.addAttribute("commentAndCreators", commentAndCreators);
        }
        //当从回复页面跳转来时，将恢复状态status从o更新为1
        return "question";
    }
}
