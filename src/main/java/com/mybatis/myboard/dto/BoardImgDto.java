package com.mybatis.myboard.dto;

import lombok.Data;

@Data
public class BoardImgDto {
    private int boardImgId;
    private int boardId;
    private String imgPath;
}
