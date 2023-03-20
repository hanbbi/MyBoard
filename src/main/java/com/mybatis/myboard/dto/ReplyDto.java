package com.mybatis.myboard.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReplyDto {
    private int replyId;
    private String title;
    private String contents;
    private Date regDate;
    private String imgPath;
    private int boardId;
    private String userId;
    private Integer fkReplyId;
    private UserDto user;
    private ReplyPreferViewDto replyPreferView;
    private List<ReplyDto> replyList;
}
