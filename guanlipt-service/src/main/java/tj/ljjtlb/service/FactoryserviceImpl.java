package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import tj.ljjtlb.utils.Page;
import tl.ljjtlb.dao.BaseDao;
import tl.ljjtlb.domain.Factory;

public class FactoryserviceImpl implements Factoryservice {
	
private BaseDao basedao;
public void setBasedao(BaseDao basedao) {
	this.basedao = basedao;
}

@Override
public List<Factory> find(String hql, Class<Factory> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.find(hql, entityClass, params);
}
@Override
public Factory get(Class<Factory> entityClass, Serializable id) {
	// TODO Auto-generated method stub
	return basedao.get(entityClass, id);
}
@Override
public Page<Factory> findPage(String hql, Page<Factory> page, Class<Factory> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.findPage(hql, page, entityClass, params);
}
@Override
public void saveOrUpdate(Factory entity) {
	// 给主键赋值
	basedao.saveOrUpdate(entity);
}
@Override
public void saveOrUpdateAll(Collection<Factory> entitys) {
	// TODO Auto-generated method stub
	basedao.saveOrUpdateAll(entitys);
}

@Override
public void deleteById(Class<Factory> entityClass, Serializable id) {
	//将此id的用户状态设为0
	basedao.deleteById(entityClass, id);
}

@Override
public void deleteById2(Class<Factory> entityClass, Serializable id) {
	//将此id的用户状态设为0
	basedao.saveOrUpdate(entityClass);
}

@Override
public void delete(Class<Factory> entityClass, Serializable[] ids) {
	// TODO Auto-generated method stub
for (Serializable id : ids) {
	this.deleteById(Factory.class, id);
}

}


}
