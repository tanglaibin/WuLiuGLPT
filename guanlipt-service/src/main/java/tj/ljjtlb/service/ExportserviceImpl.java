package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import tj.ljjtlb.utils.Page;
import tj.ljjtlb.utils.UtilFuns;
import tl.ljjtlb.dao.BaseDao;
import tl.ljjtlb.domain.Contract;
import tl.ljjtlb.domain.ContractProduct;
import tl.ljjtlb.domain.Export;
import tl.ljjtlb.domain.ExportProduct;
import tl.ljjtlb.domain.ExtCproduct;
import tl.ljjtlb.domain.ExtEproduct;
import tl.ljjtlb.domain.Module;
import tl.ljjtlb.domain.PackingList;

public class ExportserviceImpl implements Exportservice {
	
private BaseDao basedao;
public void setBasedao(BaseDao basedao) {
	this.basedao = basedao;
}

@Override
public List<Export> find(String hql, Class<Export> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.find(hql, entityClass, params);
}
@Override
public Export get(Class<Export> entityClass, Serializable id) {
	// TODO Auto-generated method stub
	return basedao.get(entityClass, id);
}
@Override
public Page<Export> findPage(String hql, Page<Export> page, Class<Export> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.findPage(hql, page, entityClass, params);
}
@Override
public void saveOrUpdate(Export entity) {
	// 保存报运单   没有的数据 进行初始化值  同步更新涉及的购销合同 以及下面的货物  附件
	//新增
	if (UtilFuns.isEmpty(entity.getId())) {
		entity.setState(0);
		String[] ids = entity.getContractIds().split(", ");
		StringBuilder sb=new StringBuilder();
		for (String id : ids) {
			//得到合同对象  更改state为2
			Contract contract = basedao.get(Contract.class, id);
			contract.setState(2);
			basedao.saveOrUpdate(contract);
			sb.append(contract.getContractNo()).append(" ");
		}
		entity.setCustomerContract(sb.toString());
		entity.setContractIds(UtilFuns.joinStr(ids, ","));
		entity.setInputDate(new Date());
		
		//通过购销合同id集合  查找下面的货物
		String hql="from ContractProduct where contract.id in ("+UtilFuns.joinInStr(ids)+")";
		List<ContractProduct> list = basedao.find(hql, ContractProduct.class, null);
		//数据搬家  将此前合同下的货物搬至报运单下  的货物数据表
		
		Set<ExportProduct> exportps=new HashSet<>();
		
		for (ContractProduct cp : list) {
			//每遍历一次，将货物搬至报运单货物下
			ExportProduct ep=new ExportProduct();
			ep.setProductNo(cp.getProductNo());
			ep.setPackingUnit(cp.getPackingUnit());
			ep.setCnumber(cp.getCnumber());
			ep.setBoxNum(cp.getBoxNum());
			ep.setPrice(cp.getPrice());
			ep.setOrderNo(cp.getOrderNo());
			ep.setExport(entity);
			
			exportps.add(ep);
			//每遍历一次货物  将货物下的附件  搬至报运单下附件
			Set<ExtCproduct> extCproducts = cp.getExtCproducts();
			
			Set<ExtEproduct> extEproducts=new HashSet<>();
			
			for (ExtCproduct ecp : extCproducts) {
				ExtEproduct eep =new ExtEproduct();
				/*BeanUtils.copyProperties(ecp, eep);
				eep.setId(null);
				eep.setExportProduct(ep);*/
				
				eep.setProductNo(ecp.getProductNo());
				eep.setProductImage(ecp.getProductImage());
				eep.setProductDesc(ecp.getProductDesc());
				eep.setPackingUnit(ecp.getPackingUnit());
				eep.setCnumber(ecp.getCnumber());
				eep.setPrice(ecp.getPrice());
				eep.setAmount(ecp.getAmount());
				eep.setProductRequest(ecp.getProductRequest());
				eep.setOrderNo(ecp.getOrderNo());
				eep.setCreateby(ecp.getCreateby());
				eep.setCreateDept(ecp.getCreateDept());
				eep.setCreateTime(ecp.getCreateTime());
				eep.setUpdateBy(ecp.getUpdateBy());
				eep.setUpdateTime(ecp.getUpdateTime());
				eep.setExportProduct(ep);

				extEproducts.add(eep);
			}
			ep.setExtEproducts(extEproducts);
		}
		entity.setExportProducts(exportps);
	}else{
		//更新
		basedao.saveOrUpdate(entity);
	}
	
}

@Override
public void saveOrUpdateAll(Collection<Export> entitys) {
	// TODO Auto-generated method stub
	basedao.saveOrUpdateAll(entitys);
}

@Override
public void deleteById(Class<Export> entityClass, Serializable id) {
	//将此id的用户状态设为0
	basedao.deleteById(entityClass, id);
}

@Override
public void deleteById2(Class<Export> entityClass, Serializable id) {
	//将此id的用户状态设为0
	basedao.saveOrUpdate(entityClass);
}

@Override
public void delete(Class<Export> entityClass, Serializable[] ids) {
	
for (Serializable id : ids) {
	this.deleteById(Export.class, id);
}

}

@Override
public void deletehuowu(Class<Export> class1, Export contractproduct) {
	// 得到货物下的附件  修改合同总金额  级联删除货物及所有附件
	/*Export contractProduct2 = basedao.get(Export.class, contractproduct.getId());
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
	basedao.deleteById(Export.class, contractproduct.getId());*/
}

@Override
public void submit(String[] ids, int i) {
	// TODO Auto-generated method stub
	for (String id : ids) {
		PackingList contract=basedao.get(PackingList.class, id);
		contract.setState(i);
		basedao.saveOrUpdate(contract);
	}
}

@Override
public void updatee(Export export3) {
	// TODO Auto-generated method stub
	//得到对象  根据改变的值 做对应修改  最后保存
	Export export=basedao.get(Export.class, export3.getId());
	
	export.setState(export3.getState());
	export.setRemark(export3.getRemark());
	//更新下面的货物信息  税金
	Set<ExportProduct> exportProducts = export3.getExportProducts();
	for (ExportProduct exportProduct : exportProducts) {
		ExportProduct exportProduct2 = basedao.get(ExportProduct.class, exportProduct.getId());
		exportProduct2.setTax(exportProduct.getTax());
		basedao.saveOrUpdate(exportProduct2);
	}
	
	//保存export
	basedao.saveOrUpdate(export);
}

}
