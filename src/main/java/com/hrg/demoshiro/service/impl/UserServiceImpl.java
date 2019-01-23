package com.hrg.demoshiro.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrg.demoshiro.bean.User;
import com.hrg.demoshiro.dto.UserInfo;
import com.hrg.demoshiro.mapper.UserMapper;
import com.hrg.demoshiro.service.IUserService;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public UserInfo findUserInfo(String userName) {
        return this.baseMapper.findUserInfo(userName);
    }

	@Override
	public IPage<User> selectPageVo(Page page, int state) {

		return this.baseMapper.selectPageVo(page, state);
	}
}