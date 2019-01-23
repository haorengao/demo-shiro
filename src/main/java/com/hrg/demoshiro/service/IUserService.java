package com.hrg.demoshiro.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hrg.demoshiro.bean.User;
import com.hrg.demoshiro.dto.UserInfo;


public interface IUserService extends IService<User> {

    UserInfo findUserInfo(String userName);
    
    IPage<User> selectPageVo(Page page, int state);

}
