package com.mybatis.myboard.mapper;

import com.mybatis.myboard.dto.BoardPreferDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardPreferMapper {
    int insertOne(BoardPreferDto boardPrefer);
    int updateOne(BoardPreferDto boardPrefer);
    int deleteById(int boardPreferId);
    int countByBoardIdAndPreferIsTrue(int boardId);
    int countByBoardIdAndPreferIsFalse(int boardId);
    BoardPreferDto selectByBoardIdAndUserId(int boardId, String userId);
}
