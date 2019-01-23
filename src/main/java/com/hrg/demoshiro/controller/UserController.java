package com.hrg.demoshiro.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrg.demoshiro.bean.User;
import com.hrg.demoshiro.dto.UserInfo;
import com.hrg.demoshiro.mapper.UserMapper;
import com.hrg.demoshiro.util.PasswordEncoder;

@RestController
@RequestMapping("/UserController")
public class UserController {
    
    @Autowired
    private UserMapper userMapper;
    
    @GetMapping("/list")
    public List<User> list(){
        List<User> students = userMapper.selectList(null);
        return students;
    }
    
    @GetMapping("/userList")
    public IPage<User> userList(Integer page, Integer size){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("user_name", "test");
    	Page<?> pageObj = new Page<Object>(page,size);
        IPage<User> userInfos = userMapper.selectPageVo(pageObj, 1);
        return userInfos;
    }
 
 
    @GetMapping("/login")
	public String login(String userName, String psw) {
		
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken(userName, psw);
		subject.login(token);
		
		//Session session = SecurityUtils.getSubject().getSession();
		
		
		
		return "成功";
	}
    
    
    @GetMapping("/addUser")
    public String addUser(String userName, String psw) {
    	
    	User user = new User();
    	
    	Map<String, String> map = PasswordEncoder.enCodePassWord(userName, psw);
    	
    	user.setUserName(userName);
    	user.setPassWord(map.get(PasswordEncoder.PASSWORD));
    	user.setSalt(map.get(PasswordEncoder.SALT));
    	
    	userMapper.insert(user);
    	
    	return "用户创建成功";
    }
 
}
