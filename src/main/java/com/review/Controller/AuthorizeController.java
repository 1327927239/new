package com.review.Controller;

import com.review.Dto.AccessTokenDto;
import com.review.Dto.GithubUser;
import com.review.Entity.User;
import com.review.Mapper.UserMapper;
import com.review.Provider.GithubProvider;
import com.review.Repository.UserRepository;
import com.review.Service.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@Slf4j
public class AuthorizeController {
    @Autowired
    GithubProvider githubProvider;
//    调用UserRepository接口，使用save方法来保存
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserServices userServices;
    @Value("${github.Client_id}")
    private String Client_id;
    @Value("${github.Client_secret}")
    private String Client_secret;
    @Value("${github.Redirect_uri}")
    private String Redirect_uri;

    @GetMapping("/callback")
    public String callback(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "state") String state,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id(Client_id);
        accessTokenDto.setClient_secret(Client_secret);
        accessTokenDto.setRedirect_uri(Redirect_uri);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
//        创建user对象并赋值
        if (githubUser!=null){
            User  user = new User();
            String tooken =UUID.randomUUID().toString();
            user.setTooken(tooken);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userServices.CreateOrUpdate(user);
            System.out.println(githubUser.getName());
//            使用Cookie来验证用户是否在数据库中有Tooken
            response.addCookie(new Cookie("tooken",tooken));
//            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }
        else {
            //调用github失败时，写入日志
            log.error("callback git fail {}",githubUser);
            return "redirect:/";
        }
    }
}
