package tj.ljjtlb.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import tj.ljjtlb.utils.Page;
import tj.ljjtlb.utils.UtilFuns;
import tl.ljjtlb.dao.BaseDao;
import tl.ljjtlb.domain.Export;
import tl.ljjtlb.domain.PackingList;

/**
 * @Description:	PackingListService接口
 * @Author:			唐来彬	唐来彬
 * @Company:		http://www.baidu.com
 * @CreateDate:		2018-3-20 16:02:05
 */

public class PackingListServiceImpl implements PackingListService {
	//spring注入dao
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<PackingList> find(String hql, Class<PackingList> entityClass, Object[] params) {
		return baseDao.find(hql, PackingList.class, params);
	}

	public PackingList get(Class<PackingList> entityClass, Serializable id) {
		return baseDao.get(PackingList.class, id);
	}

	public Page<PackingList> findPage(String hql, Page<PackingList> page, Class<PackingList> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, PackingList.class, params);
	}

	public void saveOrUpdate(PackingList entity) {
		if(entity.getId()==null){
			//新增  设置装箱单状态为默认1
			entity.setState(0);	
			//状态：0草稿1上报2报运
			//遍历ids  得到export  修改状态  保存
			String ids = entity.getExportIds();
			String[] idss = ids.split(", ");
			for(int i=0;i<idss.length;i++){
				Export export = baseDao.get(Export.class, idss[i]);
				export.setState(2);
				baseDao.saveOrUpdate(export);
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			entity.setCreateTime(sdf.format(date));
			baseDao.saveOrUpdate(entity);
		}else{
			//更新操作
			baseDao.saveOrUpdate(entity);
		}
	}

	public void save(Collection<PackingList> entitys) {
		baseDao.saveOrUpdate(entitys);
	}

	public void saveOrUpdateAll(Collection<PackingList> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<PackingList> entityClass, Serializable id) {
		PackingList pk = baseDao.get(PackingList.class, id);
		String exportIds = pk.getExportIds();
		String[] idss = exportIds.split(", ");
		for(int i=0;i<idss.length;i++){
			Export export = baseDao.get(Export.class, idss[i]);
			export.setState(1);
			baseDao.saveOrUpdate(export);
		}
		baseDao.deleteById(PackingList.class, id);
	}

	public void delete(Class<PackingList> entityClass, Serializable[] ids) {
		//删除装箱单 同时同步更改下面报运单状态为1
		for (Serializable id : ids) {
			this.deleteById(entityClass, id);
		}
	}

	@Override
	public void save(PackingList entity) {
		// TODO Auto-generated method stub
		
	}

}
