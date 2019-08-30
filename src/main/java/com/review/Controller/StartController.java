package com.review.Controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.review.Dto.QuestionWithUser;
import com.review.Entity.User;
import com.review.Mapper.NotificationMapper;
import com.review.Mapper.UserMapper;
import com.review.Repository.UserRepository;
import com.review.Service.QuestionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class StartController {
    @Autowired
    UserRepository userRepository;
    //    使用mybatis查询tooken
    @Autowired
    UserMapper userMapper;
    //通过questionServices查到所有的question以及对应的创建者user
    @Autowired
    QuestionServices questionServices;
    @Autowired
    NotificationMapper notificationMapper;

    @GetMapping("/")
    public String start(HttpServletRequest request, Model model,
                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize", defaultValue = "3") int pageSize
    ) {

        PageHelper.startPage(pageNum, 7);
        List<QuestionWithUser> list = questionServices.list();

        PageInfo<QuestionWithUser> pageInfo = new PageInfo<>(list);
//        model.addAttribute("questions", list);
        model.addAttribute("pageInfos", pageInfo);
        model.addAttribute("pageNums", pageNum);
        //查询未读回复，转发到页面，首先获取登录用户
        User user;
        user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            int i = notificationMapper.SelectUnReadCount(user.getAccountId());
            model.addAttribute("count",i);

        }

//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("tooken")) {
//                    String tooken = cookie.getValue();
//                    User user = userMapper.select(tooken);
//                    if (user != null) {
//                        request.getSession().setAttribute("user", user);
//                    }
//                    break;
//                }
//            }
//        }
        return "index";
    }
}
