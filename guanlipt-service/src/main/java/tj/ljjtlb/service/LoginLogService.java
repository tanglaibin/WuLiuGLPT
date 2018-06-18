package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import tj.ljjtlb.utils.Page;
import tl.ljjtlb.domain.LoginLog;


/**
 * @Description:	LoginLogService接口
 * @Author:			唐来彬	唐来彬
 * @Company:		http://www.baidu.com
 * @CreateDate:		2018-3-22 15:33:05
 */

public interface LoginLogService {

	public List<LoginLog> find(String hql, Class<LoginLog> entityClass, Object[] params);
	public LoginLog get(Class<LoginLog> entityClass, Serializable id);
	public Page<LoginLog> findPage(String hql, Page<LoginLog> page, Class<LoginLog> entityClass, Object[] params);
	
	public void save(LoginLog entity);
	public void saveOrUpdate(LoginLog entity);
	public void saveOrUpdateAll(Collection<LoginLog> entitys);
	
	public void deleteById(Class<LoginLog> entityClass, Serializable id);
	public void delete(Class<LoginLog> entityClass, Serializable[] ids);
}
