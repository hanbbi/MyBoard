package com.mybatis.myboard.dto;

import lombok.Data;

@Data
public class ReplyPreferDto {
    private int replyPreferId;
    private int replyId;
    private boolean prefer;
    private String userId;
}
