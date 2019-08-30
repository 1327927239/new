package com.review.Controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.review.Entity.Notification;
import com.review.Entity.Question;
import com.review.Entity.User;
import com.review.Mapper.NotificationMapper;
import com.review.Mapper.QuestionMapper;
import com.review.Mapper.UserMapper;
import com.review.Repository.NotificationRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    NotificationRepositiry notificationRepositiry;
    @Autowired
    NotificationMapper notificationMapper;


    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request, Model model,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @PathVariable(value = "action") String action) {
        //获取session中的user对象
        User SessionUser =(User) request.getSession().getAttribute("user");

        Cookie[] cookies = request.getCookies();
        String tooken = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("tooken")) {
                    tooken = cookie.getValue();
                }
                break;
            }
        }
        //以下几行代码为在查询我发布的问题的时候，无法查找到我的问题，将user改变为SessionUser
//        User user = null;
//        String creator = null;
//        if (tooken != null) {
//            user = userMapper.selectBytooken(tooken);
//            creator = user.getAccountId();
//        }
        if (SessionUser != null) {
            PageHelper.startPage(pageNum, 3);
            List<Question> questions = questionMapper.selectByCreator(SessionUser.getAccountId());
            PageInfo<Question> pageInfo = new PageInfo<>(questions);
            model.addAttribute("pageInfos", pageInfo);
            model.addAttribute("pageNum", pageNum);
        }

        if ("question".equals(action)) {
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的提问");

        } else if ("replies".equals(action)) {

            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我的回复");
            //当用户点击了回复按钮时，将未读的消息状态更新为已读
//            notificationMapper.update(SessionUser.getAccountId());
            //查询未读回复
            List<Notification> notifications = notificationMapper.selectByReceiver(Integer.valueOf(SessionUser.getAccountId()));
            //查询已读的回复
            List<Notification> IsReadnotifications = notificationMapper.selectByReceiverIsRead(Integer.valueOf(SessionUser.getAccountId()));
            //如果查询出来的已读回复，或者未读回复不为空，才转发到页面
                model.addAttribute("notifications",notifications );
                model.addAttribute("IsReadnotifications",IsReadnotifications );
            request.getSession().setAttribute("repliesCount",notifications.size());
        }

        return "profile";
    }
}
