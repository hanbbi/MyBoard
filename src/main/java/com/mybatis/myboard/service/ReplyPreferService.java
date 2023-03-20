package com.mybatis.myboard.service;

import com.mybatis.myboard.dto.ReplyPreferDto;
import com.mybatis.myboard.dto.ReplyPreferViewDto;

public interface ReplyPreferService {
    int register(ReplyPreferDto replyPrefer);
    int modify(ReplyPreferDto replyPrefer);
    int remove(int replyPreferId);
    ReplyPreferDto detail(int replyId, String loginUserId);
    ReplyPreferViewDto detailReplyPreferView(int replyId, String loginUserId);
}
