package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import tj.ljjtlb.utils.Encrypt;
import tj.ljjtlb.utils.MailUtil;
import tj.ljjtlb.utils.Page;
import tj.ljjtlb.utils.SysConstant;
import tj.ljjtlb.utils.UtilFuns;
import tl.ljjtlb.dao.BaseDao;
import tl.ljjtlb.domain.Contract;

public class ContractserviceImpl implements Contractservice {
	
private BaseDao basedao;
public void setBasedao(BaseDao basedao) {
	this.basedao = basedao;
}

@Override
public List<Contract> find(String hql, Class<Contract> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.find(hql, entityClass, params);
}
@Override
public Contract get(Class<Contract> entityClass, Serializable id) {
	// TODO Auto-generated method stub
	return basedao.get(entityClass, id);
}
@Override
public Page<Contract> findPage(String hql, Page<Contract> page, Class<Contract> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.findPage(hql, page, entityClass, params);
}
@Override
public void saveOrUpdate(Contract entity) {
	// 给主键赋值
	if (UtilFuns.isEmpty(entity.getId())) {
		entity.setTotalAmount(0d);
		entity.setState(0);
		entity.setHuowucount(0);
		entity.setFujiancount(0);
	}
	basedao.saveOrUpdate(entity);
}
@Override
public void saveOrUpdateAll(Collection<Contract> entitys) {
	// TODO Auto-generated method stub
	basedao.saveOrUpdateAll(entitys);
}

@Override
public void deleteById(Class<Contract> entityClass, Serializable id) {
	//将此id的用户状态设为0
	basedao.deleteById(entityClass, id);
}

@Override
public void deleteById2(Class<Contract> entityClass, Serializable id) {
	//将此id的用户状态设为0
	basedao.saveOrUpdate(entityClass);
}

@Override
public void delete(Class<Contract> entityClass, Serializable[] ids) {
	// TODO Auto-generated method stub
for (Serializable id : ids) {
	this.deleteById(Contract.class, id);
}

}

@Override
public void submit(String[] ids, int i) {
	// TODO Auto-generated method stub
	for (String id : ids) {
		Contract contract=basedao.get(Contract.class, id);
		contract.setState(i);
		basedao.saveOrUpdate(contract);
	}
}


}
