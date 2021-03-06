package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import tj.ljjtlb.utils.Page;
import tj.ljjtlb.utils.UtilFuns;
import tl.ljjtlb.dao.BaseDao;
import tl.ljjtlb.domain.Contract;
import tl.ljjtlb.domain.ContractProduct;
import tl.ljjtlb.domain.ExtCproduct;
import tl.ljjtlb.domain.Module;

public class ContractProductserviceImpl implements ContractProductservice {
	
private BaseDao basedao;
public void setBasedao(BaseDao basedao) {
	this.basedao = basedao;
}

@Override
public List<ContractProduct> find(String hql, Class<ContractProduct> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.find(hql, entityClass, params);
}
@Override
public ContractProduct get(Class<ContractProduct> entityClass, Serializable id) {
	// TODO Auto-generated method stub
	return basedao.get(entityClass, id);
}
@Override
public Page<ContractProduct> findPage(String hql, Page<ContractProduct> page, Class<ContractProduct> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.findPage(hql, page, entityClass, params);
}
@Override
public void saveOrUpdate(ContractProduct entity) {
	// 保存货物信息时  计算货物金额
	// 每新增货物   合同货物数+1
	Double m = 0d;
	if (UtilFuns.isEmpty(entity.getId())) {
		if (UtilFuns.isNotEmpty(entity.getCnumber()) && UtilFuns.isNotEmpty(entity.getPrice()) ) {
			m=entity.getCnumber()*entity.getPrice();
			entity.setAmount(m);
		}
		// 同步更新购销合同金额
		Contract c = basedao.get(Contract.class, entity.getContract().getId());
		c.setTotalAmount(c.getTotalAmount()+m);
		int i=c.getHuowucount();
		c.setHuowucount(i+1);
		basedao.saveOrUpdate(c);
	}else{
		//修改
		Double oldm = entity.getAmount();
		if (UtilFuns.isNotEmpty(entity.getCnumber()) && UtilFuns.isNotEmpty(entity.getPrice()) ) {
			m=entity.getCnumber()*entity.getPrice();
			entity.setAmount(m);
		}
		Contract c = basedao.get(Contract.class, entity.getContract().getId());
		c.setTotalAmount(c.getTotalAmount()-oldm+m);
		basedao.saveOrUpdate(c);
	}
	basedao.saveOrUpdate(entity);
}

@Override
public void saveOrUpdateAll(Collection<ContractProduct> entitys) {
	// TODO Auto-generated method stub
	basedao.saveOrUpdateAll(entitys);
}

@Override
public void deleteById(Class<ContractProduct> entityClass, Serializable id) {
	//将此id的用户状态设为0
	basedao.deleteById(entityClass, id);
}

@Override
public void deleteById2(Class<ContractProduct> entityClass, Serializable id) {
	//将此id的用户状态设为0
	basedao.saveOrUpdate(entityClass);
}

@Override
public void delete(Class<ContractProduct> entityClass, Serializable[] ids) {
	
for (Serializable id : ids) {
	this.deleteById(ContractProduct.class, id);
}

}

@Override
public void deletehuowu(Class<ContractProduct> class1, ContractProduct contractproduct) {
	// 得到货物下的附件  修改合同总金额  级联删除货物及所有附件
	ContractProduct contractProduct2 = basedao.get(ContractProduct.class, contractproduct.getId());
	Set<ExtCproduct> extCproducts = contractProduct2.getExtCproducts();
	Contract contract = basedao.get(Contract.class, contractproduct.getContract().getId());
	int i=contract.getHuowucount();
	int j=contract.getFujiancount();
	for (ExtCproduct extCproduct : extCproducts) {
		contract.setTotalAmount(contract.getTotalAmount()-extCproduct.getAmount());
		contract.setFujiancount(j-1);
	}
	contract.setTotalAmount(contract.getTotalAmount()-contractProduct2.getAmount());
	contract.setHuowucount(i-1);
	basedao.saveOrUpdate(contract);
	basedao.deleteById(ContractProduct.class, contractproduct.getId());
}

}
