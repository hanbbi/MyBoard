package com.mybatis.myboard.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserDto {
    private String userId;
    private String userName;
    private String userPw;
    private String userPhone;
    private String userEmail;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date userBirth;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date regDate;
}
