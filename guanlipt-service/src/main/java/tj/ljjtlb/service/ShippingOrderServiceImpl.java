package tj.ljjtlb.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import tj.ljjtlb.utils.Page;
import tl.ljjtlb.dao.BaseDao;
import tl.ljjtlb.domain.Export;
import tl.ljjtlb.domain.PackingList;
import tl.ljjtlb.domain.ShippingOrder;

/**
 * @Description:	ShippingOrderService接口
 * @Author:			唐来彬	唐来彬
 * @Company:		http://www.baidu.com
 * @CreateDate:		2018-3-22 11:45:56
 */

public class ShippingOrderServiceImpl implements ShippingOrderService {
	//spring注入dao
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<ShippingOrder> find(String hql, Class<ShippingOrder> entityClass, Object[] params) {
		return baseDao.find(hql, ShippingOrder.class, params);
	}

	public ShippingOrder get(Class<ShippingOrder> entityClass, Serializable id) {
		return baseDao.get(ShippingOrder.class, id);
	}

	public Page<ShippingOrder> findPage(String hql, Page<ShippingOrder> page, Class<ShippingOrder> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, ShippingOrder.class, params);
	}

	public void saveOrUpdate(ShippingOrder entity) {
		baseDao.saveOrUpdate(entity);
	}

	public void save(Collection<ShippingOrder> entitys) {
		baseDao.saveOrUpdate(entitys);
	}

	public void saveOrUpdateAll(Collection<ShippingOrder> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<ShippingOrder> entityClass, Serializable id) {
		//删除委托单时  修改装箱单状态为1  已上报
		PackingList pk = baseDao.get(PackingList.class, id);
		pk.setState(1);
		baseDao.saveOrUpdate(pk);
		//同步更新装箱单下的报运单  状态为2  已装箱
		String ids = pk.getExportIds();
		String[] idss = ids.split(", ");
		for(int i=0;i<idss.length;i++){
			Export export = baseDao.get(Export.class, idss[i]);
			export.setState(2);
			baseDao.saveOrUpdate(export);
		}
		baseDao.deleteById(ShippingOrder.class, id);
	}

	public void delete(Class<ShippingOrder> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			this.deleteById(entityClass, id);
		}
	}

	@Override
	public void save(ShippingOrder entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOrUpdate(ShippingOrder model, String pid) {
		//新增  设置id为pid  设置各个状态  然后保存  修改装箱单状态为2   同步获取装箱单下的报运单  修改状态为3  最后调用basedao保存
		model.setId(pid);
		model.setState(0);
		model.setIsBatch("1");
		model.setIsTrans("1");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.setCreateTime(sdf.format(date));
		baseDao.saveOrUpdate(model);
		//获取装箱单  修改状态为2  已委托
		PackingList pk = baseDao.get(PackingList.class, pid);
		pk.setState(2);
		//货物装箱单下的报运单 修改状态为3
		String ids = pk.getExportIds();
		String[] idss = ids.split(", ");
		for(int i=0;i<idss.length;i++){
			Export export = baseDao.get(Export.class, idss[i]);
			export.setState(3);
			baseDao.saveOrUpdate(export);
		}
		baseDao.saveOrUpdate(pk);
	}

	@Override
	public void submit(String[] ids, int i) {
		// TODO Auto-generated method stub
		for (String id : ids) {
			ShippingOrder contract=baseDao.get(ShippingOrder.class, id);
			contract.setState(i);
			baseDao.saveOrUpdate(contract);
		}
	}

}
