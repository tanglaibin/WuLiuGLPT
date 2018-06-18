package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import tj.ljjtlb.utils.Page;
import tl.ljjtlb.dao.BaseDao;
import tl.ljjtlb.domain.Role;

public class RoleserviceImpl implements Roleservice {
	
private BaseDao basedao;
public void setBasedao(BaseDao basedao) {
	this.basedao = basedao;
}

@Override
public List<Role> find(String hql, Class<Role> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.find(hql, entityClass, params);
}
@Override
public Role get(Class<Role> entityClass, Serializable id) {
	// TODO Auto-generated method stub
	return basedao.get(entityClass, id);
}
@Override
public Page<Role> findPage(String hql, Page<Role> page, Class<Role> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.findPage(hql, page, entityClass, params);
}
@Override
public void saveOrUpdate(Role entity) {
	// 给主键赋值
	
	basedao.saveOrUpdate(entity);
}
@Override
public void saveOrUpdateAll(Collection<Role> entitys) {
	// TODO Auto-generated method stub
	basedao.saveOrUpdateAll(entitys);
}

@Override
public void deleteById(Class<Role> entityClass, Serializable id) {
	//将此id的用户状态设为0
	basedao.deleteById(entityClass, id);
	
}

@Override
public void deleteById2(Class<Role> entityClass, Serializable id) {
	//将此id的用户状态设为0
	basedao.saveOrUpdate(entityClass);

}

@Override
public void delete(Class<Role> entityClass, Serializable[] ids) {
	// TODO Auto-generated method stub
for (Serializable id : ids) {
	this.deleteById(Role.class, id);
}

}


}
