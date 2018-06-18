package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import tj.ljjtlb.utils.Page;
import tj.ljjtlb.utils.UtilFuns;
import tl.ljjtlb.dao.BaseDao;
import tl.ljjtlb.domain.Dept;

public class DeptserviceImpl implements Deptservice {
	
private BaseDao basedao;
public void setBasedao(BaseDao basedao) {
	this.basedao = basedao;
}

@Override
public List<Dept> find(String hql, Class<Dept> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.find(hql, entityClass, params);
}
@Override
public Dept get(Class<Dept> entityClass, Serializable id) {
	// TODO Auto-generated method stub
	return basedao.get(entityClass, id);
}
@Override
public Page<Dept> findPage(String hql, Page<Dept> page, Class<Dept> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.findPage(hql, page, entityClass, params);
}
@Override
public void saveOrUpdate(Dept entity) {
	// TODO Auto-generated method stub
	if (UtilFuns.isEmpty(entity.getId())) {
		entity.setState(1);
	}
	basedao.saveOrUpdate(entity);
}
@Override
public void saveOrUpdateAll(Collection<Dept> entitys) {
	// TODO Auto-generated method stub
	basedao.saveOrUpdateAll(entitys);
}

@Override
public void deleteById(Class<Dept> entityClass, Serializable id) {
	String hql="from Dept where deptparent.id=?";
	List<Dept> list = basedao.find(hql, Dept.class, new Object[]{id});
	if (list!=null && list.size()>0) {
		for(Dept dept : list) {
			deleteById(Dept.class,dept.getId());
		}
	}
	basedao.deleteById(entityClass, id);
}

@Override
public void delete(Class<Dept> entityClass, Serializable[] ids) {
	// TODO Auto-generated method stub
for (Serializable id : ids) {
	this.deleteById(Dept.class, id);
}


}






}
