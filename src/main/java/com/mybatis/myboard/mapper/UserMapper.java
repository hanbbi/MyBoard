package com.mybatis.myboard.mapper;

import com.mybatis.myboard.dto.BoardDto;
import com.mybatis.myboard.dto.PagingDto;
import com.mybatis.myboard.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends CRUD<UserDto, Integer> {
    int insertOne(UserDto user);
    int updateOne(UserDto user);
    int deleteById(String userId);
    int countPaging(PagingDto paging);
    UserDto selectById(String userId);
    UserDto selectByUserIdAndUserPw(String userId, String userPw);
    List<UserDto> listPaging(PagingDto paging);
    List<UserDto> listAll();
}
