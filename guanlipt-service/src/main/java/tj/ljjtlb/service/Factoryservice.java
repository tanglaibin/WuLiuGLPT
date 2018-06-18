package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import tj.ljjtlb.utils.Page;
import tl.ljjtlb.domain.Factory;

public interface Factoryservice {
	    //查询所有，带条件查询
		public  List<Factory> find(String hql, Class<Factory> entityClass, Object[] params);
		//获取一条记录
		public  Factory get(Class<Factory> entityClass, Serializable id);
		//分页查询，将数据封装到一个page分页工具类对象
		public   Page<Factory> findPage(String hql, Page<Factory> page, Class<Factory> entityClass, Object[] params);
		
		//新增和修改保存
		public   void saveOrUpdate(Factory entity);
		//批量新增和修改保存
		public   void saveOrUpdateAll(Collection<Factory> entitys);
		
		//单条删除，按id
		public   void deleteById(Class<Factory> entityClass, Serializable id);
		//批量删除
		public   void delete(Class<Factory> entityClass, Serializable[] ids);
		
		void deleteById2(Class<Factory> entityClass, Serializable id);
}
