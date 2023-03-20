package com.mybatis.myboard.mapper;

import com.mybatis.myboard.dto.ReplyPreferDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyPreferMapper {
    int insertOne(ReplyPreferDto replyPrefer);
    int updateOne(ReplyPreferDto replyPrefer);
    int deleteById(int replyPreferId);
    int countByReplyIdAndPreferIsTrue(int replyId);
    int countByReplyIdAndPreferIsFalse(int replyId);
    ReplyPreferDto selectByReplyIdAndUserId(int replyId, String userId);
}
