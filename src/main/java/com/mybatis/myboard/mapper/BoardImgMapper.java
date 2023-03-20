package com.mybatis.myboard.mapper;

import com.mybatis.myboard.dto.BoardImgDto;
import com.mybatis.myboard.dto.PagingDto;
import com.mybatis.myboard.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardImgMapper {
    int insertOne(BoardImgDto boardImg);
    int deleteById(int boardImgId);
    BoardImgDto selectById(int boardImgId);
    List<BoardImgDto> listByBoardId(int boardId);
}
