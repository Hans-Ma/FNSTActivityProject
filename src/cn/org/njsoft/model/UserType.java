package cn.org.njsoft.model;

import java.util.Set;
/**
 * 2015/12/6
 * UserType表的model类
 * @see cn.org.njsoft.model#UserType
 * @author JAQ
 *
 */
public class UserType {
	private int typeId;//用户类型ID
	private String typeName;//用户类型名称	
	private Set<User> user;//one这头设置集合,与User表外键关联
	
	public int getTypeId() {//get方法
		return typeId;
	}
	public void setTypeId(int typeId) {//set方法
		this.typeId = typeId;
	}
	public String getTypeName() {//get方法
		return typeName;
	}
	public void setTypeName(String typeName) {//set方法
		this.typeName = typeName;
	}
	public Set<User> getUser() {//get方法
		return user;
	}
	public void setUser(Set<User> user) {//set方法
		this.user = user;
	}		
}
