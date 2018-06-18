package tj.ljjtlb.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import tj.ljjtlb.utils.Encrypt;
import tj.ljjtlb.utils.MailUtil;
import tj.ljjtlb.utils.Page;
import tj.ljjtlb.utils.SysConstant;
import tj.ljjtlb.utils.UtilFuns;
import tl.ljjtlb.dao.BaseDao;
import tl.ljjtlb.domain.User;

public class UserserviceImpl implements Userservice {
	
private BaseDao basedao;
public void setBasedao(BaseDao basedao) {
	this.basedao = basedao;
}

@Override
public List<User> find(String hql, Class<User> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.find(hql, entityClass, params);
}
@Override
public User get(Class<User> entityClass, Serializable id) {
	// TODO Auto-generated method stub
	return basedao.get(entityClass, id);
}
@Override
public Page<User> findPage(String hql, Page<User> page, Class<User> entityClass, Object[] params) {
	// TODO Auto-generated method stub
	return basedao.findPage(hql, page, entityClass, params);
}
@Override
public void saveOrUpdate(final User entity) {
	// 新建
	if (UtilFuns.isEmpty(entity.getId())) {
		String id=UUID.randomUUID().toString();
		entity.setId(id);
		entity.getUserinfo().setId(id);
		entity.setPassword(Encrypt.md5(SysConstant.DEFAULT_PASS,entity.getUserName()));
		
		basedao.saveOrUpdate(entity);
		
		//开启新线程，发送邮件  不影响前面的操作
		Thread th=new Thread(new Runnable() {
			public void run() {
				try {
					MailUtil.sendMail(entity.getUserinfo().getMail(), "新员工入职欢迎", "你好");
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		th.start();
		
	}else{//更新
		basedao.saveOrUpdate(entity);
	}
	
}
@Override
public void saveOrUpdateAll(Collection<User> entitys) {
	// TODO Auto-generated method stub
	basedao.saveOrUpdateAll(entitys);
}

@Override
public void deleteById(Class<User> entityClass, Serializable id) {
	//将此id的用户状态设为0
	basedao.deleteById(entityClass, id);
	
	
	
	
	/*String hql="from User where deptparent.id=?";
	List<User> list = basedao.find(hql, User.class, new Object[]{id});
	if (list!=null && list.size()>0) {
		for(User dept : list) {
			deleteById(User.class,dept.getId());
		}
	}
	basedao.deleteById(entityClass, id);*/
}

@Override
public void deleteById2(Class<User> entityClass, Serializable id) {
	//将此id的用户状态设为0
	String hql="from User where id=?";
	User user = basedao.get(User.class, id);
	user.setState(0);
	basedao.saveOrUpdate(user);

}




@Override
public void delete(Class<User> entityClass, Serializable[] ids) {
	// TODO Auto-generated method stub
for (Serializable id : ids) {
	this.deleteById2(User.class, id);
}


}






}
