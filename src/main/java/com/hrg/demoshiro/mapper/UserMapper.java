package com.hrg.demoshiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrg.demoshiro.bean.User;
import com.hrg.demoshiro.dto.UserInfo;

public interface UserMapper extends BaseMapper<User> {

	UserInfo findUserInfo(String userName);
	
	IPage<User> selectPageVo(Page page, int state);
}