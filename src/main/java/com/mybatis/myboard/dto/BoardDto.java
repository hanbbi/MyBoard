package com.mybatis.myboard.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BoardDto {
    private int boardId;
    private String title;
    private String contents;
    private Date regDate;
    private int views;
    private String userId;
    private UserDto user;
    private BoardPreferViewDto boardPreferView;
    private List<ReplyDto> replyList;
    private List<BoardImgDto> boardImgList;
}
