package com.mybatis.myboard.service;

import com.mybatis.myboard.dto.BoardDto;
import com.mybatis.myboard.dto.BoardImgDto;
import com.mybatis.myboard.dto.PagingDto;

import java.util.List;

public interface BoardService {
    int register(BoardDto board);
    int remove(int boardId);
    BoardDto detail(int boardId);
    List<BoardDto> listPaging(PagingDto paging);
    List<BoardImgDto> modify(BoardDto board, int[] delImgIds);
}
