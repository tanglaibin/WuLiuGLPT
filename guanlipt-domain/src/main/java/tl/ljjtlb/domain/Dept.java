package tl.ljjtlb.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Dept implements Serializable{
/**
 *  DEPT_ID              varchar2(40)                    not null,
   DEPT_NAME            varchar2(50),
   PARENT_ID            varchar2(40),
   STATE                NUMBER(6,0),
 */
	private String id;
	private Set<User> users=new HashSet<>();
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	private String dept_name;
	private Dept deptparent;
	private Integer state;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public Dept getDeptparent() {
		return deptparent;
	}
	public void setDeptparent(Dept deptparent) {
		this.deptparent = deptparent;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
