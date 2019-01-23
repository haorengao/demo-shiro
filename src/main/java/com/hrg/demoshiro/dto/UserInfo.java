package com.hrg.demoshiro.dto;

import com.hrg.demoshiro.bean.User;

public class UserInfo extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RoleInfo role = new RoleInfo();

	public RoleInfo getRole() {
		return role;
	}

	public void setRoles(RoleInfo role) {
		this.role = role;
	}
	
	 /**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
        return this.getUserName() + this.getSalt();
    }

	
	
	

}
