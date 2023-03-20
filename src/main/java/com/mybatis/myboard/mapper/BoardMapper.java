package com.mybatis.myboard.mapper;

import com.mybatis.myboard.dto.BoardDto;
import com.mybatis.myboard.dto.BoardPreferViewDto;
import com.mybatis.myboard.dto.PagingDto;
import com.mybatis.myboard.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper extends CRUD<BoardDto, Integer> {
    int insertOne(BoardDto board);
    int updateOne(BoardDto board);
    int updateViews(int boardId);
    int deleteById(Integer boardId);
    int countPaging(PagingDto paging);
    BoardDto selectById(Integer boardId);
    BoardPreferViewDto countPreferByBoardId(int boardId);
    List<BoardDto> listPaging(PagingDto paging);
    List<BoardDto> listAll();
}
