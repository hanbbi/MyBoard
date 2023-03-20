package com.mybatis.myboard.dto;

import lombok.Data;

@Data
public class BoardPreferDto {
    private int boardPreferId;
    private int boardId;
    private boolean prefer;
    private String userId;
}
