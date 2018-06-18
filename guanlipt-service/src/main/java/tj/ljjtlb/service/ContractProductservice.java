package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import tj.ljjtlb.utils.Page;
import tl.ljjtlb.domain.ContractProduct;

public interface ContractProductservice {
	    //查询所有，带条件查询
		public  List<ContractProduct> find(String hql, Class<ContractProduct> entityClass, Object[] params);
		//获取一条记录
		public  ContractProduct get(Class<ContractProduct> entityClass, Serializable id);
		//分页查询，将数据封装到一个page分页工具类对象
		public   Page<ContractProduct> findPage(String hql, Page<ContractProduct> page, Class<ContractProduct> entityClass, Object[] params);
		
		//新增和修改保存
		public   void saveOrUpdate(ContractProduct entity);
		//批量新增和修改保存
		public   void saveOrUpdateAll(Collection<ContractProduct> entitys);
		
		//单条删除，按id
		public   void deleteById(Class<ContractProduct> entityClass, Serializable id);
		//批量删除
		public   void delete(Class<ContractProduct> entityClass, Serializable[] ids);
		
		void deleteById2(Class<ContractProduct> entityClass, Serializable id);
		
		public void deletehuowu(Class<ContractProduct> class1, ContractProduct contractproduct);
}
