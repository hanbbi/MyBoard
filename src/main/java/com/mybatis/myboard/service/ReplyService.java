package com.mybatis.myboard.service;

import com.mybatis.myboard.dto.PagingDto;
import com.mybatis.myboard.dto.ReplyDto;

import java.util.List;

public interface ReplyService {
    int register(ReplyDto reply);
    int modify(ReplyDto reply);
    int remove(int replyId);
    ReplyDto detail(int replyId);
    List<ReplyDto> boardDetailList(int boardId, PagingDto paging);
    List<ReplyDto> userDetailList(String userId, PagingDto paging);
}
