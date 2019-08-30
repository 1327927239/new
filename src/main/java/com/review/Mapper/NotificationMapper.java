package com.review.Mapper;

import com.review.Entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Select("select * from notification where receiver = #{receiver} and status=0 order by gmt_create Desc")
    List<Notification> selectByReceiver(Integer receiver);
    //查找未读的回复数，也就是status为0的回复,通过user的accountid查询
    @Select("SELECT COUNT(status) FROM notification where status = 0 and receiver = #{receiver}")
    int SelectUnReadCount(String accountid);
    @Update("UPDATE notification SET status = 1 WHERE receiver = #{accountId} and outerid = #{id} ")
    void update(@Param("accountId")String accountId,@Param("id") Integer id);
    //查找已读的回复数，也就是status为1的回复,通过user的accountid查询
    @Select("SELECT COUNT(status) FROM notification where status = 1 and receiver = #{receiver}")
    int SelectReadCount(String accountid);
    @Select("select * from notification where receiver = #{receiver} and status=1 order by gmt_create Desc")
    List<Notification> selectByReceiverIsRead(Integer receiver);
}
