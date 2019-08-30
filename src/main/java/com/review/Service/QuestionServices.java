package com.review.Service;

import com.review.Dto.QuestionWithUser;
import com.review.Entity.Question;
import com.review.Entity.User;
import com.review.Mapper.QuestionMapper;
import com.review.Mapper.UserMapper;
import com.review.Repository.QuestionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServices {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;
    public List<QuestionWithUser> list(){
        List<QuestionWithUser> list = new ArrayList<>();
        List<Question> questions = questionMapper.selectAll();
        for (Question question : questions) {
            User user = userMapper.selectByCreator(question.getCreator());
            QuestionWithUser questionWithUser = new QuestionWithUser();
            BeanUtils.copyProperties(question,questionWithUser);
            questionWithUser.setUser(user);
            list.add(questionWithUser);
        }
        return list;
    }


    public QuestionWithUser findById(Integer id) {

        QuestionWithUser questionWithUser = new QuestionWithUser();
        Question question = questionMapper.selectById(id);
        User user = userMapper.selectByCreator(question.getCreator());
        BeanUtils.copyProperties(question,questionWithUser);
        questionWithUser.setUser(user);
        return questionWithUser;
    }

    public void CreateOrUpdate(Question question) {
        Question question1 = questionMapper.selectById(question.getId());
        if (question1==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionRepository.save(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }

    public void IncViewCount(Integer id) {
        Question question = questionMapper.selectById(id);
        Integer viewCount = questionMapper.selectViewCountById(id);
        question.setViewCount(viewCount+1);
        questionMapper.updateViewCount(question);
    }

    public void addCommentCount(Integer parentId) {
        Question question = questionMapper.selectById(parentId);
        question.setCommentCount(question.getCommentCount()+1);
        questionMapper.updateCommentCount(question);

    }

    public List<Question> selectRelated(QuestionWithUser questionAndUser) {
        //判断tag是否为空，为空返回空
        if (StringUtils.isBlank(questionAndUser.getTag())){
            return new ArrayList<>();
        }
        //将tag以，分隔
        String[] tags = StringUtils.split(questionAndUser.getTag(), ',');
        //以|分隔
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionAndUser.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionMapper.selectByRegexpTag(question);
        return questions;
    }
}
