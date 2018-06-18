package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import tj.ljjtlb.utils.Page;
import tl.ljjtlb.domain.ExtEproduct;

public interface Exteproductservice {
	    //查询所有，带条件查询
		public  List<ExtEproduct> find(String hql, Class<ExtEproduct> entityClass, Object[] params);
		//获取一条记录
		public  ExtEproduct get(Class<ExtEproduct> entityClass, Serializable id);
		//分页查询，将数据封装到一个page分页工具类对象
		public   Page<ExtEproduct> findPage(String hql, Page<ExtEproduct> page, Class<ExtEproduct> entityClass, Object[] params);
		
		//新增和修改保存
		public   void saveOrUpdate(ExtEproduct entity);
		//批量新增和修改保存
		public   void saveOrUpdateAll(Collection<ExtEproduct> entitys);
		
		//单条删除，按id
		public   void deleteById(Class<ExtEproduct> entityClass, Serializable id);
		//批量删除
		public   void delete(Class<ExtEproduct> entityClass, Serializable[] ids);
		
		void deleteById2(Class<ExtEproduct> entityClass, Serializable id);
		
		public void deletehuowu(Class<ExtEproduct> class1, ExtEproduct contractproduct);
}
