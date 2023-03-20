package com.mybatis.myboard.service;

import com.mybatis.myboard.dto.PagingDto;
import com.mybatis.myboard.dto.UserDto;
import com.mybatis.myboard.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private UserMapper userMapper;

    public UserServiceImp(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int signup(UserDto user) {
        return userMapper.insertOne(user);
    }

    @Override
    public int modify(UserDto user) {
        return userMapper.updateOne(user);
    }

    @Override
    public int idCheck(String userId) {
        if (userMapper.selectById(userId) == null) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int remove(String userId) {
        return userMapper.deleteById(userId);
    }

    @Override
    public UserDto login(String userId, String userPw) {
        return userMapper.selectByUserIdAndUserPw(userId, userPw);
    }

    @Override
    public UserDto detail(String userId) {
        UserDto detail = userMapper.selectById(userId);
        return detail;
    }

    @Override
    public List<UserDto> listPaging(PagingDto paging) {
        int totalRows = userMapper.countPaging(paging);
        paging.setTotalRows(totalRows);
        List<UserDto> userList = userMapper.listPaging(paging);
        return userList;
    }

    @Override
    public List<UserDto> listAllTest() {
        return userMapper.listAll();
    }
}
