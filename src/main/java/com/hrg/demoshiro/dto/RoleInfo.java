package com.hrg.demoshiro.dto;

import java.util.ArrayList;
import java.util.List;

import com.hrg.demoshiro.bean.Permission;
import com.hrg.demoshiro.bean.Role;

public class RoleInfo extends Role {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Permission> permissions = new ArrayList<Permission>();

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermission(List<Permission> permissions) {
		this.permissions = permissions;
	}

	
	

}
