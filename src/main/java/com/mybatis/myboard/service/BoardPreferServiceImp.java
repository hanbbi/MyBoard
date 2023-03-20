package com.mybatis.myboard.service;

import com.mybatis.myboard.dto.BoardPreferDto;
import com.mybatis.myboard.dto.BoardPreferViewDto;
import com.mybatis.myboard.mapper.BoardMapper;
import com.mybatis.myboard.mapper.BoardPreferMapper;
import com.mybatis.myboard.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardPreferServiceImp implements BoardPreferService {
    private BoardMapper boardMapper;
    private BoardPreferMapper boardPreferMapper;
    private UserMapper userMapper;

    public BoardPreferServiceImp(BoardMapper boardMapper, BoardPreferMapper boardPreferMapper, UserMapper userMapper) {
        this.boardMapper = boardMapper;
        this.boardPreferMapper = boardPreferMapper;
        this.userMapper = userMapper;
    }

    @Override
    public int register(BoardPreferDto boardPrefer) {
        return boardPreferMapper.insertOne(boardPrefer);
    }

    @Override
    public int modify(BoardPreferDto boardPrefer) {
        return boardPreferMapper.updateOne(boardPrefer);
    }

    @Override
    public int remove(int boardPreferId) {
        return boardPreferMapper.deleteById(boardPreferId);
    }

    @Override
    public BoardPreferDto detail(int boardId, String loginUserId) {
        BoardPreferDto detail = boardPreferMapper.selectByBoardIdAndUserId(boardId, loginUserId);
        return detail;
    }
    @Transactional
    @Override
    public BoardPreferViewDto detailBoardPreferView(int boardId, String loginUserId) {
        BoardPreferViewDto detailBoardPreferView;
        detailBoardPreferView =boardMapper.countPreferByBoardId(boardId);
        if (loginUserId != null) {
            BoardPreferDto loginUserPrefer = boardPreferMapper.selectByBoardIdAndUserId(boardId, loginUserId);
            detailBoardPreferView.setLoginUserPrefer(loginUserPrefer);
        }
        return detailBoardPreferView;
    }
}
