package com.mybatis.myboard.dto;

import lombok.Data;

@Data
public class ReplyPreferViewDto {
    private int likes;
    private int bads;
    private ReplyPreferDto loginUserPrefer;
}
