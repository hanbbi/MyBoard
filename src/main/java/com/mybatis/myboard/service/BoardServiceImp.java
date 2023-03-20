package com.mybatis.myboard.service;

import com.mybatis.myboard.dto.BoardDto;
import com.mybatis.myboard.dto.BoardImgDto;
import com.mybatis.myboard.dto.PagingDto;
import com.mybatis.myboard.mapper.BoardImgMapper;
import com.mybatis.myboard.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImp implements BoardService {
    private BoardMapper boardMapper;
    private BoardImgMapper boardImgMapper;

    public BoardServiceImp(BoardMapper boardMapper, BoardImgMapper boardImgMapper) {
        this.boardMapper = boardMapper;
        this.boardImgMapper = boardImgMapper;
    }

    @Override
    public int register(BoardDto board) {
        int register = 0;
        register += boardMapper.insertOne(board);
        if (board.getBoardImgList() != null) {
            for (BoardImgDto boardImg : board.getBoardImgList()) {
                boardImg.setBoardId(board.getBoardId());
                register += boardImgMapper.insertOne(boardImg);
            }
        }
        return register;
    }

    @Override
    public int remove(int boardId) {
        return boardMapper.deleteById(boardId);
    }

    @Override
    public BoardDto detail(int boardId) {
        boardMapper.updateViews(boardId);
        return boardMapper.selectById(boardId);
    }

    @Override
    public List<BoardDto> listPaging(PagingDto paging) {
        int totalRows = boardMapper.countPaging(paging);
        paging.setTotalRows(totalRows);
        return boardMapper.listPaging(paging);
    }

    @Override
    public List<BoardImgDto> modify(BoardDto board, int[] delImgIds) {
        int modify = 0;
        List<BoardImgDto> delBoardImgList = new ArrayList<BoardImgDto>();
        if (delImgIds != null) {
            for (int delImgId : delImgIds) {
                BoardImgDto boardImg = boardImgMapper.selectById(delImgId);
                delBoardImgList.add(boardImg);
                boardImgMapper.deleteById(delImgId);
            }
        }

        if (board.getBoardImgList() != null) {
            for (BoardImgDto boardImg : board.getBoardImgList()) {
                boardImg.setBoardId(board.getBoardId());
                boardImgMapper.insertOne(boardImg);
            }
        }
        boardMapper.updateOne(board);
        return delBoardImgList;
    }
}
