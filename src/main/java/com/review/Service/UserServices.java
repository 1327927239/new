package com.review.Service;

import com.review.Entity.User;
import com.review.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    @Autowired
    UserMapper userMapper;
    public void CreateOrUpdate(User user) {
        User dbUser = userMapper.selectByCreator(user.getAccountId());
        if (dbUser ==null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            user.setGmtModified(System.currentTimeMillis());
            userMapper.update(user);
        }
    }
}
