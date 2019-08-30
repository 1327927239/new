package com.review.Mapper;

import com.review.Dto.QuestionWithUser;
import com.review.Entity.Question;
import com.review.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Select("select * from question order by gmt_create Desc")
    List<Question> selectAll();
    @Select("select * from question where creator = #{creator}")
    List<Question> selectByCreator(String creator);
    @Select("select * from question where id = #{id}")
    Question selectById(Integer id);
    @Update("UPDATE question SET title=#{title}, description=#{description},tag=#{tag},gmt_modified=#{gmtModified} where id = #{id}")
    void update(Question question);
    @Update("UPDATE question SET view_count=#{viewCount} where id = #{id}")
    void updateViewCount(Question question);
    @Select("select view_count from question where id = #{id} ")
    Integer selectViewCountById(Integer id);
    @Update("UPDATE question SET comment_count=#{commentCount} where id = #{id}")
    void updateCommentCount(Question question);

    @Select("select * from question where id !=#{id} and tag regexp #{tag}")
    List<Question> selectByRegexpTag(Question question);

    @Select("select creator,title from question where id = #{id}")
    Question SelectCreator(int id);
    @Select("select * from question where title=#{searches}")
    List<QuestionWithUser> selectByDescription(String searches);
}
