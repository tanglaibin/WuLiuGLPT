package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import tj.ljjtlb.utils.Page;
import tl.ljjtlb.domain.ShippingOrder;

/**
 * @Description:	ShippingOrderService接口
 * @Author:			唐来彬	唐来彬
 * @Company:		http://www.baidu.com
 * @CreateDate:		2018-3-22 11:45:56
 */

public interface ShippingOrderService {

	public List<ShippingOrder> find(String hql, Class<ShippingOrder> entityClass, Object[] params);
	public ShippingOrder get(Class<ShippingOrder> entityClass, Serializable id);
	public Page<ShippingOrder> findPage(String hql, Page<ShippingOrder> page, Class<ShippingOrder> entityClass, Object[] params);
	
	public void save(ShippingOrder entity);
	public void saveOrUpdate(ShippingOrder entity);
	public void saveOrUpdateAll(Collection<ShippingOrder> entitys);
	
	public void deleteById(Class<ShippingOrder> entityClass, Serializable id);
	public void delete(Class<ShippingOrder> entityClass, Serializable[] ids);
	public void saveOrUpdate(ShippingOrder model, String pid);
	public void submit(String[] ids, int i);
}
