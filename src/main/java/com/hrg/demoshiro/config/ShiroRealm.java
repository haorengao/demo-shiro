package com.hrg.demoshiro.config;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hrg.demoshiro.bean.Permission;
import com.hrg.demoshiro.dto.RoleInfo;
import com.hrg.demoshiro.dto.UserInfo;
import com.hrg.demoshiro.mapper.UserMapper;
import com.hrg.demoshiro.util.Constant;

public class ShiroRealm extends AuthorizingRealm {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserMapper userMapper;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		 logger.info("权限配置----->ShiroRealm.doGetAuthorizationInfo()");
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		UserInfo userInfo  = (UserInfo)principals.getPrimaryPrincipal();
	
		RoleInfo roleInfo = userInfo.getRole();
	
		authorizationInfo.addRole(roleInfo.getRoleCode());
        for(Permission p:roleInfo.getPermissions()){
            authorizationInfo.addStringPermission(p.getPermissionCode());
        }			

        return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		logger.info("ShiroRealm.doGetAuthenticationInfo()");

        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        
        UserInfo userInfo = userMapper.findUserInfo(username);

        logger.info("----->userInfo=" + userInfo);
        if(userInfo == null){
            throw new AccountException();
        }else if(userInfo.getState() == 0){
            throw new DisabledAccountException();
        }else if(userInfo.getState() == 2){
            throw new LockedAccountException();
        }

        //保存登录用户ID
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(Constant.LOGIN_USER_ID, userInfo.getId());

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户信息
                userInfo.getPassWord(), //密码
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );

        return authenticationInfo;
	}

}
