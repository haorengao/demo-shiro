package com.hrg.demoshiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * @Autowired private SecurityManager securityManager;
	 */

	/*
	 * @PostConstruct private void initStaticSecurityManager() {
	 * SecurityUtils.setSecurityManager(securityManager()); }
	 */

//	@Bean
//	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
//
//		logger.info("ShiroConfig.shiroFilterChainDefinition().....");
//
//		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//
//		// logged in users with the 'admin' role
//		/*
//		 * chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");
//		 * 
//		 * // logged in users with the 'document:read' permission
//		 * chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");
//		 */
//
//		// all other paths require a logged in user
//		chainDefinition.addPathDefinition("/**", "anon");
//		return chainDefinition;
//	}
	
	
	
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		logger.info("ShiroConfiguration.shirFilter()");

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		//获取filters
		Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
		//将自定义的FormAuthenticationFilter注入shiroFilter中（验证码校验）
//		filters.put("authc", new CustomFormAuthenticationFilter());

		//拦截器.
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/**", "anon");
//		filterChainDefinitionMap.put("/kaptcha/**", "anon");
//		//配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
//		filterChainDefinitionMap.put("/logout", "logout");
//		//<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
//		//<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//		filterChainDefinitionMap.put("/**", "authc");
//		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
//		shiroFilterFactoryBean.setLoginUrl("/login");
//		// 登录成功后要跳转的链接
//		shiroFilterFactoryBean.setSuccessUrl("/index");
//
//		//未授权界面;
//		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
	

	/**
	 * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
	 * 
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于 md5(md5(""));
		return hashedCredentialsMatcher;
	}

	/**
	 * 自定义身份认证realm
	 * 
	 * @return
	 */
	@Bean
	public ShiroRealm shiroRealm() {
		ShiroRealm shiroRealm = new ShiroRealm();
		shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return shiroRealm;
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
	@Bean
	public DefaultWebSecurityManager securityManager(){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		// 设置realm
		securityManager.setRealm(shiroRealm());
		/*
		 * // 自定义缓存实现 使用redis securityManager.setCacheManager(redisCacheManager()); //
		 * 自定义session管理 使用redis securityManager.setSessionManager(sessionManager());
		 */
		return securityManager;
	}


}
