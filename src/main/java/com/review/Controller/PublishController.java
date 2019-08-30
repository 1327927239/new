package com.review.Controller;

import com.review.Dto.QuestionWithUser;
import com.review.Entity.Question;
import com.review.Entity.User;
import com.review.Mapper.QuestionMapper;
import com.review.Mapper.UserMapper;
import com.review.Repository.QuestionRepository;
import com.review.Service.QuestionServices;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PublishController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionServices questionServices;
    @Autowired
    QuestionMapper questionMapper;

    //修改自己发布的问题
    @GetMapping("/publish/{id}")
    public String eidtQuestion(
            @PathVariable(value = "id") Integer id,
             Model model) {
        Question question = questionMapper.selectById(id);
        model.addAttribute("title", question.getTitle());
        System.out.println("///////////");
        System.out.println(id);
        System.out.println(question.getDescription());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", id);
        return "publish";
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Model model,
                            @RequestParam("id") Integer id,
                            @RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request, HttpServletResponse response
    ) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null || title == "") {
            model.addAttribute("error", "标题为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签为空");
            return "publish";
        }

        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("tooken")) {
                    String tooken = cookie.getValue();
                    user = userMapper.select(tooken);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }


        if (user == null) {
            model.addAttribute("error", "未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setId(id);

        question.setCreator(user.getAccountId());
        System.out.println("////////////////");
        System.out.println("dsa ");
        System.out.println(question.getId());
        questionServices.CreateOrUpdate(question);
//        questionRepository.save(question);
        return "redirect:/";
    }
}
