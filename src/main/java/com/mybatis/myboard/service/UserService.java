package com.mybatis.myboard.service;

import com.mybatis.myboard.dto.PagingDto;
import com.mybatis.myboard.dto.UserDto;

import java.util.List;

public interface UserService {
    int signup(UserDto user);
    int modify(UserDto user);
    int idCheck(String userId);
    int remove(String userId);
    UserDto login(String userId, String userPw);
    UserDto detail(String userId);
    List<UserDto> listPaging(PagingDto paging);
    List<UserDto> listAllTest();
}
