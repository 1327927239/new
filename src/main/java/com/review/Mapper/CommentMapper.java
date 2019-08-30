package com.review.Mapper;

import com.review.Dto.CommentAndCreator;
import com.review.Entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("select * from comment where parent_id = #{id}")
    List<CommentAndCreator> selectByParentId(Integer id);
}
