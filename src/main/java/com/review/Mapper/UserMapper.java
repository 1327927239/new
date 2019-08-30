package com.review.Mapper;

import com.review.Entity.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {
    User select(@Param("tooken") String tooken);

    User selectByCreator(String creator);

    @Select("select * from user_review where tooken = #{tooken}")
    User selectBytooken(String tooken);

    @Insert("INSERT INTO user_review ( account_id, avatar_url, gmt_create,gmt_modified,name,tooken)\n" +
            "                       VALUES\n" +
            "                       ( #{accountId}, #{avatarUrl},#{gmtCreate},#{gmtModified},#{name},#{tooken});")
    void insert(User user);

    @Update("UPDATE user_review SET avatar_url=#{avatarUrl}, gmt_modified=#{gmtModified},name=#{name},tooken=#{tooken} where account_id = #{accountId}")
    void update(User user);
    //根据评论表里面的commentCreatorID查找对应的用户名
    @Select("select name from user_review where account_id= #{CommentCreator}")
    String selectByCommentCreator(String CommentCreator);
}
