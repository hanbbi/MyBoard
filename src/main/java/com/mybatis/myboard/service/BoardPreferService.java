package com.mybatis.myboard.service;

import com.mybatis.myboard.dto.BoardPreferDto;
import com.mybatis.myboard.dto.BoardPreferViewDto;

public interface BoardPreferService {
    int register(BoardPreferDto boardPrefer);
    int modify(BoardPreferDto boardPrefer);
    int remove(int boardPreferId);
    BoardPreferDto detail(int boardId, String loginUserId);
    BoardPreferViewDto detailBoardPreferView(int boardId, String loginUserId);
}
