package com.mybatis.myboard.service;

import com.mybatis.myboard.dto.PagingDto;
import com.mybatis.myboard.dto.ReplyDto;
import com.mybatis.myboard.mapper.ReplyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImp implements ReplyService {
    private ReplyMapper replyMapper;

    public ReplyServiceImp(ReplyMapper replyMapper) {
        this.replyMapper = replyMapper;
    }

    @Override
    public int register(ReplyDto reply) {
        return replyMapper.insertOne(reply);
    }

    @Override
    public int modify(ReplyDto reply) {
        return replyMapper.updateOne(reply);
    }

    @Override
    public int remove(int replyId) {
        return replyMapper.deleteById(replyId);
    }

    @Override
    public ReplyDto detail(int replyId) {
        return replyMapper.selectById(replyId);
    }

    @Override
    public List<ReplyDto> boardDetailList(int boardId, PagingDto paging) {
        int totalRows = replyMapper.countByBoardId(boardId, paging);
        paging.setTotalRows(totalRows);
        return replyMapper.listByBoardIdPaging(boardId, paging);
    }

    @Override
    public List<ReplyDto> userDetailList(String userId, PagingDto paging) {
        int totalRows = replyMapper.countByUserId(userId, paging);
        paging.setTotalRows(totalRows);
        return replyMapper.listByUserIdPaging(userId, paging);
    }
}
