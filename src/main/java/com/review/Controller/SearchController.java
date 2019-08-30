package com.review.Controller;

import com.github.pagehelper.PageHelper;
import com.review.Dto.QuestionWithUser;
import com.review.Entity.Question;
import com.review.Entity.User;
import com.review.Mapper.QuestionMapper;
import com.review.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @GetMapping("/search")
    public String doSearch(Model model, @RequestParam(name = "searches",required = false)String searches,
                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "3") int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<QuestionWithUser> questionWithUsers = questionMapper.selectByDescription(searches);
        for (QuestionWithUser questionWithUser : questionWithUsers) {
            User user = userMapper.selectByCreator(questionWithUser.getCreator());
            questionWithUser.setUser(user);
        }
        if (questionWithUsers!=null){
            model.addAttribute("questions",questionWithUsers);
            model.addAttribute("pageNum",pageNum);
        }
        return "showSearch";
    }
}
