package com.mybatis.myboard.mapper;

import com.mybatis.myboard.dto.PagingDto;

import java.util.List;

public interface CRUD<T,P> {
    int insertOne(T dto);
    int updateOne(T dto);
    int deleteById(P id);
    int countPaging(PagingDto paging);
    T selectById(P id);
    List<T> listPaging(PagingDto paging);
    List<T> listAll();
}
