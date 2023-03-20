package com.mybatis.myboard.mapper;

import com.mybatis.myboard.dto.PagingDto;
import com.mybatis.myboard.dto.ReplyDto;
import com.mybatis.myboard.dto.ReplyPreferViewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    int insertOne(ReplyDto reply);
    int updateOne(ReplyDto reply);
    int deleteById(int replyId);
    int countByBoardId(int boardId, PagingDto paging);
    int countByUserId(String userId, PagingDto paging);
    ReplyDto selectById(int replyId);
    ReplyPreferViewDto countPreferByReplyId(int replyId);
    List<ReplyDto> listByBoardId(int boardId);
    List<ReplyDto> listByFkReplyId(int replyId);
    List<ReplyDto> listByBoardIdPaging(int boardId, PagingDto paging);
    List<ReplyDto> listByUserIdPaging(String userId, PagingDto paging);
    List<ReplyDto> listAll();
}
