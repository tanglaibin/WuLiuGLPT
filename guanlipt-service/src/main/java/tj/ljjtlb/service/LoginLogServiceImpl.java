package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import tj.ljjtlb.utils.Page;
import tl.ljjtlb.dao.BaseDao;
import tl.ljjtlb.domain.LoginLog;

/**
 * @Description:	LoginLogService接口
 * @Author:			唐来彬	唐来彬
 * @Company:		http://www.baidu.com
 * @CreateDate:		2018-3-22 15:33:05
 */

public class LoginLogServiceImpl implements LoginLogService {
	//spring注入dao
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<LoginLog> find(String hql, Class<LoginLog> entityClass, Object[] params) {
		return baseDao.find(hql, LoginLog.class, params);
	}

	public LoginLog get(Class<LoginLog> entityClass, Serializable id) {
		return baseDao.get(LoginLog.class, id);
	}

	public Page<LoginLog> findPage(String hql, Page<LoginLog> page, Class<LoginLog> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, LoginLog.class, params);
	}

	public void saveOrUpdate(LoginLog entity) {
		baseDao.saveOrUpdate(entity);
	}

	public void save(Collection<LoginLog> entitys) {
	}

	public void saveOrUpdateAll(Collection<LoginLog> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<LoginLog> entityClass, Serializable id) {
		baseDao.deleteById(LoginLog.class, id);
	}

	public void delete(Class<LoginLog> entityClass, Serializable[] ids) {
		baseDao.delete(LoginLog.class, ids);
	}

	@Override
	public void save(LoginLog entity) {
		// TODO Auto-generated method stub
		
	}

}
