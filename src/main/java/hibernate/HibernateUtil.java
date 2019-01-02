/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import java.util.List;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author lhe
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    public static void stop() {
        sessionFactory.close();
    }
    
    public static <T> int saveEntity(T entity){
        Session session = getSession();
        Transaction tx1 = session.beginTransaction();

        int id = (Integer) session.save(entity);
        tx1.commit();
        session.close();
        return id;
    }
       
    public static <T> void updateEntity(T entity){
        Session session = getSession();
        Transaction tx1 = session.beginTransaction();

        session.update(entity);
        tx1.commit();
        session.close();
    }
    
    public static <T> List<T> getEntityList(String sql) {
        Session session = getSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery(sql);
        List<T> list = query.list();
        session.close();
        return list;
    }
    
    public static <T> T getEntity(String sql) {
        Session session = getSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery(sql);
        List<T> list = query.list();
        session.close();
        return list.size()>0? list.get(0) : null;
    }
    
    public static int clearTable(String name){
        Session session = getSession();
        String hql = String.format("delete from %s", name);
        Query query = session.createQuery(hql);
        int res = query.executeUpdate();
        session.close();
        return res;
    }
    
    
//    public static List<Site> getConfirmedSites() {
//        return getEntityList("from Site where confirmed=true and isCompany=true ");
//    }
//
//
//    public static List<Batchjob> getActiveBatchJobs() {
//        return getEntityList("from Batchjob where status in ('NEW', 'STARTED') ");
//    }
//
//    public static List<Wip> getActiveWips() {
//        return getEntityList("from Wip where status = 'NEW'");
//    }
//
//    public static Site getSiteByWip(Wip wip) {
//        String hql = String.format("from Site where url = '%s'", wip.getSite());
//        return getEntity(hql);
//    }
}
