package vn.myclass.core.data.daoimpl;

import org.hibernate.*;
import vn.myclass.core.common.constant.CoreConstant;
import vn.myclass.core.common.utils.HibernateUtil;
import vn.myclass.core.data.dao.GenericDao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class AbstactDao<ID extends Serializable, T> implements GenericDao<ID, T> {
    private Class<T> persistenceClass;

    public AbstactDao() {
        this.persistenceClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public String getPersistenceClassName() {
        return persistenceClass.getSimpleName();
    }

    //    protected Session getSession() {
//        return HibernateUtil.getSessionFactory().openSession();
//    }
    public List<T> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<T> list = new ArrayList<T>();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            StringBuilder sql = new StringBuilder("from ");
            sql.append(this.getPersistenceClassName());
            Query query = session.createQuery(sql.toString());
            list = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return list;
    }

    public T update(T entity) {
        T result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Object object = session.merge(entity);
            result = (T) object;
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

    public void save(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(entity);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public T findById(ID id) {
        T result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = (T) session.get(persistenceClass, id);
            if (null == result) {
                throw new ObjectNotFoundException("NOT FOUNT " + id, null);
            }
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

    public Object[] findByProperty(String property, Object value, String sortExpression, String sortDirection) {
        List<T> list = new ArrayList<T>();
        Object totalItem = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            StringBuilder sql1 = new StringBuilder(" from ");
            sql1.append(getPersistenceClassName());
            if (null != property && null != value) {
                sql1.append(" where ").append(property).append(" =: value ");
            }
            sql1.append(" order by ").append(sortExpression);
            if (null != sortDirection) {
                sql1.append(" " + (sortDirection.equals(CoreConstant.SORT_ASC) ? "asc" : "desc"));
            }
            Query query1 = session.createQuery(sql1.toString());
            if (value != null){
                query1.setParameter("value", value);
            }
            list = query1.list();
            StringBuilder sql2 = new StringBuilder(" select count(*) from ");
            sql2.append(getPersistenceClassName());
            if (null != property && null != value) {
                sql1.append(" where ").append(property).append(" =: value ");
            }
            Query query2 = session.createQuery(sql2.toString());
            if (value != null){
                query2.setParameter("value", value);
            }
            totalItem = query2.list().get(0);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new Object[]{totalItem, list};
    }

    public Integer delete(List<ID> ids) {
        int count = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (ID item : ids) {
                T t = (T) session.get(persistenceClass, item);
                count ++;
                session.delete(t);
            }
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return count;
    }
}
