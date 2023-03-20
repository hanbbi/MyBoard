package com.mybatis.myboard.dto;

import lombok.Data;

@Data
public class BoardPreferViewDto {
    private int likes;
    private int bads;
    private BoardPreferDto loginUserPrefer;
}
