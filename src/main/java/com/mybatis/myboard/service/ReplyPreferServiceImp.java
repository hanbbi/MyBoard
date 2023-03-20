package com.mybatis.myboard.service;

import com.mybatis.myboard.dto.ReplyPreferDto;
import com.mybatis.myboard.dto.ReplyPreferViewDto;
import com.mybatis.myboard.mapper.ReplyMapper;
import com.mybatis.myboard.mapper.ReplyPreferMapper;
import com.mybatis.myboard.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyPreferServiceImp implements ReplyPreferService {
    private ReplyMapper replyMapper;
    private ReplyPreferMapper replyPreferMapper;
    private UserMapper userMapper;

    public ReplyPreferServiceImp(ReplyMapper replyMapper, ReplyPreferMapper replyPreferMapper, UserMapper userMapper) {
        this.replyMapper = replyMapper;
        this.replyPreferMapper = replyPreferMapper;
        this.userMapper = userMapper;
    }

    @Override
    public int register(ReplyPreferDto replyPrefer) {
        return replyPreferMapper.insertOne(replyPrefer);
    }

    @Override
    public int modify(ReplyPreferDto replyPrefer) {
        return replyPreferMapper.updateOne(replyPrefer);
    }

    @Override
    public int remove(int replyPreferId) {
        return replyPreferMapper.deleteById(replyPreferId);
    }

    @Override
    public ReplyPreferDto detail(int replyId, String loginUserId) {
        ReplyPreferDto detail = replyPreferMapper.selectByReplyIdAndUserId(replyId, loginUserId);
        return detail;
    }

    @Transactional
    @Override
    public ReplyPreferViewDto detailReplyPreferView(int replyId, String loginUserId) {
        ReplyPreferViewDto detailReplyPreferView;
        detailReplyPreferView = replyMapper.countPreferByReplyId(replyId);
        if (loginUserId != null) {
            ReplyPreferDto loginUserPrefer = replyPreferMapper.selectByReplyIdAndUserId(replyId, loginUserId);
            detailReplyPreferView.setLoginUserPrefer(loginUserPrefer);
        }
        return detailReplyPreferView;
    }
}
