package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import tj.ljjtlb.utils.Page;
import tj.ljjtlb.utils.UtilFuns;
import tl.ljjtlb.dao.BaseDao;
import tl.ljjtlb.domain.Contract;
import tl.ljjtlb.domain.ContractProduct;
import tl.ljjtlb.domain.ExtCproduct;

public class ExtCProductserviceImpl implements ExtCProductservice {
	
private BaseDao basedao;
public void setBasedao(BaseDao basedao) {
	this.basedao = basedao;
}

@Override
public List<ExtCproduct> find(String hql, Class<ExtCproduct> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.find(hql, entityClass, params);
}
@Override
public ExtCproduct get(Class<ExtCproduct> entityClass, Serializable id) {
	// TODO Auto-generated method stub
	return basedao.get(entityClass, id);
}
@Override
public Page<ExtCproduct> findPage(String hql, Page<ExtCproduct> page, Class<ExtCproduct> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.findPage(hql, page, entityClass, params);
}
@Override
public void saveOrUpdate(ExtCproduct entity) {
	// 给主键赋值  保存附件时  更新货物的金额  及总金额
	Double m = 0d;
	//新增附件
	if (UtilFuns.isEmpty(entity.getId())) {
		if (UtilFuns.isNotEmpty(entity.getCnumber()) && UtilFuns.isNotEmpty(entity.getPrice()) ) {
			m=entity.getCnumber()*entity.getPrice();
			entity.setAmount(m);
		}
		// 同步更新货物 购销合同金额
		ContractProduct contractProduct = basedao.get(ContractProduct.class, entity.getContractProduct().getId());
		contractProduct.setAmount(contractProduct.getAmount()+m);
		basedao.saveOrUpdate(contractProduct);
		Contract c = basedao.get(Contract.class, entity.getContractProduct().getContract().getId());
		c.setTotalAmount(c.getTotalAmount()+contractProduct.getAmount());
		int j=c.getFujiancount();
		c.setFujiancount(j+1);
		basedao.saveOrUpdate(c);
	}else{
		//修改附件信息  更新货物金额 合同总金额
		Double oldm = entity.getAmount();
		if (UtilFuns.isNotEmpty(entity.getCnumber()) && UtilFuns.isNotEmpty(entity.getPrice()) ) {
			m=entity.getCnumber()*entity.getPrice();
			entity.setAmount(m);
			
			ContractProduct contractProduct = basedao.get(ContractProduct.class, entity.getContractProduct().getId());
			Double oldm2 = contractProduct.getAmount();
			contractProduct.setAmount(contractProduct.getAmount()-oldm+m);
			
			Contract c = basedao.get(Contract.class, entity.getContractProduct().getContract().getId());
			c.setTotalAmount(c.getTotalAmount()-oldm2+contractProduct.getAmount());
			basedao.saveOrUpdate(contractProduct);
			basedao.saveOrUpdate(c);
		}
	}
	
	basedao.saveOrUpdate(entity);
}
@Override
public void saveOrUpdateAll(Collection<ExtCproduct> entitys) {
	// TODO Auto-generated method stub
	basedao.saveOrUpdateAll(entitys);
}

@Override
public void deleteById(Class<ExtCproduct> entityClass, Serializable id) {
	//将此id的用户状态设为0
	basedao.deleteById(entityClass, id);
}

@Override
public void deleteById2(Class<ExtCproduct> entityClass, Serializable id) {
	//将此id的用户状态设为0
	basedao.saveOrUpdate(entityClass);
}

@Override
public void delete(Class<ExtCproduct> entityClass, Serializable[] ids) {
	// TODO Auto-generated method stub
for (Serializable id : ids) {
	this.deleteById(ExtCproduct.class, id);
}

}


@Override
public void deletefujian(Class<ExtCproduct> class1, ExtCproduct extCproduct) {
	// TODO Auto-generated method stub
	ExtCproduct extCproduct2 = basedao.get(ExtCproduct.class, extCproduct.getId());
    Double oldm = extCproduct2.getAmount();
	
	ContractProduct contractProduct = basedao.get(ContractProduct.class, extCproduct.getContractProduct().getId());
	Double oldm2 = contractProduct.getAmount();
	
	contractProduct.setAmount(oldm2-oldm);
	
	Contract c = basedao.get(Contract.class, extCproduct.getContractProduct().getContract().getId());
	int i=c.getFujiancount();
	c.setFujiancount(i-1);
	c.setTotalAmount(c.getTotalAmount()-oldm2+contractProduct.getAmount());
	basedao.saveOrUpdate(contractProduct);
	basedao.saveOrUpdate(c);
	// 最后调用deletebyid  删除附件
	basedao.deleteById(ExtCproduct.class, extCproduct.getId());
}


}
